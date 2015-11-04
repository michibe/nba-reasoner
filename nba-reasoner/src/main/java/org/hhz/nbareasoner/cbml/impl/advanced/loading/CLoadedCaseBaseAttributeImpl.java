package org.hhz.nbareasoner.cbml.impl.advanced.loading;

import org.hhz.nbareasoner.cbml.model.advanced.loading.CLoadedCaseBaseAttribute;

/**
 * Created by mbehr on 06.05.2015.
 */
public class CLoadedCaseBaseAttributeImpl implements CLoadedCaseBaseAttribute {


    private final String attributeValue;

    public CLoadedCaseBaseAttributeImpl(String attributeValue) {

        this.attributeValue = attributeValue;
    }



    public String getAttributeValue() {
        return attributeValue;
    }
}
