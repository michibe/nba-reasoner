package org.hhz.nbareasoner.cbml.impl.base.casebase;


import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseAttributeComputation;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseIncidentAttributeComputation;
import org.hhz.nbareasoner.util.MapList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.util.*;


/**
 * Created by mbehr on 27.04.2015.
 */

public class CCaseBaseImpl extends CComponentImplAbs implements CCaseBase {

    private final static Logger logger = LoggerFactory.getLogger(CCaseBaseForJaxb.class);

    private final MapList<String,CCase> eCases = new MapList<>();

    @NotNull
    private final CCaseBaseDefinition eCaseBaseDefinition;

    @NotNull
    private final Cbml parentEComponent;


    public CCaseBaseImpl(@Nullable Map<String, CAttribute> eAttributes, @Nullable MapList<String, CCase> eCases, @NotNull Cbml parentEComponent) {
        super(eAttributes);
        this.parentEComponent = parentEComponent;
        this.eCaseBaseDefinition = parentEComponent.getCDefinition();
        this.eCases.putAll(eCases);

    }



    public void validateAfterPredefinitions() throws CValidationException
    {

    }

    public void validateAfterComputations() throws CValidationException
    {

    }

    public void validateAfterLoadings() throws CValidationException
    {
        //validate if obligated attributes are available
        for(CCase cCase: this.getCCases().values())
        {

            if(cCase.getCAttribute("caseId")==null)
            {
                throw new CValidationException("missing obligated attribute 'caseId' for a case in the caseBase.");
            }

            for(CCaseIncident cCaseIncident : cCase.getCCaseIncidents().values())
            {
                if(cCaseIncident.getCAttribute("incidentId")==null)
                {
                    throw new CValidationException("missing obligated attribute 'incidentId' for a incident in the caseBase.") ;
                }
                if(cCaseIncident.getCAttribute("timestamp")==null)
                {
                    throw new CValidationException("missing obligated attribute 'timestamp' for  incident with incidentID '"+cCaseIncident.getCAttribute("incidentId").getValue()+"' in the caseBase.") ;
                }

                if(cCaseIncident instanceof CActivity)
                {
                    if(cCaseIncident.getCAttribute("name")==null)
                    {
                        throw new CValidationException("missing obligated attribute for  activity with incidentID '"+cCaseIncident.getCAttribute("incidentId").getValue()+"' in the caseBase.") ;
                    }
                }
            }

        }
    }

    @NotNull
    @Override
    public Cbml getParentCComponent() {
        return this.parentEComponent;
    }

    @NotNull
    @Override
    public CCaseBaseDefinition getCDefinition() {
        return this.eCaseBaseDefinition;
    }

    @Override
    @Nullable
    public CCase getEClosedCase(@NotNull String caseId) {



       return  this.eCases.getValue(caseId);
    }

    @Override
    @Nullable
    public CCase getEOpenedCase(@NotNull String caseId) throws CException {

        CCase eCaseOpened = new CCaseImpl(null, CCCase.Status.OPENED,null,this);
        try {
            eCaseOpened.addEAttribute(new CLoadedAttributeImpl("caseId",caseId,eCaseOpened));
        } catch (CException e) {
            throw new CException("Could not create CLoadedAttributeImpl due to " + e.getMessage());

        }

        try {
            CCaseBaseHelper.runOpenedCaseLoadings(eCaseOpened);
            CCaseBaseHelper.runPredefinitionsFor(eCaseOpened);

            eCaseOpened.sort();


            Map<CComputedAttributeDefinition,CCaseAttributeComputation> eCaseAttributeComputationInstances = CCaseBaseHelper.loadECaseAttributeComputations(eCaseBaseDefinition);


            //Berechnungsinstanzen für jeden Incident pro definition (Acitivity und Event) zwischenspeichern

            //Für Activities
            Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseActivityAttributeComputationInstances = CCaseBaseHelper.loadEActivityAttributeComputations(eCaseBaseDefinition);
            //case computations laden und in liste zur wiederverwendung für jeden case speichern

            //Für Events
            Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseEventAttributeComputationInstances = CCaseBaseHelper.loadEEventAttributeComputations(eCaseBaseDefinition);
            //case computations laden und in liste zur wiederverwendung für jeden case speichern



            CCaseBaseHelper.runComputationsFor(eCaseOpened, eCaseAttributeComputationInstances, eCaseActivityAttributeComputationInstances, eCaseEventAttributeComputationInstances);
            return eCaseOpened;
        }
        catch (CException eEXception)
        {

            logger.warn("Could not find openedCase '" + caseId + "' due to: ",eEXception);

            return null;
        }
    }

    @Override
    @Nullable
    public CCase getECase(@NotNull String caseId) throws CException {

        CCase eCase = this.getEClosedCase(caseId);

        if(eCase==null)
        {
            eCase = this.getEOpenedCase(caseId);
        }

      return eCase;
    }

    @NotNull
    @Override
    public List<? extends CCCase> getECasesAsList() {

        return this.eCases.values();
    }

    @Override
    @NotNull
    public MapList<String,CCase> getCCases() {
        return this.eCases;
    }

    @NotNull
    @Override
    public CCaseBase addECase(@NotNull CCase eCase) {

         this.eCases.put(eCase.getCAttribute("caseId").getValue().toString(), eCase);

        return this;
    }

    @Override
    public void renew() throws CException {

        CCaseBaseHelper.runCaseBaseLoadings(this, false);

        this.validateAfterLoadings();

        CCaseBaseHelper.runPredefinitionsFor(this);

        this.validateAfterPredefinitions();

        this.sort();

        CCaseBaseHelper.runComputationsFor(this);

        this.validateAfterComputations();

        logger.info("CaseBase renewed with: " + this.getCCases().size() + " cases");

    }

    @Override
    public void sort() {

        for(CCase cCase :this.eCases.values())
        {
            cCase.sort();
        }
    }



    @Override
    public void serializeToOutputStream(OutputStream outputStream) throws JAXBException {

        CCaseBaseJaxbAdapter cCaseBaseJaxbAdapter = new CCaseBaseJaxbAdapter();


        JAXBContext jaxbContext = JAXBContext.newInstance(CCaseBaseForJaxb.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        //output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try {
            jaxbMarshaller.marshal(cCaseBaseJaxbAdapter.marshal(this), outputStream);
        } catch (Exception e) {

           throw new JAXBException("Could not marshal " + this.getClass().getName() + " due to: ", e);
        }

    }

}
