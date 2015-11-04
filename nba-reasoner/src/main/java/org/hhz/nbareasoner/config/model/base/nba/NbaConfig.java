package org.hhz.nbareasoner.config.model.base.nba;

import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mbehr on 12.05.2015.
 */
public interface NbaConfig {

    @NotNull
    public List<NbaRatingConfig> getNbaRatingConfigs();


}
