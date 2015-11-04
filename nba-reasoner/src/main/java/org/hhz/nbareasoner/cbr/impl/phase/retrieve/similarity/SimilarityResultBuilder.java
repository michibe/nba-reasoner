package org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity;

import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by mbehr on 17.02.2015.
 *
 * Bildet die grundlage auf der später die nba berechnet werden kann... alle Fälle die ähnlich dem akutellen fall sind
 * Retrieve result sollte auch leicht serialisierbar sein
 *
 */
public class SimilarityResultBuilder {


    @NotNull
    private final CCaseBase underlyingECaseBase;
    private final Map<CCCase,SimilarCase> similarCases = new HashMap<>();
    private final CCase comparisonECase;



    public SimilarityResultBuilder(@NotNull CCaseBase underlyingECaseBase, @NotNull CCase comparisonECase) {

        this.underlyingECaseBase = underlyingECaseBase;
        this.comparisonECase = comparisonECase;



    }

    public SimilarityResult build()
    {

        return new SimilarityResult(this.underlyingECaseBase,this.comparisonECase,this.getAndOrderSimilarCases());
    }





    //Diese Methoden sollten vorher ausgeführt werden bevor mit dem result gearbeitet wird
    private List<SimilarCase> getAndOrderSimilarCases()
    {
        List<SimilarCase> sortedList= new ArrayList<SimilarCase>(similarCases.values());
        Collections.sort(sortedList,new Comparator<SimilarCase>() {
            @Override
            public int compare(SimilarCase similarCase1, SimilarCase similarCase2) {
                if( similarCase1.getWeightedSimilarity()>similarCase2.getWeightedSimilarity())
                {
                    return -1;
                }
                else if(similarCase1.getWeightedSimilarity()==similarCase2.getWeightedSimilarity())
                {
                    return 0;
                }
                else if(similarCase1.getWeightedSimilarity()<similarCase2.getWeightedSimilarity())
                {
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


   public boolean containsSimilarCaseFor(CCase closedECase)
   {
    return similarCases.containsKey(closedECase);
   }


    public void addSimilarCase(SimilarCase similarCase)
    {
        this.similarCases.put(similarCase.getRelatedCCase(),similarCase);
    }

    public Map<CCCase, SimilarCase> getSimilarCases() {
        return Collections.unmodifiableMap(similarCases);
    }

    @Override
    public String toString() {

        String similarCasesString="";

        for(SimilarCase similarCase : this.similarCases.values())
        {
            similarCasesString = similarCasesString + similarCase.toString() + "\n";
        }

        return "SimilarityResult{" +
                "similarCases=" + similarCasesString +
                '}';
    }




}
