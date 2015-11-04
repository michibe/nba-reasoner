package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.alias;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CValueAliasImpl extends CAliasImpl implements CValueAlias {


    @XmlAttribute
    private final String value;


    public CValueAliasImpl(String key, String alias, String value) {
        super(key, alias);
        this.value = value;
    }

    private CValueAliasImpl() {
        super(null, null);
        this.value = null;
    }

    public String getValue() {
        return value;
    }
}

