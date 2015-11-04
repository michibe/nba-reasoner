package org.hhz.nbareasoner.config.model.base.cbr;

import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mbehr on 20.02.2015.
 */
public interface CbrConfig {

    //Config etc umsetzen

    @NotNull
    public List<CaseSimilarityMeasureConfig> getCaseSimilarityMeasureConfigs();
}
