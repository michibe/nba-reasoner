package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.impl.advanced.loading.CAttributeLoaderImplAbs;
import org.hhz.nbareasoner.cbml.model.advanced.CLoadingSourceException;
import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.*;
import org.hhz.nbareasoner.cbml.model.base.definition.CActivityDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CEventDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinitionValue;
import org.hhz.nbareasoner.cbml.model.advanced.computing.*;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CAttributeComputation;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CCaseBaseAttributeComputation;
import org.hhz.nbareasoner.cbml.model.advanced.loading.CAttributeLoader;
import org.hhz.nbareasoner.cbml.model.advanced.loading.CLoadedCaseAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.loading.CLoadedIncidentAttribute;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by mbehr on 23.05.2015.
 *
 * This class contains static helper methods to build the casebase vial loadings, predefinitions and computations.
 * The methods are mainly used from the CCaseBaseImpl-class
 */
public class CCaseBaseHelper {


    private final static Logger logger = LoggerFactory.getLogger(CCaseBaseHelper.class);





    /**
     * Runs the loadings defined for the given caseBase and adds the casesbase-, case- and incident-attributes to the caseBase.
     * @param eCaseBase
     * @param loadOnlyDataGap
     * @throws CException
     */
    public static void runCaseBaseLoadings(@NotNull CCaseBase eCaseBase, boolean loadOnlyDataGap) throws CException {
        logger.debug("Start to build Case Base from Loadings");
    //Todo implement load only data gap
        //Alle Attribute leeren alle Cases leeren
        //tODO wenn nur gab geladen werden soll, dann muss nicht mehr alles gelöscht werden
        if (loadOnlyDataGap == false) {
            eCaseBase.getCCases().clear();
            eCaseBase.clearAttributes();
        }




        //
       AttributeLoaderStore attributeLoaderStore = new AttributeLoaderStore();



        //CaseBaseAttributeLaden
        for (CLoadedAttributeDefinition cLoadedAttributeDefinition : eCaseBase.getCDefinition().getCLoadedAttributeDefinitions().values()) {
            try {


                CAttributeLoader eDataLoader = loadEAttributeLoader(cLoadedAttributeDefinition.getLoadingSourceClosedCase());
                attributeLoaderStore.addLoader(eDataLoader);
                if (eDataLoader.isApplicable()) {
                    eDataLoader.beforeLoading();
                    CLoadedAttribute eLoadedAttribute = new CLoadedAttributeImpl(cLoadedAttributeDefinition.getKey(), eDataLoader.loadCaseBaseAttributeValue().getAttributeValue(), eCaseBase);

                    eCaseBase.addEAttribute(eLoadedAttribute);
                    logger.debug("CaseBaseAttribute '" + eLoadedAttribute + " ' added to CaseBase");
                    eDataLoader.afterLoading();

                }


            } catch (Exception e) {
                throw new CException("Could not load CaseBaseAttribute '" + cLoadedAttributeDefinition.getKey() + "' due to", e);
            }
        }

        runClosedCaseLoadings(eCaseBase, loadOnlyDataGap,attributeLoaderStore);
    }


    private static void runClosedCaseLoadings(@NotNull CCaseBase eCaseBase, boolean loadOnlyDataGap, AttributeLoaderStore attributeLoaderStore) throws CException {
        //CaseIdAttribute



        CLoadedAttributeDefinition cLoadedAttributeDefinitionCaseId = eCaseBase.getCDefinition().getCCaseDefinition().getCLoadedAttributeDefinition("caseId");
        
        try {

            CAttributeLoader eDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionCaseId.getLoadingSourceClosedCase());
            attributeLoaderStore.addLoader(eDataLoader);
            if (eDataLoader.isApplicable()) {

                eDataLoader.beforeLoading();
                List<CLoadedCaseAttribute> caseAttributes = eDataLoader.loadCaseAttributeValues(eCaseBase.getECasesAsList());
                eDataLoader.afterLoading();
                //Beim ersten Durchgang muss caseId genommen werden


                //wenn es sich um case Id handelt diese cases erstellen und hinzufügen
                for (CLoadedCaseAttribute cLoadedCaseAttribute : caseAttributes) {
                    CCase eCaseNew = new CCaseImpl(null, CCase.Status.CLOSED, null, eCaseBase);

                    CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionCaseId.getKey(), cLoadedCaseAttribute.getAttributeValue(), eCaseNew);

                    eCaseNew.addEAttribute(eLoadedAttributeNew);
                    logger.debug("Add CaseIdAttribute '" + eLoadedAttributeNew + " ' to Case");
                    logger.debug("Case added to CaseBase");


                    eCaseBase.addECase(eCaseNew);
                }
            }
        } catch (Exception e) {
            throw new CException("Could not load CaseIdAttribute '" + cLoadedAttributeDefinitionCaseId.getKey() + "' due to", e);

        }

        //Alle anderen CaseAttribute laden
        for (CLoadedAttributeDefinition cLoadedAttributeDefinitionCase : eCaseBase.getCDefinition().getCCaseDefinition().getCLoadedAttributeDefinitions().values()) {

            if (!cLoadedAttributeDefinitionCase.getKey().equals("caseId")) {
                try {
                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionCase.getLoadingSourceClosedCase());
                   attributeLoaderStore.addLoader(cDataLoader);
                    if (cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        List<CLoadedCaseAttribute> caseAttributes = cDataLoader.loadCaseAttributeValues(eCaseBase.getCCases().values());
                        cDataLoader.afterLoading();

                        for (CLoadedCaseAttribute cLoadedCaseAttribute : caseAttributes) {

                            CCase forECase = eCaseBase.getEClosedCase(cLoadedCaseAttribute.getBelongingCaseId());

                            if (forECase != null)
                            {
                                CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionCase.getKey(), cLoadedCaseAttribute.getAttributeValue(), forECase);

                                forECase.addEAttribute(eLoadedAttributeNew);
                                logger.debug("Add CaseAttribute '" + eLoadedAttributeNew + " ' to Case '" + forECase.getCAttribute("caseId").getValue() + "'");

                            }
                            else{
                                logger.info("Could not load CaseAttribute '" + cLoadedAttributeDefinitionCase.getKey() + "' because case with id '" + cLoadedCaseAttribute.getBelongingCaseId() + "' is not available.");
                            }



                        }

                    }
                } catch (Exception e) {
                    throw new CException("Could not load CaseAttribute '" + cLoadedAttributeDefinitionCase.getKey() + "' due to", e);

                }

            }


        }
        runIncidentLoadingsForClosedCases(eCaseBase, loadOnlyDataGap,attributeLoaderStore);

        attributeLoaderStore.invokeAfterLastInstanceRunLoadingOnLoader();
    }


    public static void runOpenedCaseLoadings(@NotNull CCase eOpenedCase) throws CException {
        //CaseIdAttribut wird zuvor schon festgelegt
        //Alle anderen CaseAttribute laden

        AttributeLoaderStore attributeLoaderStore = new AttributeLoaderStore();

        for (CLoadedAttributeDefinition cLoadedAttributeDefinitionCase : eOpenedCase.getCDefinition().getCLoadedAttributeDefinitions().values()) {

            if (!cLoadedAttributeDefinitionCase.getKey().equals("caseId")) {
                try {
                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionCase.getLoadingSourceOpenedCase());

                    attributeLoaderStore.addLoader(cDataLoader);

                    if (cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        CLoadedCaseAttribute cLoadedCaseAttribute = cDataLoader.loadCaseAttributeValue(eOpenedCase.getCAttribute("caseId").getValue().toString());
                        cDataLoader.afterLoading();


                            if (cLoadedCaseAttribute.getBelongingCaseId() == null || !cLoadedCaseAttribute.getBelongingCaseId().equals(eOpenedCase.getCAttribute("caseId").getValue())) {
                                throw new CException("Could not load CaseAttribute '" + cLoadedAttributeDefinitionCase.getKey() + "' for opened case because case with id '" + eOpenedCase.getCAttribute("caseId").getValue() + "' is not available.");
                            }

                            CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionCase.getKey(), cLoadedCaseAttribute.getAttributeValue(), eOpenedCase);
                            logger.debug("Add CaseAttribute '" + eLoadedAttributeNew + " ' to Case");
                            eOpenedCase.addEAttribute(eLoadedAttributeNew);



                    }
                } catch (Exception e) {
                    throw new CException("Could not load CaseAttribute '" + cLoadedAttributeDefinitionCase.getKey() + "' due to", e);

                }

            }


        }
        runIncidentLoadingsForOpenedCase(eOpenedCase,attributeLoaderStore);

        attributeLoaderStore.invokeAfterLastInstanceRunLoadingOnLoader();


    }

    private static void runIncidentLoadingsForClosedCases(@NotNull CCaseBase eCaseBase, boolean loadOnlyDataGap,AttributeLoaderStore attributeLoaderStore) throws CException {
        //ActivityAttribute laden
        //Gibt es überhaupt eine definition für activity
        CActivityDefinition eActivityDefinition = eCaseBase.getCDefinition().getCCaseDefinition().getEActivityDefinition();
        if(  eActivityDefinition!=null)
        {

            //ActivityIdAttribut laden
            CLoadedAttributeDefinition cLoadedAttributeDefinitionActivityId;
            //Gibt es überhaupt eine IncidentId, wenn nicht Fehler
            if((cLoadedAttributeDefinitionActivityId = eActivityDefinition.getCLoadedAttributeDefinition("incidentId"))!=null)
            {
                try {

                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionActivityId.getLoadingSourceClosedCase());
                    attributeLoaderStore.addLoader(cDataLoader);
                    if (cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        List<CLoadedIncidentAttribute> activityAttributes = cDataLoader.loadActivityAttributeValues(eCaseBase.getCCases().values());
                        cDataLoader.afterLoading();


                        for (CLoadedIncidentAttribute cLoadedIncidentAttribute : activityAttributes) {





                            CCase forCCase = eCaseBase.getEClosedCase(cLoadedIncidentAttribute.getBelongingCaseId().toString());
                            if(forCCase!=null)
                            {
                                CActivity cActivityNew = new CActivityImpl(null,forCCase);

                                CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionActivityId.getKey(), cLoadedIncidentAttribute.getAttributeValue(), cActivityNew);

                                cActivityNew.addEAttribute(eLoadedAttributeNew);
                                logger.debug("Add ActivityIdAttribute '" + eLoadedAttributeNew + " ' to Activity");

                                //Incidents in map mit caseId speichern
                                forCCase.addCCaseIncident(cActivityNew);





                            }
                            else{
                               logger.info("Could not load ActivityIdAttribute '" + cLoadedAttributeDefinitionActivityId.getKey() + "' because case with id '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' is not available. Maybe you should fix your sql query.");

                            }





                        }

                    }
                }catch (Exception e) {
                    throw new CException("Could not load ActivityIdAttribute '" + cLoadedAttributeDefinitionActivityId.getKey() + "' due to", e);

                }
            }
            else
            {
                throw new CException("Could not load ActivityIdAttribute because no incidentId-attribute is defined");

            }

            //ActivityAttribute laden
            for(CLoadedAttributeDefinition cLoadedAttributeDefinitionActivity : eActivityDefinition.getCLoadedAttributeDefinitions().values()) {


                if (!cLoadedAttributeDefinitionActivity.getKey().equals("incidentId")) {
                    //wenn es sich um incidentId handelt diesen Incident erstellen und hinzufügen
                    try {
                        CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionActivity.getLoadingSourceClosedCase());
                       attributeLoaderStore.addLoader(cDataLoader);
                        if(cDataLoader.isApplicable()) {
                            cDataLoader.beforeLoading();
                            List<CLoadedIncidentAttribute> activityAttributes = cDataLoader.loadActivityAttributeValues(eCaseBase.getCCases().values());
                            cDataLoader.afterLoading();


                            for (CLoadedIncidentAttribute cLoadedIncidentAttribute : activityAttributes) {

                                CCase forECase = eCaseBase.getEClosedCase(cLoadedIncidentAttribute.getBelongingCaseId());

                                if(forECase != null)
                                {

                                    CCaseIncident forActivity =  forECase.getCCaseIncident(cLoadedIncidentAttribute.getBelongingIncidentId());


                                    if (forActivity != null) {
                                        CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionActivity.getKey(), cLoadedIncidentAttribute.getAttributeValue(),forActivity);
                                        forActivity.addEAttribute(eLoadedAttributeNew);
                                        logger.debug("Add ActivityAttribute '" + eLoadedAttributeNew + " ' to Activity");

                                    }
                                    else
                                    {
                                            logger.info("Could not load ActivityAttribute '" + cLoadedAttributeDefinitionActivity.getKey() + "' because activity for case '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' with incidentid '" + cLoadedIncidentAttribute.getBelongingIncidentId() + "' is not available.");

                                    }


                                }
                                else
                                {
                                    logger.info("Could not load ActivityAttribute '" + cLoadedAttributeDefinitionActivity.getKey() + "' because case with id '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' is not available.");

                                }



                            }
                        }
                    } catch (Exception e) {
                        throw new CException("Could not load ActivityAttribute '" + cLoadedAttributeDefinitionActivity.getKey() + "' due to", e);


                    }


                }
            }

        }



        //Event Attribute laden
        CEventDefinition eEventDefinition = eCaseBase.getCDefinition().getCCaseDefinition().getEEventDefinition();
        if(  eEventDefinition!=null)
        {


            //EventIdAttribut laden
            CLoadedAttributeDefinition cLoadedAttributeDefinitionEventId;
            //Gibt es überhaupt eine IncidentId, wenn nicht Fehler
            if((cLoadedAttributeDefinitionEventId = eEventDefinition.getCLoadedAttributeDefinition("incidentId"))!=null)
            {
                try {

                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionEventId.getLoadingSourceClosedCase());
                    attributeLoaderStore.addLoader(cDataLoader);
                    if(cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        List<CLoadedIncidentAttribute> eventAttributes = cDataLoader.loadEventAttributeValues(eCaseBase.getCCases().values());
                        cDataLoader.afterLoading();

                        for (CLoadedIncidentAttribute cLoadedIncidentAttribute : eventAttributes) {
                            CCase forECase = eCaseBase.getEClosedCase(cLoadedIncidentAttribute.getBelongingCaseId());


                            if(forECase != null)
                            {
                                CEvent eEventNew = new CEventImpl(null,forECase);
                                CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionEventId.getKey(), cLoadedIncidentAttribute.getAttributeValue(),eEventNew);
                                eEventNew.addEAttribute(eLoadedAttributeNew);
                                logger.debug("Add EventIdAttribute '" + eLoadedAttributeNew + " ' to Event");
                                forECase.addCCaseIncident(eEventNew);
                            }
                            else
                            {
                                logger.info("Could not load eventIdAttribute '" + cLoadedAttributeDefinitionEventId.getKey() + "' because case with id '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' is not available. Maybe you should fix your sql-query.");

                            }



                        }
                    }
                } catch (Exception e) {
                    throw new CException("Could not load EventAttribute '" + cLoadedAttributeDefinitionEventId.getKey() + "' due to", e);

                }
            }
            else
            {
                throw new CException("Could not load EventIdAttribute because no incidentId-attribute is defined");
            }

            //EventdAttribute laden
            for(CLoadedAttributeDefinition cLoadedAttributeDefinitionEvent : eEventDefinition.getCLoadedAttributeDefinitions().values()) {


                if (!cLoadedAttributeDefinitionEvent.getKey().equals("incidentId")) {
                    //wenn es sich um incidentId handelt diesen Incident erstellen und hinzufügen

                    try {
                        CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionEvent.getLoadingSourceClosedCase());
                       attributeLoaderStore.addLoader(cDataLoader);
                        if(cDataLoader.isApplicable()) {
                            cDataLoader.beforeLoading();
                            List<CLoadedIncidentAttribute> eventAttributes = cDataLoader.loadEventAttributeValues(eCaseBase.getCCases().values());
                            cDataLoader.afterLoading();


                            for (CLoadedIncidentAttribute cLoadedIncidentAttribute : eventAttributes) {
                                CCase forECase = eCaseBase.getEClosedCase(cLoadedIncidentAttribute.getBelongingCaseId());


                                if(forECase != null)
                                {
                                    CCaseIncident forEEvent =  forECase.getCCaseIncident(cLoadedIncidentAttribute.getBelongingIncidentId());

                                    if (forEEvent != null) {
                                        CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionEvent.getKey(), cLoadedIncidentAttribute.getAttributeValue(),forEEvent);
                                        forEEvent.addEAttribute(eLoadedAttributeNew);

                                        logger.debug("Add LoadedAttribute '" + eLoadedAttributeNew + " ' to Event");
                                    }
                                    else
                                    {
                                        logger.info("Could not load EventAttribute '" + cLoadedAttributeDefinitionEvent.getKey() + "' because activity for case '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' with incidentid '" + cLoadedIncidentAttribute.getBelongingIncidentId() + "' is not available.");
                                    }

                                }
                                else
                                {
                                    logger.info("Could not load eventAttribute '" + cLoadedAttributeDefinitionEventId.getKey() + "' because case with id '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' is not available.");

                                }





                            }
                        }
                    } catch (Exception e) {
                        throw new CException("Could not load EventAttribute '" + cLoadedAttributeDefinitionEvent.getKey() + "' due to", e);


                    }


                }
            }


        }





    }

    private static void runIncidentLoadingsForOpenedCase(@NotNull CCase eOpenedCase, AttributeLoaderStore attributeLoaderStore) throws CException {
        //ActivityAttribute laden
        //Gibt es überhaupt eine definition für activity
        CActivityDefinition eActivityDefinition = eOpenedCase.getCDefinition().getEActivityDefinition();
        if(eActivityDefinition!=null)
        {

            //ActivityIdAttribut laden
            CLoadedAttributeDefinition cLoadedAttributeDefinitionActivityId;
            //Gibt es überhaupt eine IncidentId, wenn nicht Fehler
long startTime = System.nanoTime();

            if((cLoadedAttributeDefinitionActivityId = eActivityDefinition.getCLoadedAttributeDefinition("incidentId"))!=null)
            {
                try {
                    logger.info("before loading eattibuteloader");
                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionActivityId.getLoadingSourceOpenedCase());
                   attributeLoaderStore.addLoader(cDataLoader);
                    logger.info("After loading eattibuteloader");
                    if (cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        List<CLoadedIncidentAttribute> activityAttributes = cDataLoader.loadActivityAttributeValues(eOpenedCase.getCAttribute("caseId").getValue().toString());
                        cDataLoader.afterLoading();


                        for (CLoadedIncidentAttribute cLoadedIncidentAttribute : activityAttributes) {
                            logger.info("load activity attribute");
                               if(cLoadedIncidentAttribute.getBelongingCaseId().equals(eOpenedCase.getCAttribute("caseId").getValue()))
                               {
                                   CActivity cActivityNew = new CActivityImpl(null,eOpenedCase);

                                   CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionActivityId.getKey(), cLoadedIncidentAttribute.getAttributeValue(), cActivityNew);
                                   logger.debug("Add ActivityIdAttribute '" + eLoadedAttributeNew + " ' to Activity");

                                   cActivityNew.addEAttribute(eLoadedAttributeNew);

                                   //Incidents in map mit caseId speichern
                                   eOpenedCase.addCCaseIncident(cActivityNew);
                               }

                        }

                    }
                }catch (Exception e) {
                    throw new CException("Could not load ActivityIdAttribute '" + cLoadedAttributeDefinitionActivityId.getKey() + "' due to", e);

                }
            }
            else
            {
                throw new CException("Could not load ActivityIdAttribute because no incidentId-attribute is defined");

            }
            logger.info("111 " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
            startTime = System.nanoTime();
            //ActivityAttribute laden
            for(CLoadedAttributeDefinition cLoadedAttributeDefinitionActivity : eActivityDefinition.getCLoadedAttributeDefinitions().values()) {


                if (!cLoadedAttributeDefinitionActivity.getKey().equals("incidentId")) {
                    //wenn es sich um incidentId handelt diesen Incident erstellen und hinzufügen
                    try {logger.info("before loading eattibuteloader nichtid");
                        CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionActivity.getLoadingSourceOpenedCase());
                       attributeLoaderStore.addLoader(cDataLoader);
                        logger.info("after loading eattibuteloader nichtid");
                        if(cDataLoader.isApplicable()) {
                            cDataLoader.beforeLoading();
                            List<CLoadedIncidentAttribute> activityAttributes = cDataLoader.loadActivityAttributeValues(eOpenedCase.getCAttribute("caseId").getValue().toString());
                            cDataLoader.afterLoading();


                            for (CLoadedIncidentAttribute cLoadedIncidentAttribute : activityAttributes) {

                                if(cLoadedIncidentAttribute.getBelongingCaseId().equals(eOpenedCase.getCAttribute("caseId").getValue()))
                                {

                                    CCaseIncident forActivity =  eOpenedCase.getCCaseIncident(cLoadedIncidentAttribute.getBelongingIncidentId());

                                    if (forActivity == null) {
                                        throw new CException("Could not load ActivityAttribute '" + cLoadedAttributeDefinitionActivity.getKey() + "' because activity for case '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' with incidentid '" + cLoadedIncidentAttribute.getBelongingIncidentId() + "' is not available.");
                                    }

                                    CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionActivity.getKey(), cLoadedIncidentAttribute.getAttributeValue(),forActivity);
                                    forActivity.addEAttribute(eLoadedAttributeNew);
                                    logger.debug("Add ActivityAttribute '" + eLoadedAttributeNew + " ' to Activity");


                                }


                            }
                        }
                    } catch (Exception e) {
                        throw new CException("Could not load ActivityAttribute '" + cLoadedAttributeDefinitionActivity.getKey() + "' due to", e);


                    }


                }
            }

            logger.info("222 " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

        }



        //Event Attribute laden
        CEventDefinition eEventDefinition = eOpenedCase.getCDefinition().getEEventDefinition();
        if( eEventDefinition!=null)
        {


            //EventIdAttribut laden
            CLoadedAttributeDefinition cLoadedAttributeDefinitionEventId;
            //Gibt es überhaupt eine IncidentId, wenn nicht Fehler
            if((cLoadedAttributeDefinitionEventId = eEventDefinition.getCLoadedAttributeDefinition("incidentId"))!=null)
            {
                try {

                    CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionEventId.getLoadingSourceOpenedCase());
                   attributeLoaderStore.addLoader(cDataLoader);
                    if(cDataLoader.isApplicable()) {
                        cDataLoader.beforeLoading();
                        List<CLoadedIncidentAttribute> eventAttributes = cDataLoader.loadEventAttributeValues(eOpenedCase.getCAttribute("caseId").getValue().toString());
                        cDataLoader.afterLoading();

                        for (CLoadedIncidentAttribute cLoadedIncidentAttribute : eventAttributes) {

                            if(cLoadedIncidentAttribute.getBelongingCaseId().equals(eOpenedCase.getCAttribute("caseId").getValue()))
                            {

                                CEvent eEventNew = new CEventImpl(null,eOpenedCase);
                                CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionEventId.getKey(), cLoadedIncidentAttribute.getAttributeValue(),eEventNew);
                                eEventNew.addEAttribute(eLoadedAttributeNew);
                                logger.debug("Add EventIdAttribute '" + eLoadedAttributeNew + " ' to Event");
                                eOpenedCase.addCCaseIncident(eEventNew);
                            }


                        }
                    }
                } catch (Exception e) {
                    throw new CException("Could not load EventAttribute '" + cLoadedAttributeDefinitionEventId.getKey() + "' due to", e);

                }
            }
            else
            {
                throw new CException("Could not load EventIdAttribute because no incidentId-attribute is defined");
            }

            //EventdAttribute laden
            for(CLoadedAttributeDefinition cLoadedAttributeDefinitionEvent : eEventDefinition.getCLoadedAttributeDefinitions().values()) {


                if (!cLoadedAttributeDefinitionEvent.getKey().equals("incidentId")) {
                    //wenn es sich um incidentId handelt diesen Incident erstellen und hinzufügen

                    try {
                        CAttributeLoader cDataLoader = loadEAttributeLoader(cLoadedAttributeDefinitionEvent.getLoadingSourceOpenedCase());
                       attributeLoaderStore.addLoader(cDataLoader);
                        if(cDataLoader.isApplicable()) {
                            cDataLoader.beforeLoading();
                            List<CLoadedIncidentAttribute> eventAttributes = cDataLoader.loadEventAttributeValues(eOpenedCase.getCAttribute("caseId").getValue().toString());
                            cDataLoader.afterLoading();


                            for (CLoadedIncidentAttribute cLoadedIncidentAttribute : eventAttributes) {

                                if(cLoadedIncidentAttribute.getBelongingCaseId().equals(eOpenedCase.getCAttribute("caseId").getValue()))
                                {
                                    CCaseIncident forEEvent =  eOpenedCase.getCCaseIncident(cLoadedIncidentAttribute.getBelongingIncidentId());

                                    if (forEEvent == null) {
                                        throw new CException("Could not load EventAttribute '" + cLoadedAttributeDefinitionEvent.getKey() + "' because activity for case '" + cLoadedIncidentAttribute.getBelongingCaseId() + "' with incidentid '" + cLoadedIncidentAttribute.getBelongingIncidentId() + "' is not available.");
                                    }


                                    CLoadedAttribute eLoadedAttributeNew = new CLoadedAttributeImpl(cLoadedAttributeDefinitionEvent.getKey(), cLoadedIncidentAttribute.getAttributeValue(),forEEvent);
                                    forEEvent.addEAttribute(eLoadedAttributeNew);

                                    logger.debug("Add LoadedAttribute '" + eLoadedAttributeNew + " ' to Event");
                                }

                            }
                        }
                    } catch (Exception e) {
                        throw new CException("Could not load EventAttribute '" + cLoadedAttributeDefinitionEvent.getKey() + "' due to", e);


                    }


                }
            }


        }



    }




    //Predefinitions --------------------------------------

    //Achtung predefinitions rufen sich untereinandeer auf ... eventuell anderer name finden
    public static void runPredefinitionsFor(@NotNull CCaseBase eCaseBase) throws CException {

        //caseBase Predefinition
        for(CPredefinedAttributeDefinition cPredefinedAttributeDefinitionCaseBase : eCaseBase.getCDefinition().getCPredefinedAttributeDefinitions().values()) {

            //Attribut erstellen
            //Wert des Attributes durch predefinition festlegen
            //Atributwert der zuweisung holn
            String attributePerKey = eCaseBase.getCAttribute(cPredefinedAttributeDefinitionCaseBase.getPredefinition().getPerAttributeKey()).getValue().toString();
            CPredefinitionValue predefinitionValue = cPredefinedAttributeDefinitionCaseBase.getPredefinition().getPredefinitionValues().get(attributePerKey);

            String value ="";
            if(predefinitionValue==null)
            {
                value = cPredefinedAttributeDefinitionCaseBase.getValue();
            }
            else
            {
                value = predefinitionValue.getPredefineValue();
            }
            CPredefinedAttribute ePredefinedAttributeNew = null;
            try {
                ePredefinedAttributeNew = new CPredefinedAttributeImpl(cPredefinedAttributeDefinitionCaseBase.getKey(),value,eCaseBase);
            } catch (CException e) {
                throw new CException("Could not create predefinedAttribute " + attributePerKey + " because value " + value +" could not be paresed to defined datatype due to " + e.getMessage());
            }
            eCaseBase.addEAttribute(ePredefinedAttributeNew);
            logger.debug("Add PredefinedAttribute '" + ePredefinedAttributeNew + " ' to CaseBase");

            // / eEvent.addEAttributeBuilder(eLoadedAttribute);


        }

        for(CCase eCase : eCaseBase.getCCases().values())
        {
            runPredefinitionsFor(eCase);

        }

    }



    public static void runPredefinitionsFor(CCase eCase) throws CException {

        for(CPredefinedAttributeDefinition cPredefinedAttributeDefinitionCase : eCase.getCDefinition().getCPredefinedAttributeDefinitions().values()) {


            String attributePerKey = eCase.getCAttribute(cPredefinedAttributeDefinitionCase.getPredefinition().getPerAttributeKey()).getValue().toString();
            CPredefinitionValue predefinitionValue = cPredefinedAttributeDefinitionCase.getPredefinition().getPredefinitionValues().get(attributePerKey);

            String value ="";
            if(predefinitionValue==null)
            {
                value = cPredefinedAttributeDefinitionCase.getValue();
            }
            else
            {
                value = predefinitionValue.getPredefineValue();
            }

            CPredefinedAttribute ePredefinedAttributeNew = null;
            try {
                ePredefinedAttributeNew = new CPredefinedAttributeImpl(cPredefinedAttributeDefinitionCase.getKey(),value,eCase);
            } catch (CException e) {
                throw new CException("Could not create predefinedAttribute " + attributePerKey + " because value " + value +" could not be paresed to defined datatype due to " + e.getMessage());

            }
            eCase.addEAttribute(ePredefinedAttributeNew);
            logger.debug("Add PredefinedAttribute '" + ePredefinedAttributeNew + " ' to case");

        }


        //Run CaseIncident Predefinitions
        for(CCaseIncident eCaseIncident :eCase.getCCaseIncidents().values())
        {

            if(eCaseIncident instanceof CActivity)
            {
                runPredefinitionsFor(eCaseIncident);
            }
            else if (eCaseIncident instanceof CEvent)
            {
                runPredefinitionsFor(eCaseIncident);
            }

        }


    }

    private static void runPredefinitionsFor(CCaseIncident eCaseIncident) throws CException {

        for(CPredefinedAttributeDefinition cPredefinedAttributeDefinitionIncident : eCaseIncident.getCDefinition().getCPredefinedAttributeDefinitions().values()) {


            String attributePerKey = eCaseIncident.getCAttribute(cPredefinedAttributeDefinitionIncident.getPredefinition().getPerAttributeKey()).getValue().toString();

            CPredefinitionValue predefinitionValue = cPredefinedAttributeDefinitionIncident.getPredefinition().getPredefinitionValues().get(attributePerKey);

            String value ="";
            if(predefinitionValue==null)
            {
                value = cPredefinedAttributeDefinitionIncident.getValue();
            }
            else
            {
                value = predefinitionValue.getPredefineValue();
            }

            CPredefinedAttribute ePredefinedAttributeNew = null;
            try {
                ePredefinedAttributeNew = new CPredefinedAttributeImpl(cPredefinedAttributeDefinitionIncident.getKey(),value,eCaseIncident);
            } catch (CException e) {
                throw new CException("Could not create predefinedAttribute " + attributePerKey + " because value " + value +" could not be paresed to defined datatype due to " + e.getMessage());

            }
            eCaseIncident.addEAttribute(ePredefinedAttributeNew);
            logger.debug("Add PredefinedAttribute '" + ePredefinedAttributeNew + " ' to Incident");


        }
    }







  //-----------------------Computations-------------------------







    public static Map<CComputedAttributeDefinition,CCaseAttributeComputation> loadECaseAttributeComputations(CCaseBaseDefinition eCaseBaseDefinition) throws CException {
        Map<CComputedAttributeDefinition,CCaseAttributeComputation> eCaseAttributeComputationInstances = new HashMap<>();
        //case computations laden und in liste zur wiederverwendung für jeden case speichern
        for(CComputedAttributeDefinition eComputedAttributeDefinition: eCaseBaseDefinition.getCCaseDefinition().getCComputedAttributeDefinitions().values()) {

            try {
                CCaseAttributeComputation eAttributeComputation = (CCaseAttributeComputation) loadEAttributeComputation(eComputedAttributeDefinition.getComputation());
                if(eAttributeComputation.isApplicable(eCaseBaseDefinition))
                    eCaseAttributeComputationInstances.put(eComputedAttributeDefinition, eAttributeComputation);

            } catch (Exception e) {
                throw new CException("Could not load CaseAttributeComputation '" + eComputedAttributeDefinition.getKey() + "' due to " + e.getMessage(), e);


            }
        }

        return eCaseAttributeComputationInstances;
    }

    public static Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> loadEActivityAttributeComputations(CCaseBaseDefinition eCaseBaseDefinition) throws CException {
        Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseActivityAttributeComputationInstances = new HashMap<>();
        //case computations laden und in liste zur wiederverwendung für jeden case speichern
        if(eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition() != null)
        {
            for(CComputedAttributeDefinition eComputedAttributeDefinition: eCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCComputedAttributeDefinitions().values()) {

                try {
                    CCaseIncidentAttributeComputation eAttributeComputation = (CCaseIncidentAttributeComputation) loadEAttributeComputation(eComputedAttributeDefinition.getComputation());
                    if(eAttributeComputation.isApplicable(eCaseBaseDefinition))
                        eCaseActivityAttributeComputationInstances.put(eComputedAttributeDefinition, eAttributeComputation);

                } catch (Exception e) {
                    throw new CException("Could not load ActivityAttributeComputation '" + eComputedAttributeDefinition.getKey() + "' due to", e);


                }
            }
        }

        return eCaseActivityAttributeComputationInstances;
    }
    public static Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> loadEEventAttributeComputations(CCaseBaseDefinition eCaseBaseDefinition) throws CException {
        Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseEventAttributeComputationInstances = new HashMap<>();
        //case computations laden und in liste zur wiederverwendung für jeden case speichern
        if(eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition() != null) {
            for (CComputedAttributeDefinition eComputedAttributeDefinition : eCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCComputedAttributeDefinitions().values()) {

                try {
                    CCaseIncidentAttributeComputation eAttributeComputation = (CCaseIncidentAttributeComputation) loadEAttributeComputation(eComputedAttributeDefinition.getComputation());
                    if (eAttributeComputation.isApplicable(eCaseBaseDefinition))
                        eCaseEventAttributeComputationInstances.put(eComputedAttributeDefinition, eAttributeComputation);

                } catch (Exception e) {
                    throw new CException("Could not load EventAttributeComputation '" + eComputedAttributeDefinition.getKey() + "' due to", e);


                }
            }
        }

        return eCaseEventAttributeComputationInstances;
    }



    //computation methods calles other computation methods
    public static void runComputationsFor(CCaseBase eCaseBase) throws CException {

        //Von hinten nach vorne also erst computations von incidents dann von cases und dann von casebase, da eher der fall dass case computation auf bereits ausgeführte incident computations aufbauen

        CCaseBaseDefinition eCaseBaseDefinition = eCaseBase.getCDefinition();
        //BerechnungsInstanzen zur Wiederverwendung für jeden Fall zwischenspeichern
        //berechnungsmethoden pro definition für casecomputations zwisschenspeichern und dann mitgeben, da sie sonst ständig mit reflection geholt werden müssen
        //case computations laden und in liste zur wiederverwendung für jeden case speichern
        Map<CComputedAttributeDefinition,CCaseAttributeComputation> eCaseAttributeComputationInstances =loadECaseAttributeComputations(eCaseBaseDefinition);


        //Berechnungsinstanzen für jeden Incident pro definition (Acitivity und Event) zwischenspeichern

        //Für Activities
        Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseActivityAttributeComputationInstances = loadEActivityAttributeComputations(eCaseBaseDefinition);
        //case computations laden und in liste zur wiederverwendung für jeden case speichern

        //Für Events
        Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseEventAttributeComputationInstances = loadEEventAttributeComputations(eCaseBaseDefinition);
        //case computations laden und in liste zur wiederverwendung für jeden case speichern



        //Eigentlihc sollte eine fallberechnung nicht von anderen fällen abhängig sein, genauso wie eine incident berechnung nicht von anderen incident abhängig sein soll
        // deshlab wird auch nur sichergestellt dass alle inneren Berechnungen, also incident vor fall und alle fälle vor casebase durchgeführt wurden
        for(CCase eCase : eCaseBase.getCCases().values())
        {
            runComputationsFor(eCase, eCaseAttributeComputationInstances,eCaseActivityAttributeComputationInstances,eCaseEventAttributeComputationInstances);

        }



        //Casebase Computations
        for(CComputedAttributeDefinition eComputedAttributeDefinition: eCaseBaseDefinition.getCComputedAttributeDefinitions().values()) {

            try {
                CCaseBaseAttributeComputation eAttributeComputation = (CCaseBaseAttributeComputation)loadEAttributeComputation(eComputedAttributeDefinition.getComputation());

                if(eAttributeComputation.isApplicable(eCaseBaseDefinition))
                {
                    Object value =  eAttributeComputation.compute(eCaseBase);




                    CComputedAttribute eComputedAttributeNew = new CComputedAttributeImpl(eComputedAttributeDefinition.getKey(), value,eCaseBase);


                    eCaseBase.addEAttribute(eComputedAttributeNew);

                    logger.debug("Add ComputedAttribute '" + eComputedAttributeNew + " ' to CaseBase");
                }


            } catch (Exception e) {
                throw new CException("Could not compute CaseBaseAttribute '" + eComputedAttributeDefinition.getKey() + "' due to", e);


            }


        }


    }


    public static void runComputationsFor(CCase eCase, Map<CComputedAttributeDefinition,CCaseAttributeComputation> caseAttributeComputationInstances,Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseActivityAttributeComputationInstances,Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> eCaseEventAttributeComputationInstances ) throws CException {


        //incidents berechnen
        //bevor case computation durchgeführt wird, alle incidendcomputations für diesen case durchführen
        //da es die incidents vorher berechnet sein sollten, da es sein kann dass eine case berechnung die incident berechnung benötigt
        for(CCaseIncident eCaseIncident : eCase.getCCaseIncidents().values())
        {
            if(eCaseIncident instanceof CActivity) {
                runComputationsFor(eCaseIncident, eCaseActivityAttributeComputationInstances);
            }
            else if(eCaseIncident instanceof CEvent) {
                runComputationsFor(eCaseIncident, eCaseEventAttributeComputationInstances);
            }
        }


        for(Map.Entry<CComputedAttributeDefinition,CCaseAttributeComputation> entry :  caseAttributeComputationInstances.entrySet()) {

            try {

                Object value =  entry.getValue().compute(eCase);

                CComputedAttribute eComputedAttributeNew = new CComputedAttributeImpl(entry.getKey().getKey(), value,eCase);


                eCase.addEAttribute(eComputedAttributeNew);

                logger.debug("Add computedAttribute '" + eComputedAttributeNew + " ' to case");

            } catch (Exception e) {
                throw new CException("Could not compute caseAttribute '" + entry.getKey().getKey() + "' due to", e);


            }


        }


    }

    private static void runComputationsFor(CCaseIncident eCaseIncident, Map<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> caseIncidentAttributeComputationInstances) throws CException {

        for(Map.Entry<CComputedAttributeDefinition,CCaseIncidentAttributeComputation> entry :  caseIncidentAttributeComputationInstances.entrySet()) {

            try {

                String value =  entry.getValue().compute(eCaseIncident);

                CComputedAttribute eComputedAttributeNew = new CComputedAttributeImpl(entry.getKey().getKey(), value,eCaseIncident);


                eCaseIncident.addEAttribute(eComputedAttributeNew);

                logger.debug("Add computedAttribute '" + eComputedAttributeNew + " ' to caseIncident");

            } catch (Exception e) {
                throw new CException("Could not compute caseIncidentAttribute '" + entry.getKey().getKey() + "' due to", e);


            }


        }


    }




    private static CAttributeLoader loadEAttributeLoader(CLoadingSourceDefinition cLoadingSourceDefinition) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class cls = Class.forName(cLoadingSourceDefinition.getClazz());
        Constructor<?> cons = cls.getConstructor(CLoadingSourceDefinition.class);
        return  (CAttributeLoaderImplAbs) cons.newInstance(cLoadingSourceDefinition);

    }
    private static CAttributeComputation loadEAttributeComputation(CComputationDefinition eComputationDefinition) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class cls = Class.forName(eComputationDefinition.getClazz());
        Constructor<?> cons = cls.getConstructor(CComputationDefinition.class);
        return  (CAttributeComputation) cons.newInstance(eComputationDefinition);
    }


    /**
     * Store to safe all different loading classes with one instance
     */
    static class AttributeLoaderStore
    {

        AttributeLoaderStore() {
        }

        private final HashMap<String,CAttributeLoader> attributeLoaders = new HashMap();
        /**
         * Adds a loader if it doesn't exist yet and calls the beforeFirstInstanceRunsLoading() method.
         */
        public void addLoader(CAttributeLoader cAttributeLoader) throws CLoadingSourceException {

            if(this.attributeLoaders.put(cAttributeLoader.getClass().getName(),cAttributeLoader)==null)
            {
                cAttributeLoader.beforeFirstInstanceRunsLoading();
                logger.info("Attribute Loader {} added to AttributeLoaderStore.",cAttributeLoader);
            }

        }

        public void invokeAfterLastInstanceRunLoadingOnLoader() throws CLoadingSourceException {
            for(CAttributeLoader cAttributeLoader : attributeLoaders.values())
            {

                    cAttributeLoader.afterLastInstanceRunLoading();

            }
        }
    }

}

