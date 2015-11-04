package org.hhz.nbareasoner.cbml.model.base.data.extern;



import org.jetbrains.annotations.Nullable;

/**
 * Created by m on 03.04.2015.
 */

public interface CCActivity extends CCCaseIncident {




    @Nullable
    public CCActivity getNextCActivity();

    @Nullable
    public CCActivity getPreviousCActivity();

}
