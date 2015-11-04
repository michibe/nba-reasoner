package org.hhz.nbareasoner.config.impl.base.nba.rating;

import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfigParameter;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NbaRatingConfigAttributeImpl implements NbaRatingConfigParameter {

    @XmlAttribute(required = true)
    private final String key;

    @XmlAttribute(required = true)
    private final String value;

    public NbaRatingConfigAttributeImpl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private NbaRatingConfigAttributeImpl() {
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
