package org.hhz.nbareasoner.config.model.advanced.cbr.similaritymeasures;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotApplicableException;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotComputableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfigParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by mbehr on 12.03.2015.
 *
 *
 */
public interface SimilarityMeasure {

    public double compute(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase);
    //query case attribut ist das Attribut des aktiven falles
    public boolean isComputable(@Nullable CCAttribute eCaseAttributeOfQueryCase, @Nullable CCAttribute eCaseAttributeOfComparisonCase) throws NotComputableException;

    @NotNull
    public CaseSimilarityMeasureConfig getCaseSimilarityMeasureConfig();

    @NotNull
    public String getDescription();

    @NotNull
    public String getDisplayName();

    @NotNull
    public List<CObligatedParameter> getObligatedSimilarityMeasureConfigParameters();

    //Welche Attribute insgesatm, also casebase, case oder incident attribute sind notwendig für die berechnungen
    @NotNull
    public List<CObligatedAttribute> getObligatedAttributes();


    public boolean isApplicable(@NotNull CCaseBaseDefinition eCaseBaseDefinition) throws NotApplicableException;

}
