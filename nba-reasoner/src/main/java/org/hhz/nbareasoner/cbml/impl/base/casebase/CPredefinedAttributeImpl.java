package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.CComponent;
import org.hhz.nbareasoner.cbml.model.base.data.CPredefinedAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mbehr on 30.04.2015.
 */
@XmlRootElement(name="predefinedAttribute")
public class CPredefinedAttributeImpl extends CAttributeImplAbs implements CPredefinedAttribute {

    private final String originalValue;

    public CPredefinedAttributeImpl(@NotNull String key, @NotNull String originalValue, @NotNull CComponent parentComponent) throws CException {
        super(key, ((CPredefinedAttributeDefinition)parentComponent.getCDefinition().getCAttributeDefinition(key)).convertValueAsDeclaredType(originalValue),  parentComponent);

        this.originalValue = originalValue;
    }

    @NotNull
    @Override
    public CPredefinedAttributeDefinition getCDefinition() {
        return (CPredefinedAttributeDefinition)super.getCDefinition();
    }

    //For JAXB

    private CPredefinedAttributeImpl() {
        super();
        this.originalValue = null;
    }


    @Override
    public String getOriginalValue() {
        return originalValue;
    }

}
