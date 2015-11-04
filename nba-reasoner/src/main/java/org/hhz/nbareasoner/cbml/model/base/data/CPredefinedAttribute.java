package org.hhz.nbareasoner.cbml.model.base.data;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 29.04.2015.
 *
 *  There are three different types of cbml-attributes. One of them is the predefined attribute. The attribute-value will be predefined based on an attribute value of another attribute.
 *
 */
public interface CPredefinedAttribute extends CAttribute {


    /**
     * @return the corresponding definition for this component
     */
    @NotNull
    public CPredefinedAttributeDefinition getCDefinition();

    /**
     * While inserting the predefined value it will be converted to the given type. This method enables to get the original predefined value as a String.
     * @return the original predefined value as a String
     */
    @NotNull
    public String getOriginalValue();
}
