package org.hhz.nbareasoner.cbml.model.base.data;



import org.hhz.nbareasoner.cbml.model.base.data.extern.CCActivity;
import org.hhz.nbareasoner.cbml.model.base.definition.CActivityDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by m on 03.04.2015.
 *
 * Interface of the CBML-Activity. Activities are the executed incidents in a case.
 * Beside the activities there are the events ({@link CEvent}), that occur from the outside and are not explicit executed.
 */

public interface CActivity extends CCaseIncident, CCActivity {

    /**
     * Gets the parent Component ({@link CCase}) of the Activity due to the CBML-XML structure.
     * @return the parent case of the activity
     */
    @NotNull
    public CCase getParentCComponent();

    /**
     * There is one single activity-definition in the CBML-structure, that is the corresponding definition for all activities.
     * The Definition is a kind of meta information for the activities.
     * @return the corresponding activity definition
     */
    @NotNull
    public CActivityDefinition getCDefinition();

    /**
     * In the CBML-XML-structure all activities beside the last activity in a case have a subsequent activity. So by using this method one have access to the subsequent activity.
     *
     * @return the subsequent activity if exists, otherwise Null.
     */
    @Nullable
    public CActivity getNextCActivity();


    /**
     * In the CBML-XML-structure all activities beside the first activity in a case have a preceding activity. So by using this method one have access to the preceding activity.
     *
     * @return the preceding activity if exists, otherwise Null.
     */
    @Nullable
    public CActivity getPreviousCActivity();

}
