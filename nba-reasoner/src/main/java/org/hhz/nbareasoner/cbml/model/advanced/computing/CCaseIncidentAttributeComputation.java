package org.hhz.nbareasoner.cbml.model.advanced.computing;


import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseIncident;


/**
 * Created by mbehr on 11.05.2015.
 */
public interface CCaseIncidentAttributeComputation extends CAttributeComputation {
    //should return the attribute value
    public String compute(CCCaseIncident eCaseIncident);
}
