package org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.service.jsonserializer.SimilarCaseJacksonSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbehr on 16.03.2015.
 */

@JsonSerialize(using=SimilarCaseJacksonSerializer.class)
public class SimilarCase {

    private final CCase relatedECase;
    private final List<SimilarityMeasureResult> similarityMeasureResults = new ArrayList<SimilarityMeasureResult>();

    //Könnte auch in builder ausgelaggert werden, aber dann habe ich insgesamt zu viele Builder, kostet zu viel Resourcen und ist unnötig
    private double weightedSimilarity;
    private double weightedSimilarityPercentageBase;

    private double unweightedSimilarity;


    public double getWeightedSimilarityAsPercentage()
    {
        return (weightedSimilarity*100 / weightedSimilarityPercentageBase)*100;
    }

    public double getUnweightedSimilarityAsPercentage()
    {
        return (weightedSimilarity*100 /(similarityMeasureResults.size()*100))*100;
    }


    public SimilarCase(CCase relatedECase) {
        this.relatedECase = relatedECase;
    }

    public void addSimilarityMeasureResult(SimilarityMeasureResult simpleSimilarityMeasureFunctionResult)
    {
        this.similarityMeasureResults.add(simpleSimilarityMeasureFunctionResult);
        this.weightedSimilarity = weightedSimilarity+ simpleSimilarityMeasureFunctionResult.getWeightedSimilarity();
        this.weightedSimilarityPercentageBase = this.weightedSimilarityPercentageBase + simpleSimilarityMeasureFunctionResult.getUsedWeight()*100;
        this.unweightedSimilarity = unweightedSimilarity + simpleSimilarityMeasureFunctionResult.getUnweightedSimilarity();
    }


    public CCCase getRelatedCCase() {
        return this.relatedECase;
    }

    public List<SimilarityMeasureResult> getSimilarityMeasureResults() {
        return Collections.unmodifiableList(similarityMeasureResults);
    }

    public double getWeightedSimilarity() {
        return weightedSimilarity;
    }

    public double getUnweightedSimilarity() {
        return unweightedSimilarity;
    }

    @Override
    public String toString() {

        String localSimilarityFunctionResultsString = "";

        for(SimilarityMeasureResult simpleSimilarityMeasureFunctionResult : similarityMeasureResults)
        {
            localSimilarityFunctionResultsString = localSimilarityFunctionResultsString + simpleSimilarityMeasureFunctionResult.toString() + "\n";
        }


        return "SimilarCase{" +
                "relatedECase=" + relatedECase +
                ", similarityMeasureResults=" + localSimilarityFunctionResultsString +
                ", weightedSimilarity=" + weightedSimilarity +
                ", unweightedSimilarity=" + unweightedSimilarity +
                '}';
    }
}
