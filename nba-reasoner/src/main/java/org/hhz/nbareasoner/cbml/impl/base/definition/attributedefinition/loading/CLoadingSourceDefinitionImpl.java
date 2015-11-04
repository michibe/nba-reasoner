package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.loading;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceParameter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Created by mbehr on 27.04.2015.
 */
@XmlRootElement
public class CLoadingSourceDefinitionImpl implements CLoadingSourceDefinition {

    @XmlAttribute(name="class")
    private final  String clazz;


    private final Map<String,CLoadingSourceParameter> cLoadingSourceParameters = new HashMap<>();

    public CLoadingSourceDefinitionImpl(String clazz, List<CLoadingSourceParameter> cLoadingSourceParameters) {
        this.clazz = clazz;
        if(cLoadingSourceParameters !=null)
            for (CLoadingSourceParameter cLoadingSourceParameter : cLoadingSourceParameters) this.cLoadingSourceParameters.put(cLoadingSourceParameter.getKey(), cLoadingSourceParameter);


    }

    public CLoadingSourceDefinitionImpl(String clazz, CLoadingSourceParameter... cLoadingSourceParameters) {
        this.clazz = clazz;
        if(cLoadingSourceParameters !=null)
            for (CLoadingSourceParameter cLoadingSourceParameter : cLoadingSourceParameters) this.cLoadingSourceParameters.put(cLoadingSourceParameter.getKey(), cLoadingSourceParameter);

    }

    private CLoadingSourceDefinitionImpl() {
        this.clazz = null;
    }


    public String getClazz() {
        return clazz;
    }

    @Override
    public Map<String,CLoadingSourceParameter> getCLoadingSourceParameters() {
        return Collections.unmodifiableMap(cLoadingSourceParameters);
    }




    @XmlElement(type=CLoadingSourceParameterImpl.class , name="loadingSourceParameter")
    private List<CLoadingSourceParameter> getELoadingSourceParametersAsList()
    {
        return new ArrayList<CLoadingSourceParameter>(this.cLoadingSourceParameters.values());
    }

    private void setELoadingSourceParametersAsList(List<CLoadingSourceParameter> list)
    {
        for(CLoadingSourceParameter cLoadingSourceParameter : list)
        {
            this.cLoadingSourceParameters.put(cLoadingSourceParameter.getKey(), cLoadingSourceParameter);
        }
    }


}
