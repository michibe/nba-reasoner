package org.hhz.nbareasoner.cbml.model.base.data.extern;


import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 30.04.2015.
 */

public interface CCCaseIncident extends CCComponent {

    @Nullable
    public CCCaseIncident getNextEIncident();

    @Nullable
    public CCCaseIncident getPreviousEIncident();

}
