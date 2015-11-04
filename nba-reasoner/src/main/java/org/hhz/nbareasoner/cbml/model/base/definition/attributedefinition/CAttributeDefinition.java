package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CKeyAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CAttributeDefinition {

    @NotNull
    public String getKey();

    @NotNull
    public String getValue();

    @NotNull
    public String getType();



    public Map<String, CKeyAlias> getcKeyAliasDefinitions();

    public Map<String, CValueAlias> getcValueAliasDefinitions();

}
