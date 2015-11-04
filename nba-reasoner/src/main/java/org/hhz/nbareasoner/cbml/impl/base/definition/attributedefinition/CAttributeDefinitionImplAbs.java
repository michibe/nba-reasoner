package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition;

import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.alias.CKeyAliasImpl;
import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.alias.CValueAliasImpl;
import org.hhz.nbareasoner.cbml.model.base.CException;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CKeyAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Created by mbehr on 27.04.2015.
 */
public abstract class CAttributeDefinitionImplAbs implements CAttributeDefinition {



    @NotNull
    @XmlAttribute
    private final String key;

    @NotNull
    @XmlAttribute
    private final String type;



    @NotNull
    @XmlAttribute
    private final String value;




    private final Map<String,CKeyAlias> cKeyAliasDefinitions = new HashMap<String,CKeyAlias>();

    //Der key des value alias besteht aus key und value da ein alias für de für verschiedne werte vergeben werden kann
    /*So zum bsipiel
    <valueAlias key="en" alias="open" value="open"/>
    <valueAlias key="en" alias="closed" value="closed"/>
    <valueAlias key="de" alias="offen" value="open"/>
    <valueAlias key="de" alias="abgeschlossen" value="closed"/>*/
    private final Map<String,CValueAlias> cValueAliasDefinitions = new HashMap<String,CValueAlias>();



   // @XmlElement(type=EValueAliasImpl.class, name="valueAlias")
   // private  List<EValueAlias> eValueAliasDefinitionsTmp = new ArrayList<EValueAlias>();





    protected CAttributeDefinitionImplAbs(@NotNull String key, @NotNull String type, @NotNull String value, @Nullable List<CKeyAlias> eKeyAliasDefinitionsTmp, @Nullable List<CValueAlias> eValueAliasDefinitionsTmp)  {
        this.key = key;
        this.type = type;
        this.value = value;

        if(eKeyAliasDefinitionsTmp!=null)
        for (CKeyAlias eKeyAliasDefinition : eKeyAliasDefinitionsTmp) cKeyAliasDefinitions.put(eKeyAliasDefinition.getKey(),eKeyAliasDefinition);
        if(eKeyAliasDefinitionsTmp!=null)
        for (CValueAlias eValueAliasDefinition : eValueAliasDefinitionsTmp) cValueAliasDefinitions.put(eValueAliasDefinition.getKey(),eValueAliasDefinition);





    }



    protected CAttributeDefinitionImplAbs() {
        this.key = null;
        this.type = null;
        this.value = null;

    }

    @Nullable
    public CAlias getKeyAliasFor(String key)
    {
        return this.cKeyAliasDefinitions.get(key);
    }
    @Nullable
    public CAlias getValueAliasFor(String key)
    {
        return this.cValueAliasDefinitions.get(key);
    }


    public String getKey() {
        return key;
    }


    public String getType() {
        return type;
    }


    public String getValue() {
        return value;
    }







    @XmlElement(type=CKeyAliasImpl.class, name="keyAlias")
    private List<CKeyAlias> getEKeyAliasDefinitionsForJaxbAsList()
    {
        return new ArrayList<CKeyAlias>(this.cKeyAliasDefinitions.values());
    }

    private void setEKeyAliasDefinitionsForJaxbAsList(List<CKeyAlias> list)
    {

        for(CKeyAlias eKeyAlias : list)
        {
            this.cKeyAliasDefinitions.put(eKeyAlias.getKey(), eKeyAlias);
        }


    }


    @XmlElement(type=CValueAliasImpl.class, name="valueAlias")
    private List<CValueAlias> getEValueAliasDefinitionsForJaxbAsList()
    {
        return new ArrayList<CValueAlias>(this.cValueAliasDefinitions.values());
    }

    private void setEValueAliasDefinitionsForJaxbAsList(List<CValueAlias> list)
    {

        for(CValueAlias eValueAlias : list)
        {
            this.cValueAliasDefinitions.put(eValueAlias.getKey() + eValueAlias.getValue(), eValueAlias);
        }


    }


    public Map<String, CKeyAlias> getcKeyAliasDefinitions() {
        return Collections.unmodifiableMap(cKeyAliasDefinitions);
    }

    public Map<String, CValueAlias> getcValueAliasDefinitions() {
        return Collections.unmodifiableMap(cValueAliasDefinitions);
    }
}
