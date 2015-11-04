package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.computation;

import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.CAttributeDefinitionImplAbs;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CKeyAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by mbehr on 27.04.2015.
 */
@XmlRootElement(name="computedAttributeDefinition")
public class CComputedAttributeDefinitionImpl extends CAttributeDefinitionImplAbs implements CComputedAttributeDefinition {

    @XmlElement(type=CComputationDefinitionImpl.class, name = "computation")
    private final CComputationDefinition eComputationDefinition;

    public CComputedAttributeDefinitionImpl(@NotNull String key, @NotNull String type,  @NotNull String value, @Nullable List<CKeyAlias> eKeyAliasDefinitionsTmp, @Nullable List<CValueAlias> eValueAliasDefinitionsTmp, @NotNull CComputationDefinition computation) {
        super(key, type, value, eKeyAliasDefinitionsTmp, eValueAliasDefinitionsTmp);
        this.eComputationDefinition = computation;
    }

    private CComputedAttributeDefinitionImpl() {
        super();
        this.eComputationDefinition = null;
    }


    @Override
    public CComputationDefinition getComputation() {
        return this.eComputationDefinition;
    }
}
