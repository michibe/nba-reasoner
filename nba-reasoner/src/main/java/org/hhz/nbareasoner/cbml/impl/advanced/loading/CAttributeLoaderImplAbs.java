package org.hhz.nbareasoner.cbml.impl.advanced.loading;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.loading.CAttributeLoader;

/**
 * Created by mbehr on 04.05.2015.
 */
public abstract class CAttributeLoaderImplAbs implements CAttributeLoader {

    protected final CLoadingSourceDefinition cLoadingSourceDefinition;

    protected CAttributeLoaderImplAbs(CLoadingSourceDefinition eLoadingSource) {
        this.cLoadingSourceDefinition = eLoadingSource;
    }

    @Override
    public CLoadingSourceDefinition getELoadingSource() {
        return this.cLoadingSourceDefinition;
    }

    @Override
    public boolean isApplicable() throws CNotApplicableException {

        for(CObligatedParameter cObligatedCDefinitionAttribute : this.getObligatedParameters())
        {
            CLoadingSourceParameter cLoadingSourceParameter = this.cLoadingSourceDefinition.getCLoadingSourceParameters().get(cObligatedCDefinitionAttribute.getName());
            if(cLoadingSourceParameter ==null)
            {
                throw new CNotApplicableException("Loading '" + this.cLoadingSourceDefinition.getClazz() + "' not applicable due to missing loadingSourceAttribute '" + cObligatedCDefinitionAttribute+"'");

            }

            if(cObligatedCDefinitionAttribute.getAllowedValues().size()>0 && !cObligatedCDefinitionAttribute.getAllowedValues().contains(cLoadingSourceParameter.getValue())  )
            {


                throw new CNotApplicableException("Loading '" + this.cLoadingSourceDefinition.getClazz() + "' not applicable because value '" + cLoadingSourceParameter.getValue()+"' is not in allowed for obligatedDefinitionAttribute '"+cObligatedCDefinitionAttribute +"'");

            }


        }
        return true;
    }
}
