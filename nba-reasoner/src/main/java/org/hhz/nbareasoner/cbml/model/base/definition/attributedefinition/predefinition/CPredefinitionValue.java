package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition;

import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CPredefinitionValue {

    @NotNull
    public String getPerAttributeValue();

    @NotNull
    public String getPredefineValue();
}
