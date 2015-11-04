package org.hhz.nbareasoner.config.impl.advanced.nba.ratings;

import org.hhz.nbareasoner.config.model.advanced.nba.ratings.NbaRating;

/**
 * Created by mbehr on 20.03.2015.
 *
 * Hier stehen allgemiene Informationen zu den Ratings
 *
 *
 * Pro rating (for example cost) wird ein solches result erstellt, damit können gemeinsame werte innerhalb eines ratings f
 *
 * ein rating type für mehrere results, sozusagen eine art klassenvariable, aber nicht ganz, da sie nicht für klasse sondern nur für ein rating gilt
 *
 *
 */

public class NbaRatingTypeResult {

    private final NbaRating nbaRating;
    private final Double customWeight;
    private Double highestRate;
    private Double lowestRate;
    private Double ratingResultPositiveAccumulated =0.0;
    private Double ratingResultPositiveInvertedAccumulated =0.0;

    public NbaRatingTypeResult(NbaRating nbaRating, Double customWeight) {
        this.customWeight = customWeight;
        this.nbaRating = nbaRating;
    }

    public void addToRatingResultPositiveAccumulated(double rate)
    {
        this.ratingResultPositiveAccumulated = this.ratingResultPositiveAccumulated + rate;
    }

    public void addToRatingResultPositiveInvertedAccumulated(double rate)
    {
        this.ratingResultPositiveInvertedAccumulated = this.ratingResultPositiveInvertedAccumulated + rate;
    }



    public void setHighestRate( double highestRate)
    {
        if(this.highestRate==null)
        {
            this.highestRate=highestRate;
        }
        else if(this.highestRate<highestRate)
        {
            this.highestRate = highestRate;
        }
    }

    public void setLowestRate( double lowestRate)
    {
        if(this.lowestRate==null)
        {
            this.lowestRate=lowestRate;
        }
        else if(this.lowestRate>lowestRate)
        {
            this.lowestRate = lowestRate;
        }
    }

    //Add rate as lowest or highest rate
    public void setHighestOrLowestRate(double rate)
    {

        if( lowestRate==null || this.lowestRate>rate)
        {
            //System.out.println("set lowest Rate " + rate);
            this.lowestRate = rate;
        }
        else if(highestRate==null || this.highestRate<rate)
        {
            this.highestRate = rate;
        }


    }

    public Double getHighestRate() {
        return highestRate;
    }

    public Double getLowestRate() {
        return lowestRate;
    }

    public NbaRating getNbaRating() {
        return nbaRating;
    }


    public Double getRatingResultPositiveAccumulated() {
        return ratingResultPositiveAccumulated;
    }

    public Double getRatingResultPositiveInvertedAccumulated() {
        return ratingResultPositiveInvertedAccumulated;
    }


    //Returns the used weight... if custom weight available returns custom weight, if not it returns the defaultWeight of the nbaRating
    public Double getUsedWeight()
    {
        if(this.customWeight==null)
        {
           return this.nbaRating.getNbaRatingConfig().getWeightDefault();
        }
        else
        {
            return this.customWeight;
        }
    }

    public Double getCustomWeight() {
        return customWeight;
    }

    @Override
    public String toString() {
        return "NbaRatingFunctionClassResult{" +
                "nbaRating=" + nbaRating +
                ", highestRate=" + highestRate +
                ", lowestRate=" + lowestRate +
                ", ratingResultPositiveAccumulated=" + ratingResultPositiveAccumulated +
                ", ratingResultPositiveInvertedAccumulated=" + ratingResultPositiveInvertedAccumulated +
                '}';
    }


}
