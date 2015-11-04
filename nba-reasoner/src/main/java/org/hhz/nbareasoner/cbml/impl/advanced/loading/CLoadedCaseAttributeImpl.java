package org.hhz.nbareasoner.cbml.impl.advanced.loading;

import org.hhz.nbareasoner.cbml.model.advanced.loading.CLoadedCaseAttribute;

/**
 * Created by mbehr on 06.05.2015.
 */
public class CLoadedCaseAttributeImpl implements CLoadedCaseAttribute {

    private final String belongingCaseId;
    private final String attributeValue;

    public CLoadedCaseAttributeImpl(String belongingCaseId, String attributeValue) {
        this.belongingCaseId = belongingCaseId;
        this.attributeValue = attributeValue;
    }

    public String getBelongingCaseId() {
        return belongingCaseId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
