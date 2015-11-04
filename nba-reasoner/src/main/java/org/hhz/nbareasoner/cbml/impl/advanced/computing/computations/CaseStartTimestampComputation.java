package org.hhz.nbareasoner.cbml.impl.advanced.computing.computations;


import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.computing.CAttributeComputationImplAbs;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseAttributeComputation;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 23.02.2015.
 *
 */
public class CaseStartTimestampComputation extends CAttributeComputationImplAbs implements CCaseAttributeComputation {


    public CaseStartTimestampComputation(CComputationDefinition eComputationDefinition) {
        super(eComputationDefinition,"Retrieves the timestamp of the first incident");
    }


    public Timestamp compute(CCCase ccCase) {


       return (Timestamp)ccCase.getFirstCIncident().getCAttribute("timestamp").getValue();


    }





    @Override
    public    List<CObligatedParameter> getCObligatedParameters() {
        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();

        return cObligatedParameters;
    }


    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {

        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();
        obligatedAttributes.add(new CObligatedAttributeImpl("timestamp", Arrays.asList(new String[]{"java.sql.Timestamp"}) , CObligatedAttribute.CMainComponentType.CASE_INCIDENT));

        return obligatedAttributes;
    }


}
