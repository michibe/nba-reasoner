package org.hhz.nbareasoner.cbml.model.base.definition;


import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 04.05.2015.
 */


public interface CCaseDefinition extends CComponentDefinition {

    @Nullable
    public CActivityDefinition getEActivityDefinition();

    @Nullable
    public CEventDefinition getEEventDefinition();


}
