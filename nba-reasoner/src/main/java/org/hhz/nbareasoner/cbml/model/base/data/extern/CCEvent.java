package org.hhz.nbareasoner.cbml.model.base.data.extern;


import org.jetbrains.annotations.Nullable;

/**
 * Created by m on 03.04.2015.
 */
public interface CCEvent extends CCCaseIncident {



    @Nullable
    public CCEvent getNextEEvent();

    @Nullable
    public CCEvent getPreviousEEvent();



}
