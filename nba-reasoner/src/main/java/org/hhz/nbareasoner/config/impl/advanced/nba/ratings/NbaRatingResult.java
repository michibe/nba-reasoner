package org.hhz.nbareasoner.config.impl.advanced.nba.ratings;

import org.hhz.nbareasoner.config.model.advanced.nba.ratings.NbaRating;

/**
 * Created by mbehr on 18.03.2015.
 */
public class NbaRatingResult {

    //ContextObjekte für ein Rating speichern

    //Dieses Typ result teilen sich alle Ratings eines bestimmten Rating-typen... zum Beispiel rating cost ...
    private final NbaRatingTypeResult nbaRatingTypeResult;

    //was ist der höchste Wert für dieses rating

    //Was ist der niedrigste wert für dieses Rating


    public NbaRatingTypeResult getNbaRatingTypeResult() {
        return nbaRatingTypeResult;
    }

    //TODO rename to rawRate
    private final double rate; //Kann jeder x beliebige wert sein
    private double preparedRate;

    public NbaRatingResult(double rate, NbaRatingTypeResult nbaRatingTypeResult) {

        this.rate = rate;
        this.preparedRate = rate;
        this.nbaRatingTypeResult = nbaRatingTypeResult;
        //Rating Info Objekt ergänzen


    }



    //Alle werte zu positiven werten machen... dh. den negativsten wert nehmen und dann akkumulieren
    public void prepareRatingResultPositive()
    {
        if(this.nbaRatingTypeResult.getLowestRate()<0)
        {
            this.preparedRate = preparedRate + (1-this.nbaRatingTypeResult.getLowestRate());
        }


        //Indem ich jedem immer 1 hinzufüge kann ich sicher gehen, dass später  nicht durch eins geteilt wird, da jedem eins hinzugefügt bleibt das verhältnis gleich
      //  this.preparedRate = preparedRate +1;




        this.nbaRatingTypeResult.addToRatingResultPositiveAccumulated(this.preparedRate);

    }

    //prozent ausrechnen und falls minBest, dann Prozent wert umkehren und akkumulieren
    public void prepareRatingResultPositivePercentageAndInverted()
    {
        //Prozent ausrechnen

        if(this.nbaRatingTypeResult.getRatingResultPositiveAccumulated()>0)
        {
            this.preparedRate = this.preparedRate / this.nbaRatingTypeResult.getRatingResultPositiveAccumulated();

        }
        else
        {
          //  this.preparedRate = this.preparedRate / this.nbaRatingTypeResult.getRatingResultPositiveAccumulated();

        }





                //WEnn weniger besser gerankt werden soll dann prozent umdrehen zb. wird aus 12% = 88%
                if(this.nbaRatingTypeResult.getNbaRating().getRatingScale().equals(NbaRating.RatingScale.MIN_BETTER) && this.preparedRate!=0 && this.preparedRate !=1)
                {
                    //System.out.println("Yes min better prepared rate before " + this.preparedRate);
                    this.preparedRate = 1 - this.preparedRate;
                    this.nbaRatingTypeResult.addToRatingResultPositiveInvertedAccumulated(this.preparedRate);
                    //System.out.println("Yes min better add " + this.preparedRate+ " to stage2");
                }

    }

    //Falls min mehr, dann den prepared value nochmal als prozent zu allen anderen % werten ausdrücken
    //Zudem schonmal mit gewichtung multiplizieren
    public void prepareForGlobalRatingComparisonIteration3()
    {
        if(this.nbaRatingTypeResult.getNbaRating().getRatingScale().equals(NbaRating.RatingScale.MIN_BETTER)) {
            //Prozent ausrechnen Achtung es kann hier passieren dass durch 0 geteilt wird, wenn es nur einen Vorschlag gibt
            //dann sollte dieser Vorschlag eins ergeben
            if(this.nbaRatingTypeResult.getRatingResultPositiveInvertedAccumulated()==0)
            {
                //this.preparedRate = 1.0;

            }
            else
            {
                this.preparedRate = this.preparedRate / this.nbaRatingTypeResult.getRatingResultPositiveInvertedAccumulated();
                System.out.println("5 " + this.preparedRate );
            }

        }


        if(this.preparedRate!=0)
        {
            this.preparedRate = this.preparedRate*this.nbaRatingTypeResult.getUsedWeight();
        }


           // System.out.println(this.nbaRatingTypeResult);
    }


    public double getRate() {
        return rate;
    }

    public double getPreparedRate() {
        return preparedRate;
    }



    @Override
    public String toString() {
        return "NbaRatingFunctionResult{" +
                "rate=" + rate +
                ", preparedRate=" + preparedRate +
                '}';
    }
}
