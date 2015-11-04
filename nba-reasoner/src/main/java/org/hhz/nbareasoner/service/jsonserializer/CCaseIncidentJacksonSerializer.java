package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;

import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class CCaseIncidentJacksonSerializer extends JsonSerializer<CCaseIncident> {


    @Override
    public void serialize(CCaseIncident eCaseIncident, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

    jsonGenerator.writeStartObject();

              //  jsonGenerator.writeObjectFieldStart(eCaseIncident.getClass().getInterfaces()[0].getSimpleName());

        jsonGenerator.writeArrayFieldStart("attributes");
        for(CAttribute attribute : eCaseIncident.getCAttributes().values())
        {
            jsonGenerator.writeObject(attribute);
        }

        jsonGenerator.writeEndArray();

        //jsonGenerator.writeEndObject();

jsonGenerator.writeEndObject();





    }
}
