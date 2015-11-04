package org.hhz.nbareasoner.cbml.model.advanced;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mbehr on 12.05.2015.
 *
 * This should describe which Attributes are necessary e.g. for a Computation
 */
public interface CObligatedAttribute {

    @NotNull
    public CMainComponentType getForCMainComponentType();
    @NotNull
    public String getKey();

    //Returns the types allowed for this obligated attribute
    @NotNull
    public List<String> getType();

    public enum CMainComponentType
    {
        CASE_BASE,CASE,CASE_INCIDENT,ACTIVITY,EVENT;
    }
}
