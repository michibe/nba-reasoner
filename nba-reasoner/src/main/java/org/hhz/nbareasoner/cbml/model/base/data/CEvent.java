package org.hhz.nbareasoner.cbml.model.base.data;


import org.hhz.nbareasoner.cbml.model.base.data.extern.CCEvent;
import org.hhz.nbareasoner.cbml.model.base.definition.CEventDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by m on 03.04.2015.
 *
 * Interface of the CBML-Event. Events occur while a case is in process.
 * Beside the events there are the activities ({@link org.hhz.nbareasoner.cbml.model.base.data.CActivity}), that are explicit executed.
 */
public interface CEvent extends CCaseIncident, CCEvent {

    /**
     * @return the parent component based on the cbml-xml structure
     */
    @NotNull
    public CCase getParentCComponent();

    /**
     * @return the corresponding Definition
     */
    @NotNull
    public CEventDefinition getCDefinition();


    /**
     * @return the subsequent event if exists, otherwise Null.
     */
    @Nullable
    public CEvent getNextEEvent();

    /**
     * @return the preceding event if exists, otherwise Null.
     */
    @Nullable
    public CEvent getPreviousEEvent();



}
