package org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures;

import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedParameterImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mbehr on 12.03.2015.
 *
 *
 */
public class TimeStampAgeSimilarityMeasure extends SimilarityMeasureImplAbs {

    public TimeStampAgeSimilarityMeasure(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig) {
        super(caseSimilarityMeasureConfig, "Newer cases from the similar case gets a better ranking", "AgeMeasure");
    }

    //gibt einfach 1 oder 0 zurück, wenn zwei werte gleich sind

    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) {

        //bisher nur ältere keine neueren fälle miteinbezihen
         double allowedDistanceInDays =   Double.valueOf(this.getCaseSimilarityMeasureConfig().getCaseSimilarityMeasureConfigParameters().get("distanceInDays").getValue());

        Long distance = ((Timestamp)eCaseAttributeOfQueryCase.getValue()).getTime()- ((Timestamp)eCaseAttributeOfComparisonCase.getValue()).getTime();
        Long distanceInDays =  TimeUnit.MILLISECONDS.toDays(distance);


        if(distanceInDays==1)
        {
            return 1;
        }

        if(distanceInDays<0)
        {

            distanceInDays = distanceInDays*(-1);


        }


        if( distanceInDays<allowedDistanceInDays)
        {

            return 1- (distanceInDays/allowedDistanceInDays);
        }
        else
        {
            return 0;
        }





    }

    @Override
    public boolean isComputable(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) throws NotComputableException {

        if(!eCaseAttributeOfQueryCase.getType().equals("java.sql.Timestamp"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because type of eeCaseAttributeOfQueryCase " + eCaseAttributeOfQueryCase.getKey() + " is not java.sql.Timestamp but '" + eCaseAttributeOfQueryCase.getType() +"'");

        }
        if(!eCaseAttributeOfComparisonCase.getType().equals("java.sql.Timestamp"))
        {
            throw new NotComputableException("Similarity Measure '" + this.getDisplayName() +"' is not computable because type of attribute2 " + eCaseAttributeOfComparisonCase.getKey() + " is not java.sql.Timestamp but '" + eCaseAttributeOfComparisonCase.getType() +"'");

        }



        return true;


    }

    @NotNull
    @Override
    public List<CObligatedParameter> getObligatedSimilarityMeasureConfigParameters() {
        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();
        cObligatedParameters.add(new CObligatedParameterImpl("distanceInDays"));
        return cObligatedParameters;
    }

    @NotNull
    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {
        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();
        obligatedAttributes.add(new CObligatedAttributeImpl("startTimestamp",Arrays.asList(new String[]{"java.sql.Timestamp"}), CObligatedAttribute.CMainComponentType.CASE));


        return  obligatedAttributes;
    }
}
