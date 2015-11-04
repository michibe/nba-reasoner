package org.hhz.nbareasoner.config.impl.advanced.nba.ratings;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.config.model.advanced.nba.ratings.NbaRating;
import org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions.NotApplicableException;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfigParameter;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfigParameter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 */
public abstract class NbaRatingImplAbs implements NbaRating {

    @NotNull
    private final NbaRatingConfig nbaRatingConfig;
    @NotNull
    private final RatingScale ratingScale;
    @NotNull
    private final String description;

    @NotNull
    private final String displayName;

    protected NbaRatingImplAbs(@NotNull NbaRatingConfig nbaRatingConfig, @NotNull RatingScale ratingScale, @NotNull String description, @NotNull String displayName) {
        this.nbaRatingConfig = nbaRatingConfig;
        this.ratingScale = ratingScale;
        this.description = description;
        this.displayName = displayName;
    }

    @Override
    @NotNull
    public NbaRatingConfig getNbaRatingConfig() {
        return nbaRatingConfig;
    }

    @Override
    @NotNull
    public RatingScale getRatingScale() {
        return ratingScale;
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

    public boolean isApplicable(@NotNull CCaseBaseDefinition eCaseBaseDefinition) throws NotApplicableException
    {
        //Check if obligated attributes for Rating are defined
        for(CObligatedAttribute cObligatedAttribute : this.getObligatedAttributes())
        {
            switch (cObligatedAttribute.getForCMainComponentType()) {
                case CASE_BASE: {
                    CAttributeDefinition eAttributeDefinition;
                    if((eAttributeDefinition =eCaseBaseDefinition.getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                    {
                        throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing caseBase-attribute '" + cObligatedAttribute.getKey()+"'");
                    }
                    else
                    {
                        if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for caseBase not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                        }
                    }


                    break;
                }
                case CASE: {
                    CAttributeDefinition eAttributeDefinition;
                    if((eAttributeDefinition =eCaseBaseDefinition.getCCaseDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                    {
                        throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing case-attribute '" + cObligatedAttribute.getKey()+"'");
                    }
                    else
                    {
                        if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for case not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                        }
                    }


                    break;
                }
                case CASE_INCIDENT: {
                    CAttributeDefinition eAttributeDefinition;
                    if(eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition()!=null)
                    {
                        if((eAttributeDefinition =eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing incident(activity)-attribute '" + cObligatedAttribute.getKey()+"'");
                        }
                        else
                        {
                            if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                            {
                                throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                            }
                        }
                    }



                    if(eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition()!=null)
                    {
                        if((eAttributeDefinition =eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing incident(event)-attribute '" + cObligatedAttribute.getKey()+"'");

                        }
                        else
                        {
                            if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                            {
                                throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                            }
                        }
                    }



                    break;
                }
                case ACTIVITY: {
                    CAttributeDefinition eAttributeDefinition;
                    if((eAttributeDefinition =eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                    {
                        throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing activity-attribute '" + cObligatedAttribute.getKey()+"'");
                    }
                    else
                    {
                        if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                        }
                    }

                    break;
                }
                case EVENT: {
                    CAttributeDefinition eAttributeDefinition;
                    if((eAttributeDefinition =eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                    {
                        throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' not applicable due to missing event-attribute '" + cObligatedAttribute.getKey()+"'");
                    }
                    else
                    {
                        if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                        {
                            throw new NotApplicableException("NbaRating '" + this.nbaRatingConfig.getRatingClass() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                        }
                    }

                    break;
                }
            }
        }


        //Check NbaRatingConfig Attributes

        for (CObligatedParameter cObligatedParameter : this.getObligatedNbaRatingConfigParameters()) {


            NbaRatingConfigParameter nbaRatingConfigParameter = this.getNbaRatingConfig().getNbaRatingConfigParameters().get(cObligatedParameter.getName());
            if(nbaRatingConfigParameter ==null)
            {
                throw new NotApplicableException("SimilarityMeasure '" + this.getNbaRatingConfig().getRatingClass() + "' not applicable due to missing Parameter '" + cObligatedParameter+"'");

            }

            if(cObligatedParameter.getAllowedValues().size()>0 && !cObligatedParameter.getAllowedValues().contains(nbaRatingConfigParameter.getValue())  )
            {


                throw new NotApplicableException("SimilarityMeasure '" + this.getNbaRatingConfig().getRatingClass() + "' not applicable because value '" +nbaRatingConfigParameter.getValue()+"' is not in allowed for obligatedDefinitionAttribute '"+cObligatedParameter +"'");

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
