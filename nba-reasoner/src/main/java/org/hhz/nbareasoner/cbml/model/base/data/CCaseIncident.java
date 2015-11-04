package org.hhz.nbareasoner.cbml.model.base.data;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseIncidentDefinition;
import org.hhz.nbareasoner.service.jsonserializer.CCaseIncidentJacksonSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 30.04.2015.
 *
 * Every case consists out of one or more case incidents. A case incident can be an activity or an event.
 */
@JsonSerialize(using = CCaseIncidentJacksonSerializer.class)
public interface CCaseIncident extends CComponent, CCCaseIncident {


    /**
     *  Every Incident beside the first one has a subsequent incident. This method gives you access to the subsequent incident.
     *  The order of the incidents is based on their timestamp under the assumption that the case has been already sorted.
     * @return the subsequent incident if exist. Otherwise null.
     */
    @Nullable
    public CCaseIncident getNextEIncident();


    /**
     *  Every Incident beside the first one has a preceding incident. This method gives you access to the subsequent incident.
     *  The order of the incidents is based on their timestamp under the assumption that the case has been already sorted.
     * @return the previous incident if exist. Otherwise null
     */
    @Nullable
    public CCaseIncident getPreviousEIncident();

    /**
     * @return the parent case of this incident.
     */
    @NotNull
    public CCase getParentCComponent();

    /**
     * @return the corresponding definition of the incident (activity or event)
     */
    @NotNull
    public CCaseIncidentDefinition getCDefinition();

}
