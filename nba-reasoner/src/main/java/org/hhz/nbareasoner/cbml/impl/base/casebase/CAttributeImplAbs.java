package org.hhz.nbareasoner.cbml.impl.base.casebase;


import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * Created by mbehr on 30.04.2015.
 */
public abstract class CAttributeImplAbs implements CAttribute {

    private final static Logger logger = LoggerFactory.getLogger(CAttributeImplAbs.class);
    @NotNull
    private final CAttributeDefinition eAttributeDefinition;

    @XmlAttribute(name="key",required = true)
    @NotNull
    private final String key;


    @NotNull
    private final Object value;




    private final CComponent parentEComponent;

    protected CAttributeImplAbs(@NotNull String key, @NotNull Object value, @NotNull CComponent parentEComponent) {
        this.key = key;
        this.value = value;

        //checken ob der wert für den deklarierten typen anwendbar ist
        this.parentEComponent = parentEComponent;
        this.eAttributeDefinition = parentEComponent.getCDefinition().getCAttributeDefinition(key);

    }


    @NotNull
    @Override
    public CComponent getParentCComponent() {
        return this.parentEComponent;
    }

    @NotNull
    @Override
    public CAttributeDefinition getCDefinition() {
        return this.eAttributeDefinition;
    }

    @Override
    @NotNull
    public String getKey() {
        return key;
    }


    @Override
    @NotNull
    public Object getValue() {
        return this.value;
    }


    @XmlAttribute(name="value",required = true)
    @NotNull
    private String getValueAsString() {
        return this.value.toString();
    }




    @XmlAttribute(name="type",required = true)
    @Override
    @NotNull
    public String getType()
    {
        return this.eAttributeDefinition.getType();
    }




    @Override
    public String toString() {
        return "EAttributeImplAbs{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", eAttributeDefinition=" + eAttributeDefinition +
                '}';
    }


    //For JAXB

    protected CAttributeImplAbs(){
        this.eAttributeDefinition = null;
        this.key = null;
        this.value = null;
        this.parentEComponent = null;

    }

    }
