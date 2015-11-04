package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;
import org.hhz.nbareasoner.cbml.model.base.data.CComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import java.util.*;


/**
 * Created by mbehr on 27.04.2015.
 */


 public abstract class CComponentImplAbs implements CComponent {

    private final static Logger logger = LoggerFactory.getLogger(CComponentImplAbs.class);

    protected final Map<String,CAttribute> eAttributes= new HashMap<>();


    protected CComponentImplAbs(@NotNull Map<String, CAttribute> eAttributes) {
        if(eAttributes!=null)
        this.eAttributes.putAll(eAttributes);

    }

    @Override
public void clearAttributes()
{
    this.eAttributes.clear();
}

    @Override
    public void addEAttribute(@NotNull CAttribute cAttribute)
    {
        this.eAttributes.put(cAttribute.getKey(), cAttribute);
    }

    @Override
    @Nullable
    public CAttribute getCAttribute(String attributeKey)
    {
        return this.eAttributes.get(attributeKey);
    }


    public Map<String,CAttribute> getCAttributes() {
        return Collections.unmodifiableMap(this.eAttributes);
    }

    @NotNull
    @Override
    public List<CAttribute> getCAttributesAsList() {
        return Collections.unmodifiableList(new ArrayList<CAttribute>(this.eAttributes.values()));
    }

//For Jaxb

    protected CComponentImplAbs()
    {

    }

    @XmlElementRefs({@XmlElementRef(type=CLoadedAttributeImpl.class),@XmlElementRef(type=CPredefinedAttributeImpl.class),@XmlElementRef(type=CComputedAttributeImpl.class)} )
    private List<CAttribute> getAttributesForJaxbAsList()
    {

        return new ArrayList<CAttribute>(this.eAttributes.values());
    }

    private void setAttributesForJaxbAsList(List<CAttribute> cAttributes)
    {

        for(CAttribute cAttribute : cAttributes)
        {
            this.eAttributes.put(cAttribute.getKey(), cAttribute);
        }

    }





}
