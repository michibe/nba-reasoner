package org.hhz.nbareasoner.util.cache;

import org.hhz.nbareasoner.util.extern.StringHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.ResponseCache;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by m on 04.04.2015.
 *
 * Mit Hilfe dieser Klasse können die ResponseCaches abgespeichert und wieder abgerufen werden
 * dadurch werden die cached Responses an einer stelle gemanagt... die Klass ist ein singleton
 *
 *
 */
public class UrlResponseCacheManager {

    private final static Logger logger = Logger.getLogger(UrlResponseCacheManager.class.getName());
    private final static UrlResponseCacheManager instance = new UrlResponseCacheManager();
    private final int cacheLifeTimeInSeconds = 10;

    public static UrlResponseCacheManager getInstance() {
        return instance;
    }

    protected UrlResponseCacheManager() {

    }

    List<UrlResponseCache> responseCacheList =  new ArrayList<>();


    @Nullable
    public <E> E getCachedObject(@Nullable String cacheUrl)
    {
        UrlResponseCache<E> urlResponseCache = this.<E>getResponseCacheFor(cacheUrl);

        if(urlResponseCache==null)
        {
            return null;
        }
        else
        {
            return  urlResponseCache.getCachedObject();
        }
    }

    @Nullable
    public <E> UrlResponseCache<E> getResponseCacheFor(@Nullable String cacheUrl)
    {
        if(cacheUrl==null)
        {
            return  null;
        }

        Iterator<UrlResponseCache> i = responseCacheList.iterator();
        while (i.hasNext()) {
            UrlResponseCache urlResponseCache = i.next();
            if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-urlResponseCache.getTimestamp())>cacheLifeTimeInSeconds)
            {   logger.fine("remove responseCache from CacheManager: " + urlResponseCache);
                i.remove();
                if(urlResponseCache.getCacheUrl().equals(cacheUrl))
                {
                    return null;
                }
            }

            if(urlResponseCache.getCacheUrl().equals(cacheUrl))
            {
                logger.fine("Return from responseCache from CacheManager: " + urlResponseCache);
                return urlResponseCache;
            }

        }
        return null;
    }

    public void addResponseCache(UrlResponseCache responseCache)
    {
        this.responseCacheList.add(responseCache);

        logger.fine("store responseCache in CacheManager: " + responseCache);
    }


}
