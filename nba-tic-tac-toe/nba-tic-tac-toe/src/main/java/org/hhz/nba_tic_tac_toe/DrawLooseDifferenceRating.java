package org.hhz.nba_tic_tac_toe;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposalOccurrence;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingImplAbs;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 *
 *
 * Beantwo
 */
public class DrawLooseDifferenceRating extends NbaRatingImplAbs {



    public DrawLooseDifferenceRating(@NotNull NbaRatingConfig nbaRatingConfig) {
        super(nbaRatingConfig, RatingScale.MAX_BETTER, "", "DrawLooseDifferenceRating");
    }


    @Override
    public double rate(NbaProposal nbaProposal) {



        double  winCounter = 0.0;
        double  looseCounter = 0.0;
        double  drawCounter = 0.0;


        double stepsUntilLoose=0;

        for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
        {
           CCCase cCase =  nbaProposalOccurrence.getRelatedSimilarCase().getRelatedCCase();
            //Welcher spieler hatte die aktion ausgeführt

            String winnerOfProposalOccurrence =  (String) nbaProposalOccurrence.getRelatedSimilarCase().getRelatedCCase().getLastCIncident().getCAttribute("result").getValue();

            //Entspricht der winner dem vorgeshclagenen result


            if(winnerOfProposalOccurrence!=null && !winnerOfProposalOccurrence.equals("null"))
            {
                for(CCCaseIncident cCaseIncident : cCase.getCCaseIncidentsAsList())
                {

                    if(cCaseIncident.getCAttribute("name").getValue().equals(nbaProposal.getToActivityName()) && !cCaseIncident.getCAttribute("originator").getValue().equals(winnerOfProposalOccurrence))
                    {
                        stepsUntilLoose = stepsUntilLoose +cCase.getCCaseIncidentsAsList().size();
                        looseCounter = looseCounter + 1;
                        break;
                    } else if(cCaseIncident.getCAttribute("name").getValue().equals(nbaProposal.getToActivityName()) && cCaseIncident.getCAttribute("originator").getValue().equals(winnerOfProposalOccurrence))
                    {
                        winCounter = winCounter + 1;
                        break;
                    }

                }
            }
            else
            {
                drawCounter= drawCounter+1;
            }

            //hat dieser spieler gewonnen ?



        }


        //Die unentschiedenen sollten abgezogen werden

        double result = drawCounter-looseCounter;
        if(result>0)
        {
            return result;
        }
        else
        {
            return 0.0;
        }




     /*
        if((winCounter-looseCounter)<0 && stepsUntilLoose >0)
        {
            return stepsUntilLoose/looseCounter;
        }
        else
        {
            return  1000000000000000.0;
        }*/

    }



    @NotNull
    @Override
    public List<CObligatedParameter> getObligatedNbaRatingConfigParameters() {
        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();

        return cObligatedParameters;
    }




    @NotNull
    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {
       List<CObligatedAttribute> list = super.getObligatedAttributes();

       return list;
    }







}
