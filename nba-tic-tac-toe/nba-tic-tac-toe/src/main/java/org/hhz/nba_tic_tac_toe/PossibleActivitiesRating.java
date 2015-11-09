package org.hhz.nba_tic_tac_toe;

import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposalOccurrence;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingImplAbs;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 *
 *
 * Zählt die Occurences zusammen
 */
public class PossibleActivitiesRating extends NbaRatingImplAbs {


    public PossibleActivitiesRating(@NotNull NbaRatingConfig nbaRatingConfig) {
        super(nbaRatingConfig, RatingScale.MAX_BETTER, "Checks if an proposal is still possible for the active game.", "Possible Activities Rating");
    }

    @NotNull
    @Override
    public double rate(NbaProposal nbaProposal) {


        //Die Aktionen die möglich sind bekommen schon mal mindestens 1 Punkt, Aktionen die möglich sind und bereits durchgeführt wurden bekommen zusätzliche auf basis ihre occurence punkte


        if(nbaProposal.getToActivityName().equals("finish game"))
        {
            return 0.0;
        }


        double occurrence = 0.01;
      for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
        {
            occurrence = occurrence +1;
        }


        //Wenn die Aktion im query case bereits vorhanden ist, ist sie nicht möglich, wenn sie nicht vorhanden ist ist es eine mögliche aktion



        for(CCCaseIncident incident : nbaProposal.getQueryCase().getCCaseIncidentsAsList())
        {
            if(incident.getCAttribute("name").getValue().equals(nbaProposal.getToActivityName()))
            {
               return 0.0;
            }
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
        list.add(new CObligatedAttributeImpl("name", Arrays.asList(new String[]{"java.lang.String"}), CObligatedAttribute.CMainComponentType.CASE_INCIDENT));

        return list;
    }
}
