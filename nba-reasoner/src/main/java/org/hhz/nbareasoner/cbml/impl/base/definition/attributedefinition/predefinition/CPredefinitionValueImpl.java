package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.predefinition;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinitionValue;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CPredefinitionValueImpl implements CPredefinitionValue
{
    @XmlAttribute
    private final String perAttributeValue;

    @XmlAttribute
    private final String predefineValue;

    public CPredefinitionValueImpl(String perAttributeValue, String predefineValue) {
        this.perAttributeValue = perAttributeValue;
        this.predefineValue = predefineValue;
    }

    private CPredefinitionValueImpl() {
        this.perAttributeValue = null;
        this.predefineValue = null;
    }

    public String getPerAttributeValue() {
        return perAttributeValue;
    }

    public String getPredefineValue() {
        return predefineValue;
    }
}
