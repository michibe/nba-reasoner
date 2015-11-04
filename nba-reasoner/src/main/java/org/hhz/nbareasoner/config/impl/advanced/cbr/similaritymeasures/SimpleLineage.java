package org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures;

import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 12.03.2015.
 *
 *
 * Gibt es die aktuelle Aktion (letzte des queryObject)? wenn ja wird von der uzurückgegeangen und geshcut wieviele aktionen sind gleich
 *
 * /7TODO lineage berechnung komplizierter machen... was macht sinn
 */
public class SimpleLineage extends SimilarityMeasureImplAbs {

    public SimpleLineage(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig) {
        super(caseSimilarityMeasureConfig, "Compares the Lineage in a simple manner. Checks how many past activities from the active activity are the same.", "Simple Lineage");
    }

    //gibt einfach 1 oder 0 zurück, wenn zwei werte gleich sind

    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) {
        String [] eCaseAttributeOfQueryCaseLineage = ((String)eCaseAttributeOfQueryCase.getValue()).split(";");
        String [] eCaseAttributeOfComparisonCaseLineage = ((String)eCaseAttributeOfComparisonCase.getValue()).split(";");

        //System.out.println("caseLineagere " + String.valueOf(caseObject));
        // System.out.println("queryLineage " + String.valueOf(queryObject));


            //TODO so far  a second activity with the same name in the comparison case it is not evaluated
        //TODO just check how many activities equal to the last activity of query case, and run the procedure for ever
        int similarity = 0;


        String queryActivity = eCaseAttributeOfQueryCaseLineage[ eCaseAttributeOfQueryCaseLineage.length-1];

        for(int i=eCaseAttributeOfComparisonCaseLineage.length-1;i>= 0;i--)
        {
            String compareActivity = eCaseAttributeOfComparisonCaseLineage[i];

            if(compareActivity.equals(queryActivity))
            {
                similarity= similarity +1;
                if((eCaseAttributeOfQueryCaseLineage.length-1-similarity)>=0)
                {
                    queryActivity = eCaseAttributeOfQueryCaseLineage[eCaseAttributeOfQueryCaseLineage.length-1-similarity];
                }
                else
                {
                    break;
                }

            }else if(similarity>0)
            {
                break;
            }
        }

        return (double) similarity / eCaseAttributeOfQueryCaseLineage.length;


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
