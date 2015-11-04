package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CComputedAttributeDefinition extends CAttributeDefinition {
    public CComputationDefinition getComputation();
}
