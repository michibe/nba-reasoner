package org.hhz.nbareasoner.cbml.model.advanced.loading;

import org.hhz.nbareasoner.cbml.model.advanced.CLoadingSourceException;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;

import java.util.List;

/**
 * Created by mbehr on 04.05.2015.
 *
 * An attribute in the cbml.xml can be computed, predefined and loaded. This class is the interface of a Loader, that loads the data from a specific datasource.
 * In cbml for everey new loaded attribute a loader has to be specified.
 *
 */
public interface CAttributeLoader {





    /**
     * This method tests if the Loader is applicable for the given data
     * @return true if the loader is applicable
     * @throws CNotApplicableException
     */
    public boolean isApplicable() throws CNotApplicableException;


    /**
     * This method will be called after the loading of this instance of the loader has been executed.
     * @throws CLoadingSourceException
     */
    public void afterLoading() throws CLoadingSourceException;


    /**
     * This method will be called before the loading of this instance of the loader has been executed.
     * @throws CLoadingSourceException
     */
    public void beforeLoading() throws CLoadingSourceException;;

    /**
     * This method will be called once for all instances of this class before the first execution.
     * For example while loading all closed cases the method will be called when this loader is used the first time.
     * So this method can be used for setting up static variables like a connection pool etc.
      */
    public void beforeFirstInstanceRunsLoading() throws CLoadingSourceException;

    /**
     * This method will be called once for all instances of this class after the last execution with this loader has finished
     * For example while loading all closed cases the method will be called after the loader has been used for the last time.
     * So this method could be used for clossing pool conections etc.
     */
    public void afterLastInstanceRunLoading() throws CLoadingSourceException;




    public CLoadedCaseBaseAttribute loadCaseBaseAttributeValue() throws CLoadingSourceException;
    //Ids geben immer einzelne lsiten zurück, aus denen können dann die fälle gebaut werden
    //Alle vorhandenen CaseIds zurückgeben
    public List<CLoadedCaseAttribute> loadCaseAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException;
    //Alle anderen abfrgan sollen eine Map zurückgeben in denen der Key der caseId entspricht. Dadurch
    //lönnen die geladenen Attribute den fällen zugeteilt werden
    public List<CLoadedIncidentAttribute> loadActivityAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException;
    public List<CLoadedIncidentAttribute> loadEventAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException;

    //Ebenfalls für opened Case
    public CLoadedCaseAttribute loadCaseAttributeValue(String openedCaseId) throws CLoadingSourceException;
    public List<CLoadedIncidentAttribute> loadActivityAttributeValues(String openedCaseId) throws CLoadingSourceException;
    public List<CLoadedIncidentAttribute> loadEventAttributeValues(String openedCaseId) throws CNotApplicableException;


    //Gib eine Map mit falli aller AktivitätenIds zurück


    //Gib mir alle Fälle

    //Gib mir die Attribute zu allen Fällen

    //Gib mir die Aktivitäten zu bzw eigentlich zu jedem Fall




    //Gib mir die Attribute zu einer Aktivität


    //Beschreibung wie der Loader funktioniert
    public CLoadingSourceDefinition getELoadingSource();
    //Beschreibung wie der Loader funktioniert
    public String getDescription();
    //welche LoadingSourceAttribute sind notwendig
    public List<CObligatedParameter> getObligatedParameters();

    //constructor wird wie folgt aufgerufen Constructor(ELoadingSource eloadingSource)

}
