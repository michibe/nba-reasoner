package org.hhz.nbareasoner.cbml.impl.base.definition;

import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation.CComputedAttributeDefinitionImpl;
import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.loading.CLoadedAttributeDefinitionImpl;
import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinitionImpl;
import org.hhz.nbareasoner.cbml.model.base.definition.CComponentDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import java.util.*;

/**
 * Created by mbehr on 29.04.2015.
 */
public abstract class CComponentDefinitionImplAbs implements CComponentDefinition {



    private final Map<String,CLoadedAttributeDefinition> cLoadedAttributeDefinitions = new HashMap<String,CLoadedAttributeDefinition>();

    private final Map<String,CPredefinedAttributeDefinition> cPredefinedAttributeDefinitions = new HashMap<String,CPredefinedAttributeDefinition>();

    private final Map<String,CComputedAttributeDefinition> cComputedAttributeDefinitions = new HashMap<String,CComputedAttributeDefinition>();





    public CComponentDefinitionImplAbs(List<CLoadedAttributeDefinition> loadedAttributeDefinitionsTmp, List<CPredefinedAttributeDefinition> predefinedAttributeDefinitionsTmp, List<CComputedAttributeDefinition> computedAttributeDefinitionsTmp) {
        if(loadedAttributeDefinitionsTmp!=null)
        for (CLoadedAttributeDefinition loadedAttributeDefinition : loadedAttributeDefinitionsTmp) cLoadedAttributeDefinitions.put(loadedAttributeDefinition.getKey(),loadedAttributeDefinition);

        if(predefinedAttributeDefinitionsTmp!=null)
        for (CPredefinedAttributeDefinition predefinedAttributeDefinition : predefinedAttributeDefinitionsTmp) cPredefinedAttributeDefinitions.put(predefinedAttributeDefinition.getKey(),predefinedAttributeDefinition);

        if(computedAttributeDefinitionsTmp!=null)
        for (CComputedAttributeDefinition computedAttributeDefinition : computedAttributeDefinitionsTmp) cComputedAttributeDefinitions.put(computedAttributeDefinition.getKey(),computedAttributeDefinition);


    }



    @Override
    public Map<String, CLoadedAttributeDefinition> getCLoadedAttributeDefinitions() {
        return Collections.unmodifiableMap(cLoadedAttributeDefinitions);
    }

    @Override
    public Map<String, CPredefinedAttributeDefinition> getCPredefinedAttributeDefinitions() {
        return Collections.unmodifiableMap(cPredefinedAttributeDefinitions);
    }

    @Override
    public Map<String, CComputedAttributeDefinition> getCComputedAttributeDefinitions() {
        return Collections.unmodifiableMap(cComputedAttributeDefinitions);
    }

    /*
        returns the attribute definition for a given key
        null if there is no attribute definition for a specific key
         */





    @Override
    @Nullable
    public CAttributeDefinition getCAttributeDefinition(@NotNull String attributeKey) {

        if (this.cLoadedAttributeDefinitions.get(attributeKey) != null) {
            return this.cLoadedAttributeDefinitions.get(attributeKey);
        } else if (this.cPredefinedAttributeDefinitions.get(attributeKey) != null) {
            return this.cPredefinedAttributeDefinitions.get(attributeKey);
        } else if (this.cComputedAttributeDefinitions.get(attributeKey) != null) {
            return this.cComputedAttributeDefinitions.get(attributeKey);
        } else {
            return null;
        }
    }
    @Override
    @Nullable
    public CLoadedAttributeDefinition getCLoadedAttributeDefinition(@NotNull String attributeKey)
    {
            return this.cLoadedAttributeDefinitions.get(attributeKey);
    }
    @Override
    @Nullable
    public CPredefinedAttributeDefinition getCPredefinedAttributeDefinition(@NotNull String attributeKey) {

            return this.cPredefinedAttributeDefinitions.get(attributeKey);
    }
    @Override
    @Nullable
    public CComputedAttributeDefinition getCComputedAttributeDefinition(@NotNull String attributeKey) {

            return this.cComputedAttributeDefinitions.get(attributeKey);
    }



    //for JAXB

    private CComponentDefinitionImplAbs() {

    }

    @XmlElementRefs({@XmlElementRef(type=CLoadedAttributeDefinitionImpl.class)} )
    private List<CLoadedAttributeDefinition> getCLoadedAttributeDefinitionsAsList()
    {
        return new ArrayList<CLoadedAttributeDefinition>(this.cLoadedAttributeDefinitions.values());
    }

    private void setCLoadedAttributeDefinitionsAsList(List<CLoadedAttributeDefinition> list)
    {
        for(CLoadedAttributeDefinition cLoadedAttributeDefinition : list)
        {
            this.cLoadedAttributeDefinitions.put(cLoadedAttributeDefinition.getKey(), cLoadedAttributeDefinition);
        }
    }

    @XmlElementRefs({@XmlElementRef(type=CPredefinedAttributeDefinitionImpl.class)} )
    private List<CPredefinedAttributeDefinition> getCPredefinedAttributeDefinitionsForJaxbAsList()
    {
        return new ArrayList<CPredefinedAttributeDefinition>(this.cPredefinedAttributeDefinitions.values());
    }

    private void setCPredefinedAttributeDefinitionsForJaxbAsList(List<CPredefinedAttributeDefinition> list)
    {
        for(CPredefinedAttributeDefinition cPredefinedAttributeDefinition : list)
        {
            this.cPredefinedAttributeDefinitions.put(cPredefinedAttributeDefinition.getKey(), cPredefinedAttributeDefinition);
        }
    }

    @XmlElementRefs({@XmlElementRef(type=CComputedAttributeDefinitionImpl.class)} )
    private List<CComputedAttributeDefinition> getCComputedAttributeDefinitionsForJaxbAsList()
    {
        return new ArrayList<CComputedAttributeDefinition>(this.cComputedAttributeDefinitions.values());
    }

    private void setCComputedAttributeDefinitionsForJaxbAsList(List<CComputedAttributeDefinition> list)
    {
        for(CComputedAttributeDefinition eComputedAttributeDefinition : list)
        {
            this.cComputedAttributeDefinitions.put(eComputedAttributeDefinition.getKey(), eComputedAttributeDefinition);
        }
    }



}
