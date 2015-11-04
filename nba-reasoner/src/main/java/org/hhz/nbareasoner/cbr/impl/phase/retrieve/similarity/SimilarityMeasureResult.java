package org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.config.model.advanced.cbr.similaritymeasures.SimilarityMeasure;
import org.hhz.nbareasoner.service.jsonserializer.SimpleSimilarityMeasureFunctionResultJacksonSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 13.03.2015.
 */
@JsonSerialize(using=SimpleSimilarityMeasureFunctionResultJacksonSerializer.class)
public class SimilarityMeasureResult {

    //Muss zwischen 0 und 1 liegen
    private final double unweightedSimilarity;
    private final SimilarityMeasure usedSimilarityMeasure;
    @Nullable
    private final Double customWeight;

    public SimilarityMeasureResult(double unweightedSimilarity, SimilarityMeasure usedSimilarityMeasure, @Nullable Double customWeight) throws Exception {
        if(unweightedSimilarity <=1 && unweightedSimilarity >=0)
        {
            this.unweightedSimilarity = unweightedSimilarity;
            this.usedSimilarityMeasure = usedSimilarityMeasure;
            this.customWeight = customWeight;
        }
        else
        {
            //CheckedSystemException mit propertie similarity value // User Message internalException
            throw new Exception("VALUE_WRONG The similarity of the function Result must be between 1 and 0, but is " + unweightedSimilarity);

        }

    }

    public double getWeightedSimilarity()
    {
        if(this.customWeight!=null)
        {
            return unweightedSimilarity * this.customWeight;
        }
        else
        {
            return unweightedSimilarity * usedSimilarityMeasure.getCaseSimilarityMeasureConfig().getWeightDefault();
        }



    }

    public double getWeightedSimilarity(double definedWeight)
    {
        return unweightedSimilarity * definedWeight;
    }

    public double getUnweightedSimilarity() {
        return unweightedSimilarity;
    }

    public SimilarityMeasure getUsedSimilarityMeasure() {
        return this.usedSimilarityMeasure;
    }

    @NotNull
    /**
     * Will return the used weight for the calculation of the weighted similarity
     *
     * Could be the default Weight of the specified measure or the custom weight
     */
    public double getUsedWeight()
    {
        if(this.customWeight!=null)
        {
            return customWeight;
        }
        else
        {
            return this.usedSimilarityMeasure.getCaseSimilarityMeasureConfig().getWeightDefault();
        }
    }

    @Override
    public String toString() {
        return "SimpleSimilarityFunctionResult{" +
                "unweightedSimilarity=" + unweightedSimilarity +
                ", usedSimilarityMeasure=" + usedSimilarityMeasure +
                '}';
    }
}
