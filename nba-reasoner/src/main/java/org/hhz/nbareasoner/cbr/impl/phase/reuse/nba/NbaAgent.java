package org.hhz.nbareasoner.cbr.impl.phase.reuse.nba;

import org.hhz.nbareasoner.cbml.model.base.data.CActivity;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarCase;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityResult;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingResult;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingTypeResult;
import org.hhz.nbareasoner.config.model.advanced.nba.ratings.NbaRating;

import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseAgent;

import org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions.NbaException;
import org.hhz.nbareasoner.config.impl.base.FunctionResultFilter;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by mbehr on 17.02.2015.
 *
 *
 * Diese klasse führt alles zusammen und führt die methoden etc aus.
 *
 * Wenn Veränderungen in nba Config vorgenommen wurden, dann rating agent neu instanziieren
 * NBACBRManager
 */


public class NbaAgent implements CbrReuseAgent<SimilarityResult,NbaResult> {

    private final static Logger logger = LoggerFactory.getLogger(CbrReuseAgent.class);

    //TODO Achtung durch abspeichern der vorhandenen ratings  besteht die gefahr, dass durch schlechte programmierung der ratings unnötig ressourcen gebunden werden
    @NotNull
    private final List<NbaRating> nbaRatings = new ArrayList<>();

    public NbaAgent(CCaseBaseDefinition eCaseBaseDefinition, List<NbaRatingConfig> nbaRatingConfigs) {

        try {
            for (NbaRatingConfig nbaRatingConfig : nbaRatingConfigs) {
                Class cls = Class.forName(nbaRatingConfig.getRatingClass());
                Constructor<?> cons = cls.getConstructor(NbaRatingConfig.class);

                NbaRating nbaRating = (NbaRating) cons.newInstance(nbaRatingConfig);
                if (nbaRating.isApplicable(eCaseBaseDefinition)) {
                    this.nbaRatings.add(nbaRating);
                }


            }
        }
        catch (Exception e)
        {
            throw new NbaException("Could not construct NbaAgent due to: ", e);
        }

    }

    @Override
    public NbaResult reuse(@NotNull SimilarityResult similarityResult,@NotNull Map<String,Double> customRatingMeasuresWeights) throws CbrReuseException {
        try
        {
            return this.runRatingFunctions(this.createNbaProposalsFrom(similarityResult),customRatingMeasuresWeights).build();
        }
        catch (Exception e)
        {
            throw new CbrReuseException("Could not create NbaResult due to ", e);
        }
    }



    private NbaResultBuilder createNbaProposalsFrom(SimilarityResult similarityResult)  {

        NbaResultBuilder nbaResultBuilder = new NbaResultBuilder();

        //NBA-Proposals erstellen... über jeden similarCase drüber gehen alls aktionen
        //All Möglichen Kombinationen werden als Vorschlag genommen ... auch wenn diese auf den ersten Blick überhaupt keinen Sinn machen... den Sinn geben Ihnen dann die Ratingfunktionen

        CActivity fromActivity = similarityResult.getQueryCase().getLastCActivity();
        String fromActivityName = fromActivity.getCAttribute("name").getValue().toString();

        //Über Alle ähnliche Fälle drüber und Proposal mit jeder Aktivität erstellen (die events jetzt erstmal als proposal ausen vor lassen, da events keine aktion darstellen und nicht als nba vorgeschlagen werden können)
        for(SimilarCase similarCase: similarityResult.getSimilarCases())
        {

            NbaProposal nbaProposal;
            //dort über alle Aktivitäten drüber und Proposal erstellen
            for(CCCaseIncident eCaseIncident : similarCase.getRelatedCCase().getCCaseIncidentsAsList())
            {

                if(eCaseIncident instanceof CActivity)
                {
                    CActivity toActivity = (CActivity) eCaseIncident;
                    String toActivityName = toActivity.getCAttribute("name").getValue().toString();
                    nbaProposal = nbaResultBuilder.getNbaProposalFor(fromActivityName,toActivityName,similarityResult.getQueryCase(),true);

                    //Wenn der name der aktvität davor dem fromactivityNamen entspricht, gibt eine ein occurance
                    CActivity previousActivity = toActivity.getPreviousCActivity() ;

                    if(previousActivity != null && previousActivity.getCAttribute("name").getValue().equals(fromActivityName))
                    {
                        NbaProposalOccurrence nbaProposalOccurrence = new NbaProposalOccurrence(similarCase,previousActivity,toActivity);
                        nbaProposal.addNbaProposalOccurrence(nbaProposalOccurrence);
                    }

                    //WEnn alle Events durch sind checken ob das letzte event dasselbe wie das active event ist, dannn och einen vorschlag zu null hinzufügen
                    //eher nicht, da es normalerweis immer eine aktion geibt die den prozess beendet, wenn dieselbe aktion ausgeführt wurde sollte der prozess beendet sein

              /* if(i == similarCase.getRelatedCase().getEvents().size()-1 && toEventName.equals(fromEventName) )
                {
                    nbaProposal = nbaResultBuilder.getNbaProposalFor(fromEventName,null,true);
                    NbaProposalOccurrence nbaProposalOccurrence = new NbaProposalOccurrence(similarCase.getRelatedCase(),toEvent,null);
                    nbaProposal.addNbaProposalOccurrence(nbaProposalOccurrence);
                }*/

                }


            }


        }



        return nbaResultBuilder;
    }


    private NbaResultBuilder runRatingFunctions(NbaResultBuilder nbaResultBuilder, @NotNull Map<String,Double> customRatingMeasuresWeights) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        List<NbaRatingTypeResult> nbaRatingTypeResults = new ArrayList<>();

        //Alle NbaProposals durchgehen und alle ratings darauf ausführen
        //für Ratings NbaProposals die den filter nicht passieren, schleife abbrechen und diese nicht in das endergebnis mitaufnehmen
        //bzw. zum löschen markieren und am ende löschen

        //wenn keine ratings vorhanden sind auhc keine proposals zurückgeben
        if(this.nbaRatings.size()==0)
        {
            nbaResultBuilder = new NbaResultBuilder();
        }


        //Type results pro rating ertellen
        for(NbaRating nbaRating : this.nbaRatings)
        {
            Double customWeight = null;
            if(nbaRating.getNbaRatingConfig().getWeightConfigurable())
            {
                //key = attributeKey-measureName  value=0.1
                customWeight = customRatingMeasuresWeights.get("rw-"+nbaRating.getNbaRatingConfig().getRatingClass())    ;

            }

            nbaRatingTypeResults.add(new NbaRatingTypeResult(nbaRating,customWeight));
        }


        for(NbaProposal nbaProposal : nbaResultBuilder.getNbaProposals().values())
        {

            for(NbaRatingTypeResult nbaRatingTypeResult : nbaRatingTypeResults)
            {
               double rate = nbaRatingTypeResult.getNbaRating().rate(nbaProposal);

                //Wird filter passiert ?
                if (FunctionResultFilter.isPassingFilter(nbaRatingTypeResult.getNbaRating().getNbaRatingConfig().getFilterOnRatingResult(), rate)) {

                    NbaRatingResult nbaRatingResult = new NbaRatingResult(rate,nbaRatingTypeResult);
                    nbaProposal.addNbaRatingResult(nbaRatingResult);

                    nbaRatingTypeResult.setHighestOrLowestRate(rate);

                } else {
                    //Filter nicht passiert, measure schleife für diesen case abbrechen und case nicht zum ÄhnlichkeitsErgebnis hinzufügen
                    nbaResultBuilder.markNbaProposalToRemove(nbaProposal);
                    break;
                }


            }

        }

        //Alle proposals die den filter nicht passieren konnten entfernen
        nbaResultBuilder.removeMarkedNbaProposals();


        //Ein zewites mal über alle proposals drüber um die ratings zu verrechnen (dadurch dass pro proposal alle ratings bearbeitet werden muss man nicht so oft loopen)
        //Builder vorbereiten ... Ratingergebnisse verwendbar machen

        //Alle Proposals nochmal durchgehen und für jedes Rating die benötigten endgültigen werte berechnen Mann kann sichergehen dass alle proposal nur drei mal durchlaufen werden, wenn für jedes rating alle proposals durchlaufen werden würden, könnte die iteration um einiges öfter stattfinden

        for(NbaProposal nbaProposal: nbaResultBuilder.getNbaProposals().values())
        {
            //wenn niedrigster wert negativ, dann Alle Werte mit dem niedrigsten +1 addieren, damit es keine negativen werte mehr gibt
            for(NbaRatingResult nbaRatingFunctionResult : nbaProposal.getNbaRatingFunctionResults())
            {

                nbaRatingFunctionResult.prepareRatingResultPositive();

            }

        }

        for(NbaProposal nbaProposal: nbaResultBuilder.getNbaProposals().values())
        {
            //berechne Prozent und bei weniger ist besser dreh den prozentwert um und accumuliere
            for(NbaRatingResult nbaRatingFunctionResult : nbaProposal.getNbaRatingFunctionResults())
            {
                nbaRatingFunctionResult.prepareRatingResultPositivePercentageAndInverted();

            }

        }

        for(NbaProposal nbaProposal: nbaResultBuilder.getNbaProposals().values())
        {
            //berechne Prozent und bei weniger ist besser dreh den prozentwert um und accumuliere
            //Nachdem alle Werte feststehen müssen sie für den globalen vergleich mit ihrem gewicht multipliziert werden

            for(NbaRatingResult nbaRatingFunctionResult : nbaProposal.getNbaRatingFunctionResults())
            {
                nbaRatingFunctionResult.prepareForGlobalRatingComparisonIteration3();

            }

        }


        return nbaResultBuilder;
    }


   // computeNbaOn(XTrace activeCase, XLog CaseBase)


//Alle nbas ausführen
  /*  public void run(RetrieveResult retrieveResult, ratings )
    {
        //Über Alle cases des retrieves result iterieren und nba funktionen ausführen
    }*/






    //Singelton Klasse

  //  Bei erster Instanziierung sollte die baseConfig eingelesen werden

    /**
     * Hier muss die Configuration für die NBA eingelesen werden
     */
   /* public void setConfig(BaseNbaConfig baseNbaConfig)
    {
        //aus basenbaconfig das aliasmapping mit xes-alias verbinden



    }*/


    /**
     * Must be synchronized because later the config can be changed and laoded again via rest
     */











  /*  private void applyConfiguration(CbrConfiguration cbrConfiguration, NbaConfiguration nbaConfiguration)
    {
        //Configuration verstehen und anwenden
    }
*/


    /**
     * Das Ergebnis von Retrieve soll eine Sammlung von caseIds bzw. mit gewichteter Ähnlichkeit sein
     */
  /*  public RetrieveResult retrieve(CaseQuery caseQuery, SimilarityAnalysisConfig similarityConfig)
    {
        //Similarity measures ausführen   SimilarityFunctions
    }


    public ReuseResult reuse(RetrieveResult retrieveResult)
    {
        //Reuse algorithmen ausführen
        Auch dieses Result zwischenspeichern (bei den Results sollen nur die Ids zwischengespeichert werden)

    }

*/
}
