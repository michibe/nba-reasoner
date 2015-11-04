package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.CActivity;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.data.CAttribute;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.definition.CActivityDefinition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;


/**
 * Created by mbehr on 30.04.2015.
 */
@XmlRootElement(name="activity")
public class CActivityImpl extends CCaseIncidentImpl implements CActivity {

    private final static Logger logger = LoggerFactory.getLogger(CCaseIncidentImpl.class);

    @NotNull
    private final CActivityDefinition eActivityDefinition;

    public CActivityImpl(@Nullable Map<String, CAttribute> eAttributes, @NotNull CCase parentCase) {
        super(eAttributes,parentCase);

        this.eActivityDefinition = this.parentCase.getCDefinition().getEActivityDefinition();

    }


    @Override
    @NotNull
    public CActivityDefinition getCDefinition() {
        return this.eActivityDefinition;
    }

    @Nullable
    @Override
    public CActivity getNextCActivity() {

        CCaseIncident eCaseIncident = this;
        while(true)
        {
            eCaseIncident = this.getParentCComponent().getCCaseIncidents().getNextValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
           if(eCaseIncident == null)
           {
               return null;
           } else if(eCaseIncident instanceof CActivity)
           {
               return (CActivity)eCaseIncident;
           }
        }

    }

    @Nullable
    @Override
    public CActivity getPreviousCActivity() {

        CCaseIncident eCaseIncident = this;
        while(true)
        {
            eCaseIncident = this.getParentCComponent().getCCaseIncidents().getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
            if(eCaseIncident == null)
            {
                return null;
            } else if(eCaseIncident instanceof CActivity)
            {
                return (CActivity)eCaseIncident;
            }
        }
    }






    //for JAXB

    private CActivityImpl() {
        super();
        this.eActivityDefinition = null;



    }

}
