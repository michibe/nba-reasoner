package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CPredefinedAttributeDefinition extends CAttributeDefinition {
    @NotNull
    public CPredefinition getPredefinition();

    @NotNull
    public <E> E convertValueAsDeclaredType(String value) throws CException;
}
