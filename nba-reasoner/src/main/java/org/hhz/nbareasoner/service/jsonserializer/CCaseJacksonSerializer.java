package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbml.model.base.data.*;


import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class CCaseJacksonSerializer extends JsonSerializer<CCase> {


    @Override
    public void serialize(CCase wClosedCase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {


        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("attributes");
        for(CAttribute attribute : wClosedCase.getCAttributes().values())
        {
            jsonGenerator.writeObject(attribute);
        }
        jsonGenerator.writeEndArray();


        jsonGenerator.writeArrayFieldStart("activities");
        for(CCaseIncident eCaseIncident : wClosedCase.getCCaseIncidents().values())
        {
            if(eCaseIncident instanceof CActivity)
            {
                jsonGenerator.writeObject(eCaseIncident);
            }

        }



        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("events");
        for(CCaseIncident eCaseIncident : wClosedCase.getCCaseIncidents().values())
        {
            if(eCaseIncident instanceof CEvent)
            {
                jsonGenerator.writeObject(eCaseIncident);
            }

        }



        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();




    }
}
