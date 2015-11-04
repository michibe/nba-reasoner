package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.alias;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CAlias;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by mbehr on 27.04.2015.
 */
public abstract class CAliasImpl implements CAlias {


    @XmlAttribute
    private final String key;

    @XmlAttribute
    private final String alias;

    public CAliasImpl(String key, String alias) {
        this.key = key;
        this.alias = alias;
    }

    protected CAliasImpl() {
        this.key = null;
        this.alias = null;
    }



    public String getKey() {
        return key;
    }

    public String getAlias() {
        return alias;
    }
}
