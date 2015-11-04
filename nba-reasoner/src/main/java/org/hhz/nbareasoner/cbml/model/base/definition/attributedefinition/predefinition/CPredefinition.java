package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CPredefinition {

    @NotNull
    public String getPerAttributeKey();


    @NotNull
    public Map<String,CPredefinitionValue> getPredefinitionValues();
}
