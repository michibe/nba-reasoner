package org.hhz.nbareasoner.cbml.impl.advanced;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;

import java.util.List;

/**
 * Created by mbehr on 12.05.2015.
 *
 * This should describe which Attributes are necessary in the whole cbml. For example cost for a cost-Computation
 */
public class CObligatedAttributeImpl implements CObligatedAttribute {
    private final String key;
    //Welche Typen für das obligatedAttribut in ordnung sind
    private final List <String> type;
    //
    private final CMainComponentType forCMainComponentType;

    public CObligatedAttributeImpl(String key, List<String> type, CMainComponentType forCMainComponentType) {
        this.key = key;
        this.type = type;
        this.forCMainComponentType = forCMainComponentType;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public CMainComponentType getForCMainComponentType() {
        return forCMainComponentType;
    }

    public List<String> getType() {
        return type;
    }
}
