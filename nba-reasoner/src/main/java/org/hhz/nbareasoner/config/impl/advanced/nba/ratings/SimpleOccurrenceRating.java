package org.hhz.nbareasoner.config.impl.advanced.nba.ratings;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposalOccurrence;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 *
 *
 * Zählt die Occurences zusammen
 */
public class SimpleOccurrenceRating extends NbaRatingImplAbs {


    public SimpleOccurrenceRating(@NotNull NbaRatingConfig nbaRatingConfig) {
        super(nbaRatingConfig, RatingScale.MAX_BETTER, "Accumulates the number of cases that occur for a specific NbaProposal (e.g. A -> B)", "Simple Occurrence Rating ");
    }

    @NotNull
    @Override
    public double rate(NbaProposal nbaProposal) {

        Integer occurrence = 0;
      for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
        {
            occurrence = occurrence +1;
        }


        return occurrence;
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
        List <CObligatedAttribute> list = super.getObligatedAttributes();

        return list;
    }
}
