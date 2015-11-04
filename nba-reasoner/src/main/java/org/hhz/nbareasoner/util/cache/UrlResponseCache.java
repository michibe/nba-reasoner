package org.hhz.nbareasoner.util.cache;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by m on 30.03.2015.
 *
 *
 *
 *
 * Diese Klasse wird verwendet um  das ergebnis einer abfrage für eine url zwischensupeichern.
 * Eine folgeabfrage die sich mit &cacheId auf die vorherige anfrage bezieht kann so schneller abgearbeitet werden
 *
 */


    //wenn ein objekt in den cache hinzugefügt wird, muss nach der resourceId geschaut werden
public class UrlResponseCache<E> {



    //Die CacheId setzt sich aus der session id und einer zufallszahl zusammen
    private final Long timestamp;

    @NotNull
    private final String cacheUrl;

    @NotNull
    private final E cachedObject;

    public UrlResponseCache(@NotNull Long timestamp, @NotNull String cacheUrl, @NotNull E cachedObject) {
        this.timestamp = timestamp;
        this.cacheUrl = cacheUrl;
        this.cachedObject = cachedObject;
    }


    public Long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public String getCacheUrl() {
        return cacheUrl;
    }

    @NotNull
    public E getCachedObject() {
        return cachedObject;
    }

    @Override
    public String toString() {
        return "UrlResponseCache{" +
                "timestamp=" + timestamp +
                ", cacheUrl='" + cacheUrl + '}';
    }
}
