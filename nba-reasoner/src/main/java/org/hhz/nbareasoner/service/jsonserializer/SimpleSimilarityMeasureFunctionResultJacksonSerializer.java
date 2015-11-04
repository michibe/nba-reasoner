package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityMeasureResult;


import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class SimpleSimilarityMeasureFunctionResultJacksonSerializer extends JsonSerializer<SimilarityMeasureResult> {


    @Override
    public void serialize(SimilarityMeasureResult simpleSimilarityMeasureFunctionResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("attributeKey");
        jsonGenerator.writeString(simpleSimilarityMeasureFunctionResult.getUsedSimilarityMeasure().getCaseSimilarityMeasureConfig().getAttributeKey());

        jsonGenerator.writeFieldName("similarityFunction");
        jsonGenerator.writeString(simpleSimilarityMeasureFunctionResult.getUsedSimilarityMeasure().getClass().getSimpleName());

        jsonGenerator.writeFieldName("usedWeight");
        jsonGenerator.writeNumber(simpleSimilarityMeasureFunctionResult.getUsedWeight());

        jsonGenerator.writeFieldName("weightedSimilarity");
        jsonGenerator.writeNumber(simpleSimilarityMeasureFunctionResult.getWeightedSimilarity());

    jsonGenerator.writeEndObject();





    }
}
