package org.hhz.nbareasoner.config.model.base.cbr.similarity;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by mbehr on 13.05.2015.
 */
public interface CaseSimilarityMeasureConfig {

    @NotNull
    public Map<String, CaseSimilarityMeasureConfigParameter> getCaseSimilarityMeasureConfigParameters();

    @NotNull
    public String getAttributeKey();

    @NotNull
    public String getSimilarityMeasureClass();

    @NotNull
    public Double getWeightDefault(); //default = 1.0

    @NotNull
    public Boolean getWeightConfigurable(); //default = false


    @NotNull
    public String getFilterOnSimilarityMeasureResult(); //<value >value =value // default ist 0

}
