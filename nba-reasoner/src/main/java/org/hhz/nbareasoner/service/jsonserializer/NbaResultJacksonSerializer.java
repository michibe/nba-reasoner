package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaResult;

import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class NbaResultJacksonSerializer extends JsonSerializer<NbaResult> {


    @Override
    public void serialize(NbaResult nbaResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();
            jsonGenerator.writeArrayFieldStart("nbaProposals");
        for(NbaProposal nbaProposal : nbaResult.getNbaProposals())
        {
            jsonGenerator.writeObject(nbaProposal);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }
}
