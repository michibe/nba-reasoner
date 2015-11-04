package org.hhz.nbareasoner.config.model.base.nba.rating;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by mbehr on 13.05.2015.
 */
public interface NbaRatingConfig {

    @NotNull
    public Map<String, NbaRatingConfigParameter> getNbaRatingConfigParameters();

    @NotNull
    public String getRatingClass();

    @NotNull
    public double getWeightDefault();

    @NotNull
    public boolean getWeightConfigurable();

    @NotNull
    public String getFilterOnRatingResult();

    //Defines if the configured NbaRating should be displayed in the overview for the fast results
    @NotNull
    public boolean displayInOverview();

}
