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
import java.util.concurrent.TimeUnit;

/**
 * Created by mbehr on 23.02.2015.
 *
 *
 */
public class CaseDurationComputation extends CAttributeComputationImplAbs implements CCaseAttributeComputation<Long> {


    public CaseDurationComputation(CComputationDefinition eComputationDefinition) {
        super(eComputationDefinition,"calculates the duration from the first until the last incident in millis.");
    }


    public Long compute(CCCase eCase) {



      return getDateDiff((Timestamp) eCase.getLastCIncident().getCAttribute("timestamp").getValue(),(Timestamp) eCase.getFirstCIncident().getCAttribute("timestamp").getValue(),TimeUnit.MILLISECONDS);
    }

    private long getDateDiff(Timestamp timestampEnd, Timestamp timestampStart, TimeUnit timeUnit) {
        long diffInMillies = timestampEnd.getTime() - timestampStart.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }



    @Override
    public  List<CObligatedParameter> getCObligatedParameters() {
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
