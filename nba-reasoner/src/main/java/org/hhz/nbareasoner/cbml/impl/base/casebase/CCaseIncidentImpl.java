package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by mbehr on 30.04.2015.
 */
public abstract class CCaseIncidentImpl extends CComponentImplAbs implements CCaseIncident {


    protected final CCase parentCase;

    public CCaseIncidentImpl(Map<String, CAttribute> eAttributes, CCase parentCase) {
        super(eAttributes);
        this.parentCase = parentCase;

    }


    @Override
    @NotNull
    public CCase getParentCComponent() {
        return parentCase;
    }

    @Nullable
    @Override
    public CCaseIncident getNextEIncident() {

            return this.getParentCComponent().getCCaseIncidents().getNextValueFor(this.getCAttribute("incidentId").getValue().toString());

    }

    @Nullable
    @Override
    public CCaseIncident getPreviousEIncident() {
        return this.getParentCComponent().getCCaseIncidents().getPreviousValueFor(this.getCAttribute("incidentId").getValue().toString());

    }


    //For JAXB

    protected CCaseIncidentImpl()
    {
        this.parentCase = null;
    }


    @Override
    public String toString() {



        return "CCaseIncidentImpl{" +
                "parentCase=" + parentCase +
                "attributes=" +  this.getCAttributesAsList().toString()+
                '}';
    }
}
