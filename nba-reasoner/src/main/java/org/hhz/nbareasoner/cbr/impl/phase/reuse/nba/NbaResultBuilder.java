package org.hhz.nbareasoner.cbr.impl.phase.reuse.nba;

import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;

import java.util.*;

/**
 * Created by mbehr on 12.03.2015.
 */
public class NbaResultBuilder {

    private final Map<String,NbaProposal> nbaProposals = new HashMap<String,NbaProposal>();
    private final List<NbaProposal> nbaProposalsToRemove = new ArrayList<NbaProposal>();


    public NbaResult build()
    {
        return new NbaResult(this.getAndSortNbaProposals());
    }


    public NbaProposal getNbaProposalFor(String fromActivityName, String toActivityName, CCCase queryCase, boolean createAndAddIfAbsent)
    {
        NbaProposal nbaProposal = this.nbaProposals.get(NbaProposal.createKeyFrom(fromActivityName,toActivityName));

        if(nbaProposal ==null)
        {
            if(createAndAddIfAbsent)
            {
                nbaProposal = new NbaProposal(fromActivityName,toActivityName,queryCase);
                this.nbaProposals.put(nbaProposal.getKey(),nbaProposal);

            }
            else
            {
                return null;
            }
        }
        return nbaProposal;

    }


    //Diese Methoden sollten vorher ausgeführt werden bevor mit dem result gearbeitet wird
    private List<NbaProposal> getAndSortNbaProposals()
    {
        List<NbaProposal> sortedList= new ArrayList<NbaProposal>(nbaProposals.values());
        Collections.sort(sortedList,new Comparator<NbaProposal>() {

            @Override
            public int compare(NbaProposal nbaProposal1, NbaProposal nbaProposal2) {
                if( nbaProposal1.getPreparedRate()>nbaProposal2.getPreparedRate())
                {
                    return -1;
                }
                else if(nbaProposal1.getPreparedRate()==nbaProposal2.getPreparedRate())
                {
                    return 0;
                }
                else if(nbaProposal1.getPreparedRate()<nbaProposal2.getPreparedRate()) {
                    return 1;
                }
                else
                {
                    return -111111111;
                }

            }
        });



        return sortedList;
    }


    @Override
    public String toString() {
        String nbaProposalsString = "";
        for(NbaProposal nbaProposal : nbaProposals.values())
        {
            nbaProposalsString = nbaProposalsString + nbaProposal.toString() + "\n";
        }

        return "NbaResultBuilder{" +
                "nbaProposals=" + nbaProposalsString +
                '}';
    }


    public void markNbaProposalToRemove(NbaProposal nbaProposal)
    {
        this.nbaProposalsToRemove.add(nbaProposal);
    }

    public void removeMarkedNbaProposals()
    {

        for(NbaProposal nbaProposal : this.nbaProposalsToRemove)
        {
            this.nbaProposals.remove(nbaProposal.getKey());
        }

        this.nbaProposalsToRemove.clear();
    }

    public Map<String, NbaProposal> getNbaProposals() {
        return Collections.unmodifiableMap(nbaProposals);
    }


    // Liste sortieren befor sie ins endgültige Result kommt
}
