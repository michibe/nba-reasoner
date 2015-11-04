package org.hhz.nbareasoner.cbml.impl.base.definition;

import org.hhz.nbareasoner.cbml.model.base.definition.CActivityDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;

import java.util.List;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CActivityDefinitionImpl extends CComponentDefinitionImplAbs implements CActivityDefinition {

    public CActivityDefinitionImpl(List<CLoadedAttributeDefinition> loadedAttributeDefinitionsTmp, List<CPredefinedAttributeDefinition> predefinedAttributeDefinitionsTmp, List<CComputedAttributeDefinition> computedAttributeDefinitionsTmp) {
        super(loadedAttributeDefinitionsTmp, predefinedAttributeDefinitionsTmp, computedAttributeDefinitionsTmp);
    }

    public CActivityDefinitionImpl() {
        super(null,null,null);
    }

}
