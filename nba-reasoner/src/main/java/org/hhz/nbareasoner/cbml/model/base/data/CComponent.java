package org.hhz.nbareasoner.cbml.model.base.data;

import org.hhz.nbareasoner.cbml.model.base.definition.CComponentDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by m on 03.04.2015.
 */


/**
 * Components in cbml are all XML-Elements inside the casebase-tag (casebase, case, event, activity) beside the attribute-declarations like loadedAttribute etc.
 */
public interface CComponent extends CParent, CAttributable {

    /**
     * Every component has a definition-counterpart in the cbml-definition section. This method will return the belonging definition.
     * @return the corresponding definition for this component
     */
    @NotNull
    public CComponentDefinition getCDefinition();

    /**
     *
     * @return the parent component based on the cbml-xml structure
     */
    @NotNull
    public CParent getParentCComponent();

}
