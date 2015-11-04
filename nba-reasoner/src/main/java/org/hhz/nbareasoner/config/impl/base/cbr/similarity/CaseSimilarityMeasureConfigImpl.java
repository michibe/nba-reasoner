package org.hhz.nbareasoner.config.impl.base.cbr.similarity;

import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation.CComputationParameterImpl;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationParameter;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfigParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * Created by mbehr on 13.05.2015.
 */
public class CaseSimilarityMeasureConfigImpl implements CaseSimilarityMeasureConfig {

    private final Map<String,CaseSimilarityMeasureConfigParameter> caseSimilarityMeasureConfigParameters = new HashMap<>();

    @XmlAttribute(name="attributeKey", required = true)
    @NotNull
    private final String attributeKey;



    @XmlAttribute(name="similarityMeasureClass", required = true)
    @NotNull
    private final String similarityMeasureClass;

    @XmlAttribute(name="weightDefault")
    @NotNull
    private final double weightDefault; // default is 1

    @XmlAttribute(name="weightConfigurable")
    @NotNull
    private final boolean weightConfigurable; //default ist false

    @XmlAttribute(name="filterOnSimilarityMeasureResult")
    @NotNull
    private final String filterOnSimilarityMeasureResult; //<value >value =value // default ist 0




    public CaseSimilarityMeasureConfigImpl(@NotNull String attributeKey, @NotNull String similarityFunctionClass, @NotNull double weightDefault, @NotNull boolean weightConfigurable, @NotNull String filterOnFunctionResult,@Nullable List<CaseSimilarityMeasureConfigParameter> caseSimilarityMeasureConfigParameters) {
        this.attributeKey = attributeKey;
        this.similarityMeasureClass = similarityFunctionClass;


            this.weightDefault = weightDefault;


        this.weightConfigurable = weightConfigurable;

            this.filterOnSimilarityMeasureResult = filterOnFunctionResult;


        if(caseSimilarityMeasureConfigParameters != null)
            for (CaseSimilarityMeasureConfigParameter caseSimilarityMeasureConfigAttribute : caseSimilarityMeasureConfigParameters) this.caseSimilarityMeasureConfigParameters.put(caseSimilarityMeasureConfigAttribute.getKey(),caseSimilarityMeasureConfigAttribute);

    }




    //For JAXB

    private CaseSimilarityMeasureConfigImpl() {
        this.attributeKey = null;
        this.similarityMeasureClass = null;
        this.weightDefault = 1.0;
        this.weightConfigurable = false;
        this.filterOnSimilarityMeasureResult = "0";
    }




    public Map<String, CaseSimilarityMeasureConfigParameter> getCaseSimilarityMeasureConfigParameters() {
        return Collections.unmodifiableMap(caseSimilarityMeasureConfigParameters);
    }

    @NotNull
    public String getAttributeKey() {
        return attributeKey;
    }

    @NotNull
    public String getSimilarityMeasureClass() {
        return similarityMeasureClass;
    }

    @NotNull
    public Double getWeightDefault() {
        return weightDefault;
    }

    @NotNull
    public Boolean getWeightConfigurable() {
        return weightConfigurable;
    }

    @NotNull
    public String getFilterOnSimilarityMeasureResult() {
        return filterOnSimilarityMeasureResult;
    }





//For Jaxb




    @XmlElement(type=CaseSimilarityMeasureConfigAttributeImpl.class , name="caseSimilarityMeasureConfigParameter")
    private List<CaseSimilarityMeasureConfigParameter> getCaseSimilarityMeasureConfigAttributesAsList()
    {
        return new ArrayList<CaseSimilarityMeasureConfigParameter>(this.caseSimilarityMeasureConfigParameters.values());
    }

    private void setCaseSimilarityMeasureConfigAttributesAsList(List<CaseSimilarityMeasureConfigParameter> list)
    {
        for(CaseSimilarityMeasureConfigParameter caseSimilarityMeasureConfigAttribute : list)
        {
            this.caseSimilarityMeasureConfigParameters.put(caseSimilarityMeasureConfigAttribute.getKey(), caseSimilarityMeasureConfigAttribute);
        }
    }
}
