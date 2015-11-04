package org.hhz.nbareasoner.cbml.model.base.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.service.jsonserializer.CAttributeJacksonSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 29.04.2015.
 *
 *
 */

@JsonSerialize(using = CAttributeJacksonSerializer.class)
public interface CAttribute extends CCAttribute {
    /**
     * Get the parent component ({@link CComponent}) of the Attribute based on the CBML-XML structure.
     * @return the parent component of this attribute (e.g. {@link org.hhz.nbareasoner.cbml.model.base.data.CCaseBase}, {@link org.hhz.nbareasoner.cbml.model.base.data.CCase},{@link org.hhz.nbareasoner.cbml.model.base.data.CCaseIncident})
     */
    @NotNull
    public CComponent getParentCComponent();

    /**
     * Every Attribute is based on a attribute-definition in the cbml structure, that declares meta-informations for the attribute.
     *
     * @return the corresponding attribute definition
     */
    @NotNull
    public CAttributeDefinition getCDefinition();





}
