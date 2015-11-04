package org.hhz.nbareasoner.cbml.impl.base.casebase;


import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.util.MapList;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by mbehr on 18.05.2015.
 *
 *
 * Wird benötigt um über jaxb casebase einzulesen. und dann wird neue final casebase erstellt mit den definitionen etc.
 */
@XmlRootElement(name="caseBase")
public class CCaseBaseForJaxb extends CComponentImplAbs {

    private final static Logger logger = LoggerFactory.getLogger(CCaseBaseForJaxb.class);

    private MapList<String,CCase> eCases = new MapList<>();

    private Cbml parentEComponent;
    private CCaseBaseDefinition eCaseBaseDefinition;

    public CCaseBaseForJaxb(CCaseBase eCaseBase)
    {
        this.eCases = eCaseBase.getCCases();
        this.eAttributes.putAll(eCaseBase.getCAttributes());
    }

    /**
     * Methode um die eigentliche Case Basis zu erstellen.
     *
     *
     * @return
     */
    public CCaseBase build() throws CException {
        final long startTime = System.nanoTime();

      CCaseBase eCaseBaseNew = this.createNewECaseBaseFromOldECaseBase(this,this.parentEComponent);
      eCaseBaseNew.sort();

        logger.info("New caseBase built from old caseBase in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " milliseconds.");

        return eCaseBaseNew;
    }

    @NotNull
    private CCaseBase createNewECaseBaseFromOldECaseBase(@NotNull CCaseBaseForJaxb oldECaseBase, Cbml parentEComponent) throws CException {


        CCaseBase eCaseBaseNew = new CCaseBaseImpl(null,null,parentEComponent);

        this.addNewEAttributesToComponent(eCaseBaseNew, oldECaseBase.getCAttributes().values());

        return eCaseBaseNew;
    }


    @NotNull
    private void addNewECasesToCaseBase(@NotNull CCaseBase componentToAdd, @NotNull List<CCase> eCasesToAdd) throws CException {


        for(CCase eCase : eCasesToAdd)
        {
            componentToAdd.addECase(this.createNewECaseFromOldECase(eCase, componentToAdd));

        }

    }

    @NotNull
    private CCase createNewECaseFromOldECase(@NotNull CCase oldECase, CCaseBase parentEComponent) throws CException {

            CCase eCaseNew = new CCaseImpl(null,oldECase.getStatus(),null,parentEComponent);
        this.addNewEAttributesToComponent(eCaseNew,oldECase.getCAttributes().values());

        this.addNewECaseIncidentsToCase(eCaseNew,oldECase.getCCaseIncidents().values());

        return eCaseNew;
        }




    @NotNull
    private void  addNewECaseIncidentsToCase(@NotNull CCase componentToAdd, @NotNull List<CCaseIncident> eCaseIncidentsToAdd ) throws CException {

        for (CCaseIncident eCaseIncident : eCaseIncidentsToAdd)
        {
            componentToAdd.addCCaseIncident(this.createNewECaseIncidentFromOldECaseIncident(eCaseIncident, componentToAdd));
        }

    }

    @NotNull
    private CCaseIncident createNewECaseIncidentFromOldECaseIncident(@NotNull CCaseIncident oldECaseIncident,@NotNull CCase parentEComponent) throws CException {

        if(oldECaseIncident instanceof CActivity)
        {
           CCaseIncident eCaseIncidentNew = new CActivityImpl(null,parentEComponent);
            this.addNewEAttributesToComponent(eCaseIncidentNew, oldECaseIncident.getCAttributes().values());
            return eCaseIncidentNew;

        }
        else if(oldECaseIncident instanceof CEvent)
        {

            CCaseIncident eCaseIncidentNew = new CEventImpl(null,parentEComponent);
            this.addNewEAttributesToComponent(eCaseIncidentNew,oldECaseIncident.getCAttributes().values());
            return eCaseIncidentNew;
        }
        else
        {
            throw new CException("Could not create new caseIncident from old caseIncident " + oldECaseIncident.toString());
        }
    }



    @NotNull
    private void addNewEAttributesToComponent(@NotNull CComponent eComponentToAddAttributes,@NotNull Collection<CAttribute> cAttributesToAdd) throws CException {
        for(CAttribute cAttribute : cAttributesToAdd)
        {
            eComponentToAddAttributes.addEAttribute(this.createNewEAttributeFromOldEAttribute(cAttribute,eComponentToAddAttributes));
        }

    }

    @NotNull
    private CAttribute createNewEAttributeFromOldEAttribute(@NotNull CAttribute cAttribute, @NotNull CComponent parentComponent) throws CException {
        if(cAttribute instanceof CLoadedAttribute)
        {
            return new CLoadedAttributeImpl(cAttribute.getKey(), ((CLoadedAttribute)cAttribute).getOriginalValue(), parentComponent);
        }
        else if(cAttribute instanceof CPredefinedAttribute)
        {
            return new CPredefinedAttributeImpl(cAttribute.getKey(), ((CPredefinedAttribute)cAttribute).getOriginalValue(),parentComponent);
        }
        else if(cAttribute instanceof CComputedAttribute)
        {
            return new CComputedAttributeImpl(cAttribute.getKey(), cAttribute.getValue(),parentComponent);

        }
        else
        {
            throw new CException("Could not create new attribute from old attribute " + cAttribute.toString());

        }
    }



    public CCaseBaseForJaxb addECase(@NotNull CCase eCase)
    {
           this.eCases.put(eCase.getCAttribute("caseId").getValue().toString(),eCase);

        return this;
    }

    public CCase getECase(String caseId) {
        return this.eCases.getValue(caseId);
    }










    //for jaxb
/*
    @XmlElementRefs({@XmlElementRef(type=ECaseBuilder.class)} )
    private void setCasesForJaxbAsList(List<ECaseBuilder> list)
    {
        for(ECaseBuilder eCaseBuilder : list)
        {
            this.eCaseBuilders.put(eCaseBuilder.getEAttributeBuilder("caseId").getValue(), eCaseBuilder);
        }


    }*/

    @NotNull
    @Override
    public CCaseBaseDefinition getCDefinition() {
        return this.eCaseBaseDefinition;
    }

    @NotNull
    @Override
    public Cbml getParentCComponent() {
        return this.parentEComponent;
    }


    //For JAXB

    private CCaseBaseForJaxb() {
        super();
    }


    //Set definition before unmarshal
    private void beforeUnmarshal(final Unmarshaller unmarshaller,
                                 final Object parent)
    {

        this.parentEComponent = ((Cbml) parent);
        this.eCaseBaseDefinition = this.parentEComponent.getCDefinition();


    }




    private List<CCase> getCasesForJaxbAsList()
    {
        return this.eCases.values();
    }

    @XmlElementRefs({@XmlElementRef(type=CCaseImpl.class)} )
    private void setCasesForJaxbAsList(List<CCase> list)
    {
        for(CCase eCase : list)
        {
            this.eCases.put(eCase.getCAttribute("caseId").getValue().toString(), eCase);
        }
    }

}
