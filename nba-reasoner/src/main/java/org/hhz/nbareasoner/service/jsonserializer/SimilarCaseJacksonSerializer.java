package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarCase;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityMeasureResult;


import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class SimilarCaseJacksonSerializer extends JsonSerializer<SimilarCase> {


    @Override
    public void serialize(SimilarCase similarCase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeStartObject();


        jsonGenerator.writeNumberField("weightedSimilarity",similarCase.getWeightedSimilarity());

        jsonGenerator.writeNumberField("weightedSimilarityAsPercentage",similarCase.getWeightedSimilarityAsPercentage());

        jsonGenerator.writeArrayFieldStart("similarityFunctionResults");
        for(SimilarityMeasureResult simpleSimilarityMeasureFunctionResult : similarCase.getSimilarityMeasureResults())
        {
            jsonGenerator.writeObject(simpleSimilarityMeasureFunctionResult);

        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeFieldName("relatedCase");
        jsonGenerator.writeObject(similarCase.getRelatedCCase());

    jsonGenerator.writeEndObject();





    }
}
