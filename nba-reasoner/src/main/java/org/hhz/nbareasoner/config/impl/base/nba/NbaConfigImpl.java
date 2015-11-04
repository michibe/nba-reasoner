package org.hhz.nbareasoner.config.impl.base.nba;


import org.hhz.nbareasoner.config.impl.base.nba.rating.NbaRatingConfigImpl;
import org.hhz.nbareasoner.config.model.base.nba.NbaConfig;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NbaConfigImpl implements NbaConfig{

    @XmlElementWrapper(name="nbaRatingConfigs", required = true)
    @XmlElement(name="nbaRatingConfig",type= NbaRatingConfigImpl.class)
    private final List<NbaRatingConfig> nbaRatingConfigs = new ArrayList<>();

    public NbaConfigImpl(@NotNull List<NbaRatingConfig> nbaRatingConfigs) {
        this.nbaRatingConfigs.addAll(nbaRatingConfigs);
    }

    @Override
    public List<NbaRatingConfig> getNbaRatingConfigs() {
        return Collections.unmodifiableList(nbaRatingConfigs);
    }

    //for JAXB
    private NbaConfigImpl() {
    }
}
