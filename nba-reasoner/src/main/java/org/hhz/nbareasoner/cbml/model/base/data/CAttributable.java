package org.hhz.nbareasoner.cbml.model.base.data;

import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttributable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Created by mbehr on 29.04.2015.
 *
 * The CBML components caseBase, case, activity and event can be equiped with attributes (Loaded-Attibutes, Predefined-Attributes and Computed-Attributes)
 * Therefore this interface indicates that a component is attributable. So the java classes of these components will implement this interface.
 *
 */
public interface CAttributable extends CCAttributable {

    /**
     * Every attributable component can be added a new attribute.
     *
     * @param cAttribute
     *                  the new Attribute that will be added to the attributable component.
     */
    public void addEAttribute(@NotNull CAttribute cAttribute);


    /**
     *
     * @param attributeKey The key of the attribute. In the cbml xml structure the key is defined as a xml-attribute (key="keyname")
     * @return the attribute specified by the attribute-key. If no such attribute exists Null will be returned.
     */
    @Nullable
    public CAttribute getCAttribute(@NotNull String attributeKey);


    /**
     *
     * @return all attributes as map
     */
    @NotNull
    public Map<String,CAttribute> getCAttributes();
    //public EAttributeDefinition getAttributeDefinition();

    /**
     * Removes all attributes.
     */
    public void clearAttributes();

    /**
     *
     * @return all attributes as list.
     */
    public List<CAttribute> getCAttributesAsList();
}
