package org.hhz.nbareasoner.config.impl.advanced.nba.ratings;

import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposalOccurrence;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 *
 *
 * Beantwo
 */
public class AverageDurationRating extends NbaRatingImplAbs {


    public AverageDurationRating(@NotNull NbaRatingConfig nbaRatingConfig) {
        super(nbaRatingConfig, RatingScale.MIN_BETTER, "Calculates the average duration of the cases that occur for the NbaProposal. The duration should be given in seconds.", "Average Duration Rating");
    }

    @Override
    public double rate(NbaProposal nbaProposal) {


        Long duration = 0L;

        for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
        {
            duration = duration + ((Long)(nbaProposalOccurrence.getRelatedSimilarCase().getRelatedCCase().getCAttribute("duration").getValue()));
        }

        if(nbaProposal.getNbaProposalOccurrences().size()>0)
        {
            return (double) duration/nbaProposal.getNbaProposalOccurrences().size();
        }
        else
        {
            //Was wenn keine vorkommen vorhanden sind?
        //TODO Lösung finden um schlechtesten Wert zurückzugeben... Double max macht keinen sinn, was ist das längste was sein kann ? vllt muss, das als parameter mitangegeben werden
            return 9999999999999999999.0;
        }
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
        list.add(new CObligatedAttributeImpl("duration", Arrays.asList(new String[]{"java.lang.Long"}), CObligatedAttribute.CMainComponentType.CASE));

        return list;
    }


}
