package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationParameter;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 27.04.2015.
 */

public class CComputationParameterImpl implements CComputationParameter
{
   //TODO JAXB-Annotationen vervollständigen wie zb. mit required
    @XmlAttribute(required = true)
    private final String key;

    @XmlAttribute(required = true)
    private final String value;

    public CComputationParameterImpl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private CComputationParameterImpl() {
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
