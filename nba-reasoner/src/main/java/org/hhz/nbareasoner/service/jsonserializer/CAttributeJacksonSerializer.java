package org.hhz.nbareasoner.service.jsonserializer;




import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CKeyAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;

import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 */

public class CAttributeJacksonSerializer extends JsonSerializer<CAttribute> {


    @Override
    public void serialize(CAttribute attribute, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("key", attribute.getKey());
        jsonGenerator.writeObjectField("value", attribute.getValue().toString());

        jsonGenerator.writeArrayFieldStart("keyAliasDefinitions");
        for(CKeyAlias cKeyAlias : attribute.getCDefinition().getcKeyAliasDefinitions().values())
        {
            jsonGenerator.writeObject(cKeyAlias);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("valueAliasDefinitions");
        for(CValueAlias cValueAlias : attribute.getCDefinition().getcValueAliasDefinitions().values())
        {
            jsonGenerator.writeObject(cValueAlias);
        }
        jsonGenerator.writeEndArray();




        jsonGenerator.writeEndObject();

    }
}
