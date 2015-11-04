package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CComputationDefinitionImpl implements CComputationDefinition {

    @XmlAttribute(name="class" ,required = true)
    private final String clazz;

    private final Map<String, CComputationParameter> cComputationParameters = new HashMap<>();

    public CComputationDefinitionImpl(String clazz, List<CComputationParameter> cComputationParameters) {
        this.clazz = clazz;
        if(cComputationParameters != null)
            for (CComputationParameter cComputationParameter : cComputationParameters) this.cComputationParameters.put(cComputationParameter.getKey(), cComputationParameter);


    }
    public CComputationDefinitionImpl(String clazz, CComputationParameter... cComputationParameters) {
        this.clazz = clazz;
        if(cComputationParameters != null)
            for (CComputationParameter cComputationParameter : cComputationParameters) this.cComputationParameters.put(cComputationParameter.getKey(), cComputationParameter);


    }

    private CComputationDefinitionImpl() {
        this.clazz = null;
    }


@Override
    public String getClazz() {
        return clazz;
    }

    @Override
    public Map<String, CComputationParameter> getCComputationParameters() {
        return Collections.unmodifiableMap(this.cComputationParameters);
    }





    //For JAXB


    @XmlElement(type=CComputationParameterImpl.class, name="computationParameter")
    private List<CComputationParameter> getEComputationParametersAsList()
    {
        return new ArrayList<CComputationParameter>(this.cComputationParameters.values());
    }

    private void setEComputationParametersAsList(List<CComputationParameter> list)
    {
        for(CComputationParameter cComputationParameter : list)
        {
            this.cComputationParameters.put(cComputationParameter.getKey(), cComputationParameter);
        }
    }
}
