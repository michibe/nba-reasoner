package org.hhz.nbareasoner.cbml.model.base.data;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.jetbrains.annotations.NotNull;



/**
 * Created by mbehr on 29.04.2015.
 *
 * There are three different types of cbml-attributes. One of them is the loaded attribute. The attribute-value will be loaded based on a loader from an outside datasource.
 */
public interface CLoadedAttribute extends CAttribute {


    /**
     * @return the corresponding definition for this component
     */
    @NotNull
    public CLoadedAttributeDefinition getCDefinition();

    /**
     * While loading the values they are converted to a given type. This method enables to receive the original loaded value not converted to any type.
     * @return the original loaded value as a String
     */
    @NotNull
    public String getOriginalValue();

}
