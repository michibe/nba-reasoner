package org.hhz.nbareasoner.cbml.model.base.data.extern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by mbehr on 29.04.2015.
 */
public interface CCAttributable {


    @Nullable
    public CCAttribute getCAttribute(@NotNull String attributeKey);

    @NotNull
    public List<? extends CCAttribute> getCAttributesAsList();
    //public EAttributeDefinition getAttributeDefinition();

}
