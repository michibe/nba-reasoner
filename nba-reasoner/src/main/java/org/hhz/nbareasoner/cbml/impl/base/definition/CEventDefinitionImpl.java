package org.hhz.nbareasoner.cbml.impl.base.definition;

import org.hhz.nbareasoner.cbml.model.base.definition.CEventDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;

import java.util.List;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CEventDefinitionImpl extends CComponentDefinitionImplAbs implements CEventDefinition {


    public CEventDefinitionImpl(List<CLoadedAttributeDefinition> loadedAttributeDefinitionsTmp, List<CPredefinedAttributeDefinition> predefinedAttributeDefinitionsTmp, List<CComputedAttributeDefinition> computedAttributeDefinitionsTmp) {
        super(loadedAttributeDefinitionsTmp, predefinedAttributeDefinitionsTmp, computedAttributeDefinitionsTmp);
    }

    public CEventDefinitionImpl() {
        super(null,null,null);
    }
}
