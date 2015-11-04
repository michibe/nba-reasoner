package org.hhz.nbareasoner.cbml.model.base.data.extern;


import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by m on 03.04.2015.
 */

public interface CCCase extends CCComponent {



    @Nullable
    public CCCaseIncident getCCaseIncident(@NotNull String key);



    // public void addEIncident(ECaseIncident eCaseIncident);

    @NotNull
    public CCCaseBase getParentCComponent();


    @NotNull
    public List<? extends CCCaseIncident> getCCaseIncidentsAsList();



    @NotNull
    public Status getStatus();

    public enum Status {
        OPENED,CLOSED;
    }



    public CCCaseIncident getLastCIncident();
    public CCCaseIncident getFirstCIncident();
    public CCActivity getLastCActivity();
    public CCActivity getFirstCActivity();
    public CCEvent getLastCEvent();
    public CCEvent getFirstCEvent();


}
