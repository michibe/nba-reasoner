package org.hhz.nbareasoner.cbml.impl.advanced.loading;

import org.hhz.nbareasoner.cbml.model.advanced.loading.CLoadedIncidentAttribute;

/**
 * Created by mbehr on 06.05.2015.
 */
public class CLoadedIncidentAttributeImpl implements CLoadedIncidentAttribute {

    private final String belongingCaseId;
    private final String belongingIncidentId;
    private final String attributeValue;

    public CLoadedIncidentAttributeImpl(String belongingCaseId, String belongingIncidentId, String attributeValue) {
        this.belongingCaseId = belongingCaseId;
        this.belongingIncidentId = belongingIncidentId;
        this.attributeValue = attributeValue;
    }

    public String getBelongingCaseId() {
        return belongingCaseId;
    }

    public String getBelongingIncidentId() {
        return belongingIncidentId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    @Override
    public String toString() {
        return "CLoadedIncidentAttributeImpl{" +
                "belongingCaseId='" + belongingCaseId + '\'' +
                ", belongingIncidentId='" + belongingIncidentId + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                '}';
    }
}
