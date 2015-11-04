package org.hhz.nbareasoner.cbr.impl.phase.reuse.nba;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingResult;
import org.hhz.nbareasoner.service.jsonserializer.NbaProposalJacksonSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbehr on 12.03.2015.
 *
 * Activity
 *
 */
@JsonSerialize(using=NbaProposalJacksonSerializer.class)
public class NbaProposal {

    private final CCCase queryCase;
    private final String fromActivityName;
    private final String toActivityName;
    private final List<NbaProposalOccurrence> nbaProposalOccurrences= new ArrayList<NbaProposalOccurrence>();

    //Liste von den ergebnissen der verschiedenen Funktionen für dieses Proposal
    private final List<NbaRatingResult> nbaRatingFunctionResults = new ArrayList<NbaRatingResult>();

    //Die rate wird zu prozent gerechnet
    public double getPreparedRate()
    {
        double rate =0;
        double denominator=0;
        for(NbaRatingResult nbaRatingFunctionResult: nbaRatingFunctionResults)
        {
            //Nur wenn dieses rating überhaupt iwo größer 0 ist, sol ich bei der gesamtverechnung berücksichtigt werden
            if(nbaRatingFunctionResult.getNbaRatingTypeResult().getRatingResultPositiveAccumulated()>0)
            {
                rate = rate + nbaRatingFunctionResult.getPreparedRate();
                denominator= denominator + (nbaRatingFunctionResult.getNbaRatingTypeResult().getUsedWeight()*1);
            }

        }

        if(denominator==0)
        {
            return 0;
        }
        else
        {
            return rate/denominator;
        }

    }

    protected void addNbaRatingResult(NbaRatingResult nbaRatingFunctionResult)
    {
        this.nbaRatingFunctionResults.add(nbaRatingFunctionResult);
    }


    public NbaProposal(String fromActivityName, String toActivityName, CCCase queryCase) {
        this.fromActivityName = fromActivityName;
        this.toActivityName = toActivityName;
        this.queryCase = queryCase;
    }

    protected void addNbaProposalOccurrence(NbaProposalOccurrence nbaProposalOccurrence)
    {
        this.nbaProposalOccurrences.add(nbaProposalOccurrence);
    }


    public String getKey()
    {
        return NbaProposal.createKeyFrom(this.fromActivityName,this.toActivityName);
    }


    public static String createKeyFrom(String fromActivityName, String toActivityName)
    {
        return fromActivityName + "->"+toActivityName;
    }


    @Override
    public String toString() {

        String nbaProposalOccurrencesString = "";

        for(NbaProposalOccurrence nbaProposalOccurrence:nbaProposalOccurrences)
        {
            nbaProposalOccurrencesString =  nbaProposalOccurrencesString + nbaProposalOccurrence.toString() + "\n";
        }

        return "NbaProposal{" +
                "fromActivityName='" + fromActivityName + '\'' +
                ", toActivityName='" + toActivityName + '\'' +
                ", nbaProposalOccurrences=" + nbaProposalOccurrencesString +

                '}';
    }

    public String getFromActivityName() {
        return fromActivityName;
    }

    public String getToActivityName() {
        return toActivityName;
    }

    public List<NbaProposalOccurrence> getNbaProposalOccurrences() {
        return Collections.unmodifiableList(nbaProposalOccurrences);
    }

    public List<NbaRatingResult> getNbaRatingFunctionResults() {
        return Collections.unmodifiableList(nbaRatingFunctionResults);
    }

    public CCCase getQueryCase() {
        return queryCase;
    }
}


