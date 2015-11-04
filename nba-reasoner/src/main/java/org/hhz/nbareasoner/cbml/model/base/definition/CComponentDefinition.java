package org.hhz.nbareasoner.cbml.model.base.definition;


import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CComponentDefinition {

    @Nullable
    public CAttributeDefinition getCAttributeDefinition(@NotNull String attributeKey);

    @Nullable
    public CLoadedAttributeDefinition getCLoadedAttributeDefinition(@NotNull String key);

    @Nullable
    public CComputedAttributeDefinition getCComputedAttributeDefinition(@NotNull String key);

    @Nullable
    public CPredefinedAttributeDefinition getCPredefinedAttributeDefinition(@NotNull String key);

    @NotNull
    public Map<String, CLoadedAttributeDefinition> getCLoadedAttributeDefinitions();

    @NotNull
    public Map<String, CPredefinedAttributeDefinition> getCPredefinedAttributeDefinitions();

    @NotNull
    public Map<String, CComputedAttributeDefinition> getCComputedAttributeDefinitions();






}
