package org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 12.03.2015.
 */
public class EqualLiteral extends SimilarityMeasureImplAbs {


//gibt einfach 1 oder 0 zurück, wenn zwei werte gleich sind


    //Braucht einen constructor der nur caseSimilarityMeasureConfig hat
    public EqualLiteral(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig) {
        super(caseSimilarityMeasureConfig, "This Similarity Measure compares two literal values if they are equal (result = 1) or not (result = 0). ", "Equal Literal");
    }

    @Override
    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) {
        if(eCaseAttributeOfQueryCase.getValue().equals(eCaseAttributeOfComparisonCase.getValue()))
        {
            return 1;
        }
        else
        {
            return 0;
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

        return true;
    }

    @Override
    public String getDisplayName() {
        return this.getClass().getSimpleName();
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
        return super.getObligatedAttributes();
    }


}
