package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CLoadedAttributeDefinition extends CAttributeDefinition {

    @NotNull
    public CLoadingSourceDefinition getLoadingSourceOpenedCase();

    @NotNull
    public CLoadingSourceDefinition getLoadingSourceClosedCase();


    @NotNull
    public <E> E convertValueAsDeclaredType(String value) throws CException;

}
