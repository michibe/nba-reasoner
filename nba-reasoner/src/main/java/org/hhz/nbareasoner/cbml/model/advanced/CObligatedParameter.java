package org.hhz.nbareasoner.cbml.model.advanced;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * which parameters are needed for a computation or a loading function
 */
public interface CObligatedParameter {

    /**
     *
     * @return the name of the attribute
     */
    @NotNull
    public String getName();

    /**
     * This method should return a Array of Strings with the allowed value for the defined Attribute.
     * If the lsit is empty all values are allowed
     * @return a List y of allowed values
     */
    @NotNull
    public List<String> getAllowedValues();


    @NotNull
    public String getAllowedValuesAsString();
}
