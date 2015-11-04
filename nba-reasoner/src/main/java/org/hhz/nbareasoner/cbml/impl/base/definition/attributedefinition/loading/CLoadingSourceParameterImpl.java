package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.loading;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceParameter;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CLoadingSourceParameterImpl implements CLoadingSourceParameter
{
    @NotNull
    @XmlAttribute(required = true)
    private final String key;

    @NotNull
    @XmlAttribute(required = true)
    private final String value;

    public CLoadingSourceParameterImpl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private CLoadingSourceParameterImpl() {
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
