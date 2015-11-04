package org.hhz.nbareasoner.cbml.model.base.data;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseDefinition;
import org.hhz.nbareasoner.service.jsonserializer.CCaseJacksonSerializer;
import org.hhz.nbareasoner.util.MapList;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by m on 03.04.2015.
 *
 * The CCase class represents a cbml-case. It is child of the caseBase and holds activities and events.
 *
 */
@JsonSerialize(using = CCaseJacksonSerializer.class)
public interface CCase extends CComponent, CCCase {

    /**
     *
     *
     * @return the corresponding case definition
     */
    @NotNull
    public CCaseDefinition getCDefinition();

    /**
     *
     * @param key the key of the incident specified in the cbml-xml
     * @return the case incident (activity or event) specified by its key. If no incident for the given key exists it returns null.
     */
    @Nullable
    public CCaseIncident getCCaseIncident(@NotNull String key);

    /**
     *
     * @return the casebase of this case. There is only one casebase in cbml, that includes all cases.
     */
    @NotNull
    public CCaseBase getParentCComponent();

    // public void addEIncident(ECaseIncident eCaseIncident);




    /**
     *
     * @return all case incidents (activities and events) for this case as MapList
     */
    @NotNull
    public MapList<String,CCaseIncident> getCCaseIncidents();

    /**
     * Adds a caseIncident to this case.
     * @param cCaseIncident incident to add to this case
     * @return returns this case
     */
    public CCase addCCaseIncident(@NotNull CCaseIncident cCaseIncident);

    /**
     * @return all case incidents (activities and events) for this case as List
     */
    @NotNull
    public List<CCaseIncident> getCCaseIncidentsAsList();

    /**
     * sorts all incidents in this case by its timestamp
     */
    public void sort();



    /**
     *  Removes all stored incidents for this specific case
     */
    public void removeAllIncidents();

    /**
     *
     * @return the chronological last incident (activity or event) for this case assumed that sort() has been called for this case.
     */
    public CCaseIncident getLastCIncident();

    /**
     *
     * @return the chronological first incident (activity or event) for this case assumed that sort() has been called for this case.
     */
    public CCaseIncident getFirstCIncident();

    /**
     * @return the chronological last activity for this case assumed that sort() has been called for this case.
     */
    public CActivity getLastCActivity();
    /**
     * @return the chronological first activity for this case assumed that sort() has been called for this case.
     */
    public CActivity getFirstCActivity();
    /**
     * @return the chronological last event for this case assumed that sort() has been called for this case.
     */
    public CEvent getLastCEvent();
    /**
     * @return the chronological first event for this case assumed that sort() has been called for this case.
     */
    public CEvent getFirstCEvent();




}
