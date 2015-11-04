package org.hhz.nbareasoner.service.jsonserializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposalOccurrence;
import org.hhz.nbareasoner.config.impl.advanced.nba.ratings.NbaRatingResult;

import java.io.IOException;

/**
 * Created by m on 23.03.2015.
 *
 */

public class NbaProposalJacksonSerializer extends JsonSerializer<NbaProposal> {

    @Override
    public void serialize(NbaProposal nbaProposal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("fromActivityName", nbaProposal.getFromActivityName());
            jsonGenerator.writeStringField("toActivityName", nbaProposal.getToActivityName());
            jsonGenerator.writeNumberField("preparedRate", nbaProposal.getPreparedRate());

        jsonGenerator.writeArrayFieldStart("nbaRatingFunctionResults");

        for(NbaRatingResult nbaRatingFunctionResult : nbaProposal.getNbaRatingFunctionResults())
        {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("ratingFunction",nbaRatingFunctionResult.getNbaRatingTypeResult().getNbaRating().getDisplayName());

            jsonGenerator.writeBooleanField("displayResultInOverview",nbaRatingFunctionResult.getNbaRatingTypeResult().getNbaRating().getNbaRatingConfig().displayInOverview());
            jsonGenerator.writeNumberField("preparedRate",nbaRatingFunctionResult.getPreparedRate());
            jsonGenerator.writeNumberField("rate",nbaRatingFunctionResult.getRate());
            jsonGenerator.writeNumberField("weight",nbaRatingFunctionResult.getNbaRatingTypeResult().getNbaRating().getNbaRatingConfig().getWeightDefault());
            jsonGenerator.writeNumberField("usedWeight",nbaRatingFunctionResult.getNbaRatingTypeResult().getUsedWeight());
            jsonGenerator.writeEndObject();

        }
        jsonGenerator.writeEndArray();

       /* jsonGenerator.writeArrayFieldStart("nbaProposalOccurrences");

        for(NbaProposalOccurrence nbaProposalOccurrence : nbaProposal.getNbaProposalOccurrences())
        {
           jsonGenerator.writeObject(nbaProposalOccurrence.getRelatedCase().getCAttribute("caseId"));
        }

        jsonGenerator.writeEndArray();*/

        jsonGenerator.writeEndObject();

    }
}
