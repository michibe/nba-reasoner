package org.hhz.nbareasoner.cbml.impl.base.casebase;



import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseDefinition;
import org.hhz.nbareasoner.util.MapList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by mbehr on 27.04.2015.
 */

 @XmlRootElement(name="case")
 public class CCaseImpl extends CComponentImplAbs implements CCase {

    private final static Logger logger = LoggerFactory.getLogger(CComponentImplAbs.class);

    @NotNull
    private final CCaseDefinition eCaseDefinition;

    private final MapList<String,CCaseIncident> eCaseIncidents = new MapList<>();

    @NotNull
    private final Status status;


    private final CCaseBase parentEComponent;

    public CCaseImpl(@Nullable Map<String, CAttribute> eAttributes, @NotNull Status status, @Nullable MapList<String, CCaseIncident> eCaseIncidents, @NotNull CCaseBase parentComponent) {
        super(eAttributes);
        this.status = status;
        this.parentEComponent = parentComponent;
        this.eCaseIncidents.putAll(eCaseIncidents);

        this.eCaseDefinition = parentComponent.getCDefinition().getCCaseDefinition();

    }

    @Nullable
    @Override
    public CCaseIncident getCCaseIncident(@NotNull String key)
    {
       return this.eCaseIncidents.getValue(key);
    }


    @Nullable
    @Override
    public CCaseIncident getLastCIncident() {
        return this.eCaseIncidents.getValue(this.eCaseIncidents.size()-1);
    }

    @Nullable
    @Override
    public CCaseIncident getFirstCIncident() {
        return  this.eCaseIncidents.getValue(0);
    }


    @Override
    @Nullable
    public CActivity getLastCActivity() {

        CCaseIncident eCaseIncident = this.eCaseIncidents.getValue(this.eCaseIncidents.size()-1);


        while(true)
        {

            if(eCaseIncident == null)
            {
                return null;
            }
            else if(eCaseIncident instanceof CActivity)
            {
                return (CActivity)eCaseIncident;
            }


            eCaseIncident = this.eCaseIncidents.getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
        }
    }



    @Override
    @Nullable
    public CActivity getFirstCActivity() {

        CCaseIncident eCaseIncident = this.eCaseIncidents.getValue(this.eCaseIncidents.size()-1);


        while(true)
        {

            if(eCaseIncident == null)
            {
                return null;
            }
            else if(eCaseIncident instanceof CActivity)
            {
                return (CActivity)eCaseIncident;
            }


            eCaseIncident = this.eCaseIncidents.getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
        }

    }


    @Override
    @Nullable
    public CEvent getLastCEvent() {

        CCaseIncident eCaseIncident = this.eCaseIncidents.getValue(this.eCaseIncidents.size()-1);


        while(true)
        {

            if(eCaseIncident == null)
            {
                return null;
            }
            else if(eCaseIncident instanceof CEvent)
            {
                return (CEvent)eCaseIncident;
            }


            eCaseIncident = this.eCaseIncidents.getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
        }

    }

    @Override
    @Nullable
    public CEvent getFirstCEvent() {

        CCaseIncident eCaseIncident = this.eCaseIncidents.getValue(this.eCaseIncidents.size()-1);


        while(true)
        {

            if(eCaseIncident == null)
            {
                return null;
            }
            else if(eCaseIncident instanceof CEvent)
            {
                return (CEvent)eCaseIncident;
            }


            eCaseIncident = this.eCaseIncidents.getPreviousValueFor(eCaseIncident.getCAttribute("incidentId").getValue().toString());
        }
    }

    @NotNull
    @Override
    public MapList<String, CCaseIncident> getCCaseIncidents() {
        return this.eCaseIncidents;
    }

    @NotNull
    @Override
    public Status getStatus() {
        return this.status;
    }



    @Override
    public CCaseDefinition getCDefinition() {
        return this.eCaseDefinition;
    }

    @NotNull
    @Override
    public CCaseBase getParentCComponent() {
        return this.parentEComponent;
    }

    @Override
    public CCase addCCaseIncident(@NotNull CCaseIncident eCaseIncident) {
        this.eCaseIncidents.put(eCaseIncident.getCAttribute("incidentId").getValue().toString(), eCaseIncident);
        return this;
    }

    @Override
    public void removeAllIncidents() {
        eCaseIncidents.clear();
    }

    @NotNull
    @Override
    public List<CCaseIncident> getCCaseIncidentsAsList() {
        return this.eCaseIncidents.values();
    }

    @Override
    public void sort() {



       /* this.eCaseIncidents.sort(new Comparator<CCaseIncident>() {
            @Override
            public int compare(CCaseIncident cCaseIncident1, CCaseIncident cCaseIncident2) {
//TODO wenn gleiche id dann probleme weil verteilung von timestamp nicht mehr richtig funktioniert ?
                logger.fine("Compare caseIncident " + cCaseIncident1 + " and caseIncident" + cCaseIncident2);

                //Wenn Incident1 cor incident 2 liegt dann
                try {
                    if (( (Timestamp)cCaseIncident1.getCAttribute("timestamp").getValue()).before(((Timestamp)cCaseIncident2.getCAttribute("timestamp").getValue()))) {
                        return -1;
                    }  //wenn incident 2 vor incident 1 liegt dann
                    else if (( (Timestamp)cCaseIncident2.getCAttribute("timestamp").getValue()).before(( (Timestamp)cCaseIncident1.getCAttribute("timestamp").getValue()))) {
                        return 1;
                    }
                    //wenn incident 1 und incident 2 gleichzeitig waren
                    else {
                        return 0;
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Cannot sort Incidents due to",e);
                }


            }
            });*/

        this.eCaseIncidents.sort(new Comparator<CCaseIncident>() {
            @Override
            public int compare(CCaseIncident cCaseIncident1, CCaseIncident cCaseIncident2) {
//TODO wenn gleiche id dann probleme weil verteilung von timestamp nicht mehr richtig funktioniert ?
                logger.debug("Compare caseIncident " + cCaseIncident1 + " and caseIncident" + cCaseIncident2);




                return ((Timestamp) cCaseIncident1.getCAttribute("timestamp").getValue()).compareTo(((Timestamp) cCaseIncident2.getCAttribute("timestamp").getValue()));


            }

        });





    }


    //for JAXB


    private CCaseImpl()
    {
        eCaseDefinition = null;
        status = null;
        parentEComponent = null;

    }

    @XmlElementRefs({@XmlElementRef(type=CActivityImpl.class),@XmlElementRef(type=CEventImpl.class)} )
    private List<CCaseIncident> getCaseIncidentsForJaxbAsList()
    {

        return Collections.unmodifiableList(this.eCaseIncidents.values());
    }


    private void setCaseIncidentsForJaxbAsList(List<CCaseIncident> list)
    {

        for(CCaseIncident eCaseIncident : list)
        {
            this.eCaseIncidents.put(eCaseIncident.getCAttribute("incidentId").getValue().toString(), eCaseIncident);
        }
    }



    }
