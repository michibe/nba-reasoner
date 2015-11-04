package org.hhz.nbareasoner.cbr.impl;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbr.impl.phase.retain.SimpleRetainAgent;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityAgent;
import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaAgent;

import org.hhz.nbareasoner.cbr.model.phase.retain.CbrRetainAgent;
import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveAgent;
import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveResult;
import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseAgent;
import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseResult;
import org.hhz.nbareasoner.config.impl.base.NbaReasonerConfigImpl;
import org.hhz.nbareasoner.config.model.base.NbaReasonerConfig;
import org.hhz.nbareasoner.cbml.impl.base.CbmlImpl;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by mbehr on 12.03.2015.
 *

 */
public class CbrManager {

    private static Logger logger = Logger.getLogger(CbrManager.class.getName());
    private final Cbml cbml;
    private final NbaReasonerConfig nbaReasonerConfig;

    private final CbrRetrieveAgent cbrRetrieveAgent;
    private final CbrReuseAgent cbrReuseAgent;
    private final CbrRetainAgent cbrRetainAgent;


    //Duchr diese variablen können die funktionen ausgeschaltet werden zum beispiel beim update der datenbasis
    private boolean lockRetrieve = false;
    private boolean lockReuse = false;
    private boolean lockCaseBase = false;


    //der reuse agent ist dafür verantwortlich was in der reusephase des cbr zyklus passiert


    public CbrManager(Cbml cbml, NbaReasonerConfig nbaReasonerConfig, CbrRetrieveAgent cbrRetrieveAgent, CbrReuseAgent cbrReuseAgent, CbrRetainAgent cbrRetainAgent) {
        this.cbml = cbml;
        this.nbaReasonerConfig = nbaReasonerConfig;
        this.cbrRetrieveAgent = cbrRetrieveAgent;
        this.cbrReuseAgent = cbrReuseAgent;
        this.cbrRetainAgent = cbrRetainAgent;
    }

    public static CbrManager createCbrManager(URI cbmlFilePathUri,URI nbaReasonerConfigFilePathUri ) {

        final Cbml cbml;
       final NbaReasonerConfig nbaReasonerConfig;
        CbrRetrieveAgent cbrRetrieveAgent;
        CbrReuseAgent cbrReuseAgent;
        CbrRetainAgent cbrRetainAgent;

        try {

            //Pfad laden



            cbml = CbmlImpl.loadCbml(new File(cbmlFilePathUri));




        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load Cbml  due to "  + e.getMessage(),e);
        }

        try {


            nbaReasonerConfig = NbaReasonerConfigImpl.loadNbaReasonerConfig(new File(nbaReasonerConfigFilePathUri), cbml.getCDefinition());


        } catch (Exception e) {
            throw new RuntimeException("Could not load NbaReasonerConfig due to " + e.getMessage(),e);
        }


/*

    problem die retireveagents bekommen ihre casebase mit... wenn sich jetzt im hintergrund die casebase ienfach mal ändert
    hat zb der similarity agent eine andere casebase, als die die eigentlich existiert

*/




        cbrRetrieveAgent = new SimilarityAgent(cbml.getCDefinition(),nbaReasonerConfig.getCbrConfig().getCaseSimilarityMeasureConfigs());

        cbrReuseAgent = new NbaAgent(cbml.getCDefinition(), nbaReasonerConfig.getNbaConfig().getNbaRatingConfigs());

        cbrRetainAgent = new SimpleRetainAgent();


        return new CbrManager(cbml,nbaReasonerConfig,cbrRetrieveAgent,cbrReuseAgent,cbrRetainAgent);
    }

    public NbaReasonerConfig getNbaReasonerConfig() {
        return nbaReasonerConfig;
    }



    @NotNull
    public CCase getCCase(@NotNull String caseId) throws CbrRuntimeException {



        if(this.lockCaseBase==false)
        {


            CCase cCase = null;
            try {
                cCase = this.cbml.getCCaseBase().getECase(caseId);
            } catch (CException e) {
              e.printStackTrace();
            }

            if(cCase==null)
            {
                throw new CbrRuntimeException("Could not find case with id " + caseId, HttpStatus.NOT_FOUND);
            }

               return cCase;

        }
        else
        {
            throw new CbrRuntimeException("Cannot get case "+caseId+" because getCase() is currently locked while retain phase is in progress.",HttpStatus.LOCKED);
        }

    }

    public CCaseBase getCCaseBase() throws CbrRuntimeException {

        if(this.lockCaseBase==false)
        {
            return this.cbml.getCCaseBase();
        }
        else
        {
            throw new CbrRuntimeException("Cannot get caseBase  because getCaseBase() is currently locked while retain phase is in progress.",HttpStatus.LOCKED);

        }
    }

    public List<CCase> getCaseBaseCases() throws CbrRuntimeException {
        if(this.lockCaseBase==false)
        {
            return this.cbml.getCCaseBase().getCCases().values();
        }
        else
        {
            throw new CbrRuntimeException("Cannot get caseBase  because getCaseBase() is currently locked while retain phase is in progress.",HttpStatus.LOCKED);

        }

    }


    public void serializeCaseBaseToOutputStream(@NotNull OutputStream outputStream) throws JAXBException {
        this.cbml.getCCaseBase().serializeToOutputStream(outputStream);
    }

    public void serializeCbmlToOutputStream(@NotNull OutputStream outputStream) throws JAXBException {
        this.cbml.serializeToOutputStream(outputStream);
    }

    public void serializeEDefinitionToOutputStream(@NotNull OutputStream outputStream) throws JAXBException {
        this.cbml.getCDefinition().serializeToOutputStream(outputStream);
    }



    //Query String in the form smw-name-lineage = 10 & smw-name-lineage = 1
    @Nullable
    public  CbrRetrieveResult retrieve(@NotNull CCase queryCase, @NotNull Map<String,Double> customSimilarityMeasuresWeights) throws CbrRetrieveAgent.CbrRetrieveException, CbrRuntimeException {

        if(this.lockRetrieve==false)
        {

            logger.fine("Start retrieving similar cases of case " + queryCase.getCAttribute("caseId") +" with custom weights: " + customSimilarityMeasuresWeights.toString());


            CbrRetrieveResult cbrRetrieveResult =  this.cbrRetrieveAgent.retrieve(this.cbml.getCCaseBase(),queryCase, customSimilarityMeasuresWeights);

            return cbrRetrieveResult;



        }
        else
        {
            throw new CbrRetrieveAgent.CbrRetrieveException("Reuse is currently locked because retain phase is in progress.");
        }


    }






    // configHandlder ?
    // Was muss mit diesen konfigurationen geschehen ?


  //  die similarity measures


  //  NBACBRManager

  // getRetrieveResult(ausführungsid)
    //eig gleich wie cycle
   //    run()


   //  retrieve()

      //   reuse()


           //  retain zeitgesteuert

//Hält die algorithmen
    //DataManager



    //Das Ergebnis des retrieves sollte eine gerankte Liste von ähnlichen fällen sein
    //Ähnliche Fälle retrieven



    @Nullable
    public CbrReuseResult reuse(@Nullable CbrRetrieveResult cbrRetrieveResult, @NotNull Map<String,Double> customRatingMeasuresWeights) throws CbrReuseAgent.CbrReuseException, CbrRetrieveAgent.CbrRetrieveException, CbrRuntimeException {


        if(this.lockReuse==false)
        {




            CbrReuseResult cbrReuseResult =  this.cbrReuseAgent.reuse(cbrRetrieveResult,customRatingMeasuresWeights);
            return cbrReuseResult;


        }
        else
        {
            throw new CbrReuseAgent.CbrReuseException("Reuse is currently locked because retain phase is in progress.");
        }

    }


   //Bisher: erneuert casebasis und gibt neue casebasis zurück
    public List<CCase> retain() throws CbrRetainAgent.CbrRetainException {
        //Alle möglichen Ausführngen müssen während der retain phase gestoppt werden, falls das erforderlich ist
        if(this.cbrRetainAgent.lockCaseBaseNecessary())
        {
            this.lockCaseBase = true;
        }

        if(this.cbrRetainAgent.lockRetrieveNecessary())
        {
            this.lockRetrieve = true;
        }

        if(this.cbrRetainAgent.lockReuseNecessary())
        {
            this.lockReuse = true;
        }

        //Alle agent Caches leeren



        //Alle Fälle müssen neu geladen werden

        this.cbrRetainAgent.retain(this.cbml.getCCaseBase());


        this.lockCaseBase = false;
        this.lockRetrieve = false;
        this.lockReuse = false;



        return this.cbml.getCCaseBase().getCCases().values();
    }



    public CCaseBaseDefinition getCbmlDefinition() {
        return cbml.getCDefinition();
    }
}
