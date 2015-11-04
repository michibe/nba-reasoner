package org.hhz.nbareasoner.cbml.impl.advanced;

import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mbehr on 30.07.2015.
 *
 * The CObligatedDefinitionAttribute describes which Attribute is for a definition obligatory
 */
public class CObligatedParameterImpl implements CObligatedParameter {

    //Name of the attribute
    @NotNull
    private final String name;

    //allowed values for the attribute... if null all values are allowed
    @NotNull
    private final List<String> allowedValues;

    public CObligatedParameterImpl(@NotNull String name, String... allowedValues) {
        this.name = name;
        this.allowedValues = new ArrayList(Arrays.asList(allowedValues));

    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public List<String> getAllowedValues() {
        return allowedValues;
    }

    @NotNull
    @Override
    public String getAllowedValuesAsString() {

        String result ="[";
        for(String s: allowedValues)
        {
            result = result +s + " ";
        }
        result = result + "]";

        return result;
    }

    @Override
    public String toString() {
        return "CObligatedDefinitionAttributeImpl{" +
                "name='" + name + '\'' +
                ", allowedValues=" + getAllowedValuesAsString() +
                '}';
    }
}
