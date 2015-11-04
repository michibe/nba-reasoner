package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarCase;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityResult;


import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class SimilarityResultJacksonSerializer extends JsonSerializer<SimilarityResult> {


    @Override
    public void serialize(SimilarityResult similarityResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("comparisonCase");
    jsonGenerator.writeObject(similarityResult.getQueryCase());

        jsonGenerator.writeArrayFieldStart("similarCases");
        for(SimilarCase similarCase :similarityResult.getSimilarCases())
        {
            jsonGenerator.writeObject(similarCase);
        }

      jsonGenerator.writeEndArray();


    jsonGenerator.writeEndObject();





    }
}
