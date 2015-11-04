package org.hhz.nbareasoner.cbml.model.base.data;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 29.04.2015.
 *
 * There are three different types of cbml-attributes. One of them is the computed attribute. The attribute-value will be computed based on a programmed computation.
 */

public interface CComputedAttribute extends CAttribute {

    /**
     * @return the corresponding definition of the attribute.
     */
    @NotNull
    public CComputedAttributeDefinition getCDefinition();
}
