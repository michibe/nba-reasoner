package org.hhz.nbareasoner.config.impl.base.cbr.similarity;

import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfigParameter;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 13.05.2015.
 */
public class CaseSimilarityMeasureConfigAttributeImpl implements CaseSimilarityMeasureConfigParameter {

    @XmlAttribute(required = true)
    private final String key;

    @XmlAttribute(required = true)
    private final String value;

    public CaseSimilarityMeasureConfigAttributeImpl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private CaseSimilarityMeasureConfigAttributeImpl() {
        this.key = null;
        this.value = null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
