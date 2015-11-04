package org.hhz.nbareasoner.cbml.impl.advanced.computing.computations;


import org.hhz.nbareasoner.cbml.impl.advanced.computing.CAttributeComputationImplAbs;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseBase;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseAttributeComputation;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseBaseAttributeComputation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 23.02.2015.
 *
 * Counts the incidents
 */
public class CasesIncidentsNumberComputation extends CAttributeComputationImplAbs implements CCaseAttributeComputation, CCaseBaseAttributeComputation {

    //Vorsicht musss public sein !!!
    public CasesIncidentsNumberComputation(CComputationDefinition eComputationDefinition) {
        super(eComputationDefinition,"Counts all incidents, or cases");
    }


    public String compute(CCCase eCase) {

        int number = 0;

        for(CCCaseIncident eeCaseIncident: eCase.getCCaseIncidentsAsList())
        {
            number = number +1;
        }

      return String.valueOf(number);
    }

    @Override
    public String compute(CCCaseBase eCaseBase) {
        int number = 0;

        for(CCCase eCase: eCaseBase.getECasesAsList())
        {
            number = number +1;
        }

        return String.valueOf(number);
    }



    @Override
    public List<CObligatedParameter> getCObligatedParameters() {

        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();



        return cObligatedParameters;


    }


    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {

        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();

        return obligatedAttributes;
    }



}
