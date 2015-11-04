package org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity;

import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;

import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.SimilarityMeasureException;
import org.hhz.nbareasoner.config.model.advanced.cbr.similaritymeasures.SimilarityMeasure;


import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveAgent;


import org.hhz.nbareasoner.config.impl.base.FunctionResultFilter;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;


import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by mbehr on 01.04.2015.
 *
 *
 * Agent ist auf eine config festgelegt.. .er lädt bereits die Measures... wenn die config geändert wird müssen die agents neu instanziert werden
 */
public class SimilarityAgent implements CbrRetrieveAgent <SimilarityResult>{

    private final static Logger logger = LoggerFactory.getLogger(SimilarityAgent.class);


    //Load SimilarityMeasures via reflection and store them to use them for every case
    @NotNull
    private final List<SimilarityMeasure> similarityMeasures = new ArrayList<>();


    public SimilarityAgent(@NotNull CCaseBaseDefinition eCaseBaseDefinition, @NotNull List<CaseSimilarityMeasureConfig> caseSimilarityMeasureConfigs) {



        try {
            for (CaseSimilarityMeasureConfig caseSimilarityMeasureConfig : caseSimilarityMeasureConfigs) {
                Class cls = Class.forName(caseSimilarityMeasureConfig.getSimilarityMeasureClass());
                Constructor<?> cons = cls.getConstructor(CaseSimilarityMeasureConfig.class);

                SimilarityMeasure similarityMeasure = (SimilarityMeasure) cons.newInstance(caseSimilarityMeasureConfig);
                if (similarityMeasure.isApplicable(eCaseBaseDefinition)) {
                    this.similarityMeasures.add(similarityMeasure);
                }


            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not construct SimilarityAgent() due to: ", e);
        }

    }

    @Override
    public SimilarityResult retrieve(@NotNull CCaseBase eCaseBase,@NotNull CCase retrieveForCase, @NotNull Map<String,Double> customWeights ) throws CbrRetrieveException {


        try
        {
            return this.retrieveSimilarCasesFor(eCaseBase, retrieveForCase, customWeights);
        }
        catch (Exception e)
        {
            throw new CbrRetrieveException("Could not retrieve similar cases due to: " , e);
        }
    }




    private SimilarityResult retrieveSimilarCasesFor(CCaseBase cCaseBase, CCase eQueryCase,@NotNull Map<String,Double> customWeights) throws Exception {

        SimilarityResultBuilder similarityResultBuilder = new SimilarityResultBuilder(cCaseBase, eQueryCase);

        //Alle Fälle durchgehen und für jeden Fall alle Ähnlichkeitsmethoden durchführen (weniger iteration dadurch)

        for(CCase cCase : cCaseBase.getCCases().values()) {
            //Geholter fall muss nicht mit selben fall verglichen werden
            if (!cCase.equals(eQueryCase))
            {
                //Ähnlicher fallVorschlag erstellen
                SimilarCase similarCase = new SimilarCase(cCase);
            boolean addToSimilarityResultBuilder = true;

            //Jede Ähnlihckeitsfunktion anwenden, wenn das ergebnis den filter nicht passiert, dann iteration abbrechen und fall am ende nicht hinzufügen
            //keine weiteren ähnlichkeitsfunktionen für diesen fall ausführen
            for (SimilarityMeasure similarityMeasure : this.similarityMeasures) {
                CAttribute caseAttributeOfCQueryCase = eQueryCase.getCAttribute(similarityMeasure.getCaseSimilarityMeasureConfig().getAttributeKey());

                //TODO Ähnlichkeitsfunktionen sollen auch auf Attribute anwendbar werden, die nicht immer vorhanden sein müssen.... und wenn diese nicht vorhanden sind wird die ähnlcihkeitsmethode nicht ausgeführt
                //TODO dh. wenn caseAttributeOfCQueryCase == null ist dann sollte ähnlichkeitsfunktion nicht ausgeführt werden
                //Dies soll dazu dienen, dass Ähnlichkeitsattribute auch erst mit fortlaufender entwicklung eines falles hinzukommen können

                CAttribute caseAttributeOfComparisonCase = cCase.getCAttribute(similarityMeasure.getCaseSimilarityMeasureConfig().getAttributeKey());

                //Test if Measurement is computable
                if (similarityMeasure.isComputable(caseAttributeOfCQueryCase, caseAttributeOfComparisonCase)) {

                    //Wenn das gewicht konfigurierbar ist, dann sollen die customWieghts berücksichtigt werden
                    Double customWeight = null;
                    if(similarityMeasure.getCaseSimilarityMeasureConfig().getWeightConfigurable())
                    {
                        //key = attributeKey-measureName  value=0.1
                        customWeight = customWeights.get("smw-"+similarityMeasure.getCaseSimilarityMeasureConfig().getAttributeKey()+"-"+similarityMeasure.getCaseSimilarityMeasureConfig().getSimilarityMeasureClass())    ;

                    }


                    SimilarityMeasureResult similarityMeasureResult = new SimilarityMeasureResult(similarityMeasure.compute(caseAttributeOfCQueryCase, caseAttributeOfComparisonCase), similarityMeasure,customWeight);
                    similarCase.addSimilarityMeasureResult(similarityMeasureResult);

                    if (FunctionResultFilter.isPassingFilter(similarityMeasure.getCaseSimilarityMeasureConfig().getFilterOnSimilarityMeasureResult(), similarityMeasureResult.getUnweightedSimilarity())) {

                    } else {
                        //Filter nicht passiert, measure schleife für diesen case abbrechen und case nicht zum ÄhnlichkeitsErgebnis hinzufügen
                        addToSimilarityResultBuilder = false;
                        break;
                    }


                } else {
                    throw new SimilarityMeasureException("Similarity Measure '" + similarityMeasure.getCaseSimilarityMeasureConfig().getSimilarityMeasureClass() + "' is not computable for the attributes: '" + caseAttributeOfCQueryCase + "' and '" + caseAttributeOfComparisonCase + "'");
                }


            }

            if (addToSimilarityResultBuilder == true) {
                similarityResultBuilder.addSimilarCase(similarCase);
            }

        }
        }

        return similarityResultBuilder.build();
    }








}
