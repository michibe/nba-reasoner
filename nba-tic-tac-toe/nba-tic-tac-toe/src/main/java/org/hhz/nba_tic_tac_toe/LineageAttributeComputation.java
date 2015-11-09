package org.hhz.nba_tic_tac_toe;


import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.computing.CAttributeComputationImplAbs;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseAttributeComputation;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 23.02.2015.
 *
 * Constructs a String based Lineage of all the Events per case in the format eventKey;eventKey;eventKey
 */
public class LineageAttributeComputation extends CAttributeComputationImplAbs implements CCaseAttributeComputation {


    public LineageAttributeComputation(CComputationDefinition eComputationDefinition) {
        super(eComputationDefinition,"Summarizes all incident-names and the playing player in one line seperated via ;");
    }


    public String compute(CCCase eCase) {

        String result = "";

        String playerName="Player2";
        for(CCCaseIncident eeCaseIncident: eCase.getCCaseIncidentsAsList())
        {
            if(eeCaseIncident.getCAttribute("originator").getValue().equals("game"))
            {
                playerName="game";
            }
            else
            {

                if(playerName.equals("Player2")||playerName.equals("game"))
                {
                    playerName="Player1";
                }
                else if(playerName.equals("Player1"))
                {
                    playerName="Player2";
                }
            }

            result = result +  eeCaseIncident.getCAttribute("name").getValue()+ "|"+ playerName +";";





        }

      return result;
    }





    @Override
    public    List<CObligatedParameter> getCObligatedParameters() {
        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();

        return cObligatedParameters;
    }


    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {

        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();
        obligatedAttributes.add(new CObligatedAttributeImpl("name", Arrays.asList(new String[]{"java.lang.String"}) , CObligatedAttribute.CMainComponentType.CASE_INCIDENT));
        obligatedAttributes.add(new CObligatedAttributeImpl("originator", Arrays.asList(new String[]{"java.lang.String"}) , CObligatedAttribute.CMainComponentType.CASE_INCIDENT));

        return obligatedAttributes;
    }


}
