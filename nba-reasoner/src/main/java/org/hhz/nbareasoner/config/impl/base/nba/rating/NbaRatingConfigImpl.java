package org.hhz.nbareasoner.config.impl.base.nba.rating;

import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfig;
import org.hhz.nbareasoner.config.model.base.nba.rating.NbaRatingConfigParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NbaRatingConfigImpl implements NbaRatingConfig {

    private final Map<String,NbaRatingConfigParameter> nbaRatingConfigParameters = new HashMap<>();


    @XmlAttribute(name="ratingClass", required = true)
    @NotNull
    private final String ratingClass;

    @XmlAttribute(name="weightDefault", required = false)
    @NotNull
    private final double weightDefault; // default is 1

    @XmlAttribute(name="weightConfigurable")
    @NotNull
    private final boolean weightConfigurable; //default ist false

    @XmlAttribute(name="filterOnRatingResult", required = false)
    @NotNull
    private final String filterOnRatingResult;

    @XmlAttribute(name="displayInOverview", required = false)
    @NotNull
    private final boolean displayInOverview; //default = false


    public NbaRatingConfigImpl(@NotNull String ratingFunctionClass, @NotNull double weightDefault, @NotNull boolean weightConfigurable, @NotNull String filterOnFunctionResult, @NotNull boolean displayInOverview, @Nullable List<NbaRatingConfigParameter> nbaRatingConfigParameters) {
        this.ratingClass = ratingFunctionClass;
        this.weightDefault = weightDefault;
        this.weightConfigurable = weightConfigurable;
        this.filterOnRatingResult = filterOnFunctionResult;
        this.displayInOverview = displayInOverview;
        if(nbaRatingConfigParameters != null)
            for (NbaRatingConfigParameter nbaRatingConfigAttribute : nbaRatingConfigParameters) this.nbaRatingConfigParameters.put(nbaRatingConfigAttribute.getKey(),nbaRatingConfigAttribute);

    }

    @NotNull
    public Map<String, NbaRatingConfigParameter> getNbaRatingConfigParameters() {
        return Collections.unmodifiableMap(nbaRatingConfigParameters);
    }

    @NotNull
    public String getRatingClass() {
        return ratingClass;
    }

    @NotNull
    public double getWeightDefault() {
        return weightDefault;
    }

    @NotNull
    public boolean getWeightConfigurable() {
        return this.weightConfigurable;
    }

    @NotNull
    public String getFilterOnRatingResult() {
        return filterOnRatingResult;
    }

    @NotNull
    @Override
    public boolean displayInOverview() {
        return this.displayInOverview;
    }

    //for JAXB

    private NbaRatingConfigImpl() {
        this.ratingClass = null;
        this.weightDefault = 1.0;
        this.filterOnRatingResult = "0";
        this.weightConfigurable = false;
        this.displayInOverview = false;
    }



    //For Jaxb

    @XmlElement(type=NbaRatingConfigAttributeImpl.class , name="nbaRatingConfigParameter")
    private List<NbaRatingConfigParameter> getNbaRatingConfigParametersAsList()
    {
        return new ArrayList<NbaRatingConfigParameter>(this.nbaRatingConfigParameters.values());
    }

    private void setNbaRatingConfigParametersAsList(List<NbaRatingConfigParameter> list)
    {
        for(NbaRatingConfigParameter nbaRatingConfigAttribute : list)
        {
            this.nbaRatingConfigParameters.put(nbaRatingConfigAttribute.getKey(), nbaRatingConfigAttribute);
        }
    }
}
