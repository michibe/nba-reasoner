package org.hhz.nbareasoner.config.impl.advanced.cbr.similaritymeasures;


import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceParameter;
import org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions.NotApplicableException;
import org.hhz.nbareasoner.config.model.advanced.cbr.similaritymeasures.SimilarityMeasure;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfigParameter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mbehr on 12.03.2015.
 *
 * Simple Similarity function ist für normale Attribute gedacht dann gibt es noch komplexe attribute wie liste und container oder sowas
 */
public abstract class SimilarityMeasureImplAbs implements SimilarityMeasure {


    @NotNull
    private final CaseSimilarityMeasureConfig caseSimilarityMeasureConfig;

    @NotNull
    private final String description;

    @NotNull
    private final String displayName;

    protected SimilarityMeasureImplAbs(@NotNull CaseSimilarityMeasureConfig caseSimilarityMeasureConfig, @NotNull String description, @NotNull String displayName) {
        this.caseSimilarityMeasureConfig = caseSimilarityMeasureConfig;
        this.description = description;
        this.displayName = displayName;
    }

    @Override
    @NotNull
    public CaseSimilarityMeasureConfig getCaseSimilarityMeasureConfig() {
        return caseSimilarityMeasureConfig;
    }

    @Override
    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public boolean isApplicable(@NotNull CCaseBaseDefinition eCaseBaseDefinition) throws NotApplicableException {
        //Check if obligated attributes for Rating are defined
        for (CObligatedAttribute cObligatedAttribute : this.getObligatedAttributes()) {
            switch (cObligatedAttribute.getForCMainComponentType()) {
                case CASE_BASE: {
                    CAttributeDefinition eAttributeDefinition;
                    if ((eAttributeDefinition = eCaseBaseDefinition.getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                        throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing caseBase-attribute '" + cObligatedAttribute.getKey() + "'");
                    } else {
                        if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for caseBase not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                        }
                    }


                    break;
                }
                case CASE: {
                    CAttributeDefinition eAttributeDefinition;
                    if ((eAttributeDefinition = eCaseBaseDefinition.getCCaseDefinition().getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                        throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing case-attribute '" + cObligatedAttribute.getKey() + "'");
                    } else {
                        if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for case not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                        }
                    }


                    break;
                }
                case CASE_INCIDENT: {
                    CAttributeDefinition eAttributeDefinition;
                    if (eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition() != null) {
                        if ((eAttributeDefinition = eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing incident(activity)-attribute '" + cObligatedAttribute.getKey() + "'");
                        } else {
                            if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                                throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                            }
                        }
                    }


                    if (eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition() != null) {
                        if ((eAttributeDefinition = eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing incident(event)-attribute '" + cObligatedAttribute.getKey() + "'");

                        } else {
                            if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                                throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                            }
                        }
                    }


                    break;
                }
                case ACTIVITY: {
                    CAttributeDefinition eAttributeDefinition;
                    if ((eAttributeDefinition = eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                        throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing activity-attribute '" + cObligatedAttribute.getKey() + "'");
                    } else {
                        if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                        }
                    }

                    break;
                }
                case EVENT: {
                    CAttributeDefinition eAttributeDefinition;
                    if ((eAttributeDefinition = eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey())) == null) {
                        throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' not applicable due to missing event-attribute '" + cObligatedAttribute.getKey() + "'");
                    } else {
                        if (!cObligatedAttribute.getType().contains(eAttributeDefinition.getType())) {
                            throw new NotApplicableException("SimilarityMeasure '" + this.caseSimilarityMeasureConfig.getSimilarityMeasureClass() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType() + "' for obligated attribute '" + cObligatedAttribute.getKey() + "'");

                        }
                    }

                    break;
                }
            }
        }


        //Check if obligated parameters are available

        for (CObligatedParameter cObligatedParameter : this.getObligatedSimilarityMeasureConfigParameters()) {


            CaseSimilarityMeasureConfigParameter caseSimilarityMeasureConfigParameter = this.getCaseSimilarityMeasureConfig().getCaseSimilarityMeasureConfigParameters().get(cObligatedParameter.getName());
            if(caseSimilarityMeasureConfigParameter ==null)
            {
                throw new NotApplicableException("SimilarityMeasure '" + this.getCaseSimilarityMeasureConfig().getSimilarityMeasureClass() + "' not applicable due to missing Parameter '" + cObligatedParameter+"'");

            }

            if(cObligatedParameter.getAllowedValues().size()>0 && !cObligatedParameter.getAllowedValues().contains(caseSimilarityMeasureConfigParameter.getValue())  )
            {


                throw new NotApplicableException("SimilarityMeasure '" + this.getCaseSimilarityMeasureConfig().getSimilarityMeasureClass() + "' not applicable because value '" +caseSimilarityMeasureConfigParameter.getValue()+"' is not in allowed for obligatedDefinitionAttribute '"+cObligatedParameter +"'");

            }




        }
            return true;
    }


    @NotNull
    @Override
    public List<CObligatedAttribute> getObligatedAttributes() {
        List<CObligatedAttribute> obligatedAttributes = new ArrayList<>();

        return  obligatedAttributes;
    }

    }


