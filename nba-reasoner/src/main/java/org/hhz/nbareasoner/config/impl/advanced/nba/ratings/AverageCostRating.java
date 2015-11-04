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
public class AverageCostRating extends NbaRatingImplAbs {



    public AverageCostRating(@NotNull NbaRatingConfig nbaRatingConfig) {
        super(nbaRatingConfig, RatingScale.MIN_BETTER, "Calculates the average Cost of the cases that occur for the NbaProposal.", "Average Cost Rating");
    }

    @Override
    public double rate(NbaProposal nbaProposal) {

//TODO achtung was wenn keine occurences da sind ?   nicht bewerten, kann ich sie so einfach aus bewertung fliegen lassen ? ... alle mit höchstem double wert zurückgeben, heißt schlechtester wert und schlechtester wert nehmen? NoOcurrenceToRateExcpetion

//TODO objekt anstatt einfacher double wert zurückgeben indem nicht ausführbarkeit festgehaten werden kann, wie wird nicht ausführbarkeit weiter beachtet ?
        double accumulatedReserveForAllOccurrences=0;

            for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
              {
                  accumulatedReserveForAllOccurrences = accumulatedReserveForAllOccurrences + Double.valueOf(String.valueOf(nbaProposalOccurrence.getRelatedSimilarCase().getRelatedCCase().getCAttribute("averageCost").getValue()));
              }

        return  accumulatedReserveForAllOccurrences/nbaProposal.getNbaProposalOccurrences().size();
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
        list.add(new CObligatedAttributeImpl("averageCost", Arrays.asList(new String[]{"java.lang.Double"}), CObligatedAttribute.CMainComponentType.CASE));

       return list;
    }







}
