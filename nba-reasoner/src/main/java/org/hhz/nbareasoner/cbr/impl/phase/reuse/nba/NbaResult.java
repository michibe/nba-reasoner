package org.hhz.nbareasoner.cbr.impl.phase.reuse.nba;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseResult;
import org.hhz.nbareasoner.service.jsonserializer.NbaResultJacksonSerializer;

import java.util.Collections;
import java.util.List;

/**
 * Created by mbehr on 12.03.2015.
 */

@JsonSerialize(using=NbaResultJacksonSerializer.class)
public class NbaResult implements CbrReuseResult {

  private final List<NbaProposal> nbaProposals;


  public NbaResult(List<NbaProposal> nbaProposals) {
    this.nbaProposals = nbaProposals;
  }

    public List<NbaProposal> getNbaProposals() {
        return Collections.unmodifiableList(nbaProposals);
    }

    @Override
    public String toString() {
        String nbaProposalsString = "";

        for(NbaProposal nbaProposal : nbaProposals)
        {
            nbaProposalsString = nbaProposalsString +nbaProposal.toString() +"\n";
        }

        return "NbaResult{" +
                "nbaProposals=" + nbaProposalsString +
                '}';
    }
}
