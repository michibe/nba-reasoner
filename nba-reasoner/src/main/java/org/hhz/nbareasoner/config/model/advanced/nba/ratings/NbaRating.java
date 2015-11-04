package org.hhz.nbareasoner.config.model.advanced.nba.ratings;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaProposal;


import org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions.NotApplicableException;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mbehr on 18.03.2015.
 */
public interface NbaRating {


    //kann einen beliebeigen doublewert zurückgeben, sollte dem echten ergebnis entsprechen... cost rating beispielsweise den kosten
    @NotNull
    public double rate(NbaProposal nbaProposal);

    @NotNull
    public String getDescription();

    @NotNull
    public String getDisplayName();

    @NotNull
    public RatingScale getRatingScale();

    @NotNull
    public NbaRatingConfig getNbaRatingConfig();

    @NotNull
    public List<CObligatedParameter> getObligatedNbaRatingConfigParameters();

    //Welche Attribute insgesatm, also casebase, case oder incident attribute sind notwendig für die berechnungen
    @NotNull
    public List<CObligatedAttribute> getObligatedAttributes();


    public boolean isApplicable(@NotNull CCaseBaseDefinition eCaseBaseDefinition) throws NotApplicableException;


    public enum RatingScale
    {
        MIN_BETTER, MAX_BETTER;
    }
}
