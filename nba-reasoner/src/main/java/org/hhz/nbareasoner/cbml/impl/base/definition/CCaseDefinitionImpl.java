package org.hhz.nbareasoner.cbml.impl.base.definition;


import org.hhz.nbareasoner.cbml.model.base.definition.CActivityDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CEventDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CCaseDefinitionImpl extends CComponentDefinitionImplAbs implements CCaseDefinition {


    @XmlElement(type=CActivityDefinitionImpl.class,required = true)
    private final CActivityDefinition activityDefinition;
    @XmlElement(type=CEventDefinitionImpl.class, required = true)
    private final CEventDefinition eventDefinition;

    private CCaseDefinitionImpl() {
        super(null,null,null);
        this.activityDefinition = null;
        this.eventDefinition = null;
    }
    public CCaseDefinitionImpl(List<CLoadedAttributeDefinition> loadedAttributeDefinitionsTmp, List<CPredefinedAttributeDefinition> predefinedAttributeDefinitionsTmp, List<CComputedAttributeDefinition> computedAttributeDefinitionsTmp, CActivityDefinition activityDefinition, CEventDefinition eventDefinition) {
        super(loadedAttributeDefinitionsTmp, predefinedAttributeDefinitionsTmp, computedAttributeDefinitionsTmp);
        this.activityDefinition = activityDefinition;
        this.eventDefinition = eventDefinition;
    }

    @Override
    public CActivityDefinition getEActivityDefinition() {
        return activityDefinition;
    }

    @Override
    public CEventDefinition getEEventDefinition() {
        return eventDefinition;
    }


}
