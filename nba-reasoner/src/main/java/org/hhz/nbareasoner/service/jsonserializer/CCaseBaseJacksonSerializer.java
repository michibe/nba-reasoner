package org.hhz.nbareasoner.service.jsonserializer;




import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;


import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class CCaseBaseJacksonSerializer extends JsonSerializer<CCaseBase> {


    @Override
    public void serialize(CCaseBase caseBase, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {


        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("attributes");
        for(CAttribute attribute : caseBase.getCAttributes().values())
        {
            jsonGenerator.writeObject(attribute);
        }
        jsonGenerator.writeEndArray();



        jsonGenerator.writeArrayFieldStart("cases");
        for(CCase wClosedCase : caseBase.getCCases().values())
        {
            jsonGenerator.writeObject(wClosedCase);
        }

        jsonGenerator.writeEndArray();



        jsonGenerator.writeEndObject();

    }
}
