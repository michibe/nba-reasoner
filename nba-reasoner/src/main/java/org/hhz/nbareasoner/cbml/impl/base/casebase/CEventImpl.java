package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.definition.CEventDefinition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;


/**
 * Created by mbehr on 30.04.2015.
 */
@XmlRootElement(name="event")
public class CEventImpl extends CCaseIncidentImpl implements CEvent {

    private final static Logger logger = LoggerFactory.getLogger(CComponentImplAbs.class);

    @NotNull
    private CEventDefinition eEventDefinition;


    public CEventImpl(@NotNull Map<String, CAttribute> eAttributes, @NotNull CCase parentCase) {
        super(eAttributes,parentCase);
        this.eEventDefinition = this.parentCase.getCDefinition().getEEventDefinition();

    }



    @Override
    public CEventDefinition getCDefinition() {
        return this.eEventDefinition;
    }

    @Nullable
    @Override
    public CEvent getNextEEvent() {

        CCaseIncident eCaseIncident = this;
        while(true)
        {
            eCaseIncident = this.getParentCComponent().getCCaseIncidents().getNextValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
            if(eCaseIncident == null)
            {
                return null;
            } else if(eCaseIncident instanceof CEvent)
            {
                return (CEvent)eCaseIncident;
            }
        }

    }

    @Nullable
    @Override
    public CEvent getPreviousEEvent() {

        CCaseIncident eCaseIncident = this;
        while(true)
        {
            eCaseIncident = this.getParentCComponent().getCCaseIncidents().getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
            if(eCaseIncident == null)
            {
                return null;
            } else if(eCaseIncident instanceof CEvent)
            {
                return (CEvent)eCaseIncident;
            }
        }
    }



    //For JAXB

    private CEventImpl() {
        super();


    }
}
