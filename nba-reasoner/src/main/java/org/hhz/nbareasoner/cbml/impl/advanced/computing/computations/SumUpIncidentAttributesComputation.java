package org.hhz.nbareasoner.cbml.impl.advanced.computing.computations;


import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedParameterImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.computing.CAttributeComputationImplAbs;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseAttributeComputation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 23.02.2015.
 *
 *
 */

public class SumUpIncidentAttributesComputation extends CAttributeComputationImplAbs implements CCaseAttributeComputation {

    public SumUpIncidentAttributesComputation(CComputationDefinition eComputationDefinition) {
        super(eComputationDefinition,"Sums-up a specific incident-Attribute defined via the sumAttribute-parameter.");
    }


    public Object compute(CCCase ccCase) {

        Double result =0.0;

        for(CCCaseIncident eeCaseIncident: ccCase.getCCaseIncidentsAsList()   )
        {


            result = result +  ((Double)(eeCaseIncident.getCAttribute(this.eComputationDefinition.getCComputationParameters().get("sumAttribute").getValue()).getValue()));
        }

        return result;
    }





    @Override
    public  List<CObligatedParameter> getCObligatedParameters() {

        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();
        cObligatedParameters.add(new CObligatedParameterImpl("sumAttribute"));



        return cObligatedParameters;
    }


    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {

        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();





        obligatedAttributes.add(new CObligatedAttributeImpl(this.eComputationDefinition.getCComputationParameters().get("sumAttribute").getValue(), Arrays.asList(new String[]{"java.lang.Integer", "java.lang.Double"}), CObligatedAttribute.CMainComponentType.CASE_INCIDENT));

        return obligatedAttributes;
    }

}
