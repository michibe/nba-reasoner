package org.hhz.nba_tic_tac_toe;

import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedAttributeImpl;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures.SimilarityMeasureImplAbs;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by mbehr on 12.03.2015.
 *
 *
 * Es ist egal wie ich hingekommen bin, die die übrig sind müssen gleich sein. Dh. der comparisonCase muss bis zur anzahl des querycases alle  scritte des query cases enthalten
 * die gleich liegen, also spieler x auf x und andersrum
 *
 * /7TODO lineage berechnung komplizierter machen... was macht sinn
 */
public class GameLineage extends SimilarityMeasureImplAbs {

    public GameLineage(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig) {
        super(caseSimilarityMeasureConfig, "Compares the Lineage in a simple manner. Checks how many past activities from the active activity are the same.", "GameLineage");
    }

    //gibt 0 zurück wenn die activitäten nicht entsprechen

    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) {
        String [] eCaseAttributeOfQueryCaseLineage = ((String)eCaseAttributeOfQueryCase.getValue()).split(";");
        String [] eCaseAttributeOfComparisonCaseLineage = ((String)eCaseAttributeOfComparisonCase.getValue()).split(";");





        //wenn das aktuelle spiel bereits mehr Züge hat als das Vergleichsspiel, dann wird es mit keiner ähnlihckeit bewertet, da es für die ratings nicht verwendet werden kann
        if(eCaseAttributeOfComparisonCaseLineage.length<eCaseAttributeOfQueryCaseLineage.length)
        {
            return 0.0;
        }
        else
        {

            //comparisonCase muss alle schritte enthalten

            //Wenn durch dieselbe anzahl von zügen im vergleichsfall nicht derselbe stand hergestellt wurde, wie das aktive spiel besitzt dann ebenfalls keine ähnlichkeit

            String [] eCaseAttributeOfComparisonCaseLineageSameSize = Arrays.copyOfRange(eCaseAttributeOfComparisonCaseLineage,0,eCaseAttributeOfQueryCaseLineage.length);
            List a = Arrays.asList(eCaseAttributeOfQueryCaseLineage);




            if(a.containsAll(Arrays.asList(eCaseAttributeOfComparisonCaseLineageSameSize)))
            {

                return 1.0;
            }
            else
            {

                return 0.0;
            }

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
