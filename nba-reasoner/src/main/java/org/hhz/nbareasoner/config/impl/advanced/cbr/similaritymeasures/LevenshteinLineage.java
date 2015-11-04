package org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures;

import org.apache.commons.lang3.StringUtils;
import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by mbehr on 12.03.2015.
 *
 *
 * Gibt es die aktuelle Aktion (letzte des queryObject)? wenn ja wird von der uzurückgegeangen und geshcut wieviele aktionen sind gleich
 *
 * /7TODO lineage berechnung komplizierter machen... was macht sinn
 */
public class LevenshteinLineage extends SimilarityMeasureImplAbs {

    public LevenshteinLineage(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig) {
        super(caseSimilarityMeasureConfig, "Compares the edit distance of the two lineages using levenshtein. Checks how many past activities from the active activity are the same.", "Levenshtein Lineage");
    }

    //gibt einfach 1 oder 0 zurück, wenn zwei werte gleich sind

    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) {

        //TODO das eventuell in eine vorbereitende computation auslagern
        //ToDO achtung durch das umwandeln dürfen bisher die lineages nicht länger sein als das scii alphabet
        //Zuerst die strings umwandeln in einfache chars pro aktionstyp ein spezieller char

        String eCaseAttributeOfQueryCaseLineage = (String)eCaseAttributeOfQueryCase.getValue();
        String eCaseAttributeOfComparisonCaseLineage = (String)eCaseAttributeOfComparisonCase.getValue();


        Set<String> differentOccurences = new HashSet<>();

        differentOccurences.addAll(Arrays.asList((eCaseAttributeOfQueryCaseLineage.split(";"))));
        differentOccurences.addAll(Arrays.asList((eCaseAttributeOfComparisonCaseLineage.split(";"))));

        int i =33;
        for(String s : differentOccurences)
        {
            eCaseAttributeOfQueryCaseLineage = eCaseAttributeOfQueryCaseLineage.replace("s",Character.toString((char)i));
            eCaseAttributeOfComparisonCaseLineage = eCaseAttributeOfComparisonCaseLineage.replace("s",Character.toString((char)i));

            i++;
        }




       double levenshteinDistance = (double) StringUtils.getLevenshteinDistance(eCaseAttributeOfComparisonCaseLineage.replace(";",""),eCaseAttributeOfQueryCaseLineage.replace(";",""));


       if(eCaseAttributeOfComparisonCaseLineage.length()<eCaseAttributeOfQueryCaseLineage.length())
       {
           return 1-(levenshteinDistance / eCaseAttributeOfQueryCaseLineage.length());

       } else if(eCaseAttributeOfComparisonCaseLineage.length()>eCaseAttributeOfQueryCaseLineage.length())
       {
           return 1-(levenshteinDistance / eCaseAttributeOfComparisonCaseLineage.length());
       }
        else
       {
           return 1-(levenshteinDistance / eCaseAttributeOfQueryCaseLineage.length());
       }


    }

    @Override
    public boolean isComputable(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) throws NotComputableException {

        if(!eCaseAttributeOfQueryCase.getType().equals("java.lang.String"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because type of eeCaseAttributeOfQueryCase " + eCaseAttributeOfQueryCase.getKey() + " is not java.lang.String but '" + eCaseAttributeOfQueryCase.getType() +"'");

        }
        if(!eCaseAttributeOfComparisonCase.getType().equals("java.lang.String"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because type of attribute2 " + eCaseAttributeOfComparisonCase.getKey() + " is not java.lang.String but '" + eCaseAttributeOfComparisonCase.getType() +"'");

        }

        if(!eCaseAttributeOfQueryCase.getKey().equals("lineage"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because key of eeCaseAttributeOfQueryCase is not lineage but '" + eCaseAttributeOfQueryCase.getKey() +"'");

        }

        if(!eCaseAttributeOfComparisonCase.getKey().equals("lineage"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because key of attribute2 is not lineage but '" + eCaseAttributeOfComparisonCase.getKey() +"'");
        }

        return true;


    }

    @NotNull
    @Override
    public List<CObligatedParameter> getObligatedSimilarityMeasureConfigParameters() {
        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();

        return cObligatedParameters;
    }





    @NotNull
    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {
        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();
        obligatedAttributes.add(new CObligatedAttributeImpl("lineage", Arrays.asList(new String[]{"java.lang.String"}), CObligatedAttribute.CMainComponentType.CASE));


        return  obligatedAttributes;
    }
}
