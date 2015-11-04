package org.hhz.nbareasoner.config.impl.base.cbr;


import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation.CComputationParameterImpl;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationParameter;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.CaseSimilarityMeasureConfigImpl;
import org.hhz.nbareasoner.config.model.base.cbr.CbrConfig;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbehr on 20.02.2015.
 */
public class CbrConfigImpl implements CbrConfig {

    @XmlElementWrapper(name="caseSimilarityMeasureConfigs", required = true)
  @XmlElement(name="caseSimilarityMeasureConfig",type= CaseSimilarityMeasureConfigImpl.class)
  private final List<CaseSimilarityMeasureConfig> caseSimilarityMeasureConfigs = new ArrayList<>();

    public CbrConfigImpl(@NotNull List<CaseSimilarityMeasureConfig> caseSimilarityMeasureConfigs) {
        this.caseSimilarityMeasureConfigs.addAll(caseSimilarityMeasureConfigs);
    }

    @NotNull
    public List<CaseSimilarityMeasureConfig> getCaseSimilarityMeasureConfigs() {
        return Collections.unmodifiableList(caseSimilarityMeasureConfigs);
    }




//for JAXB
    private CbrConfigImpl() {
            }



}
