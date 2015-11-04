package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.CComponent;
import org.hhz.nbareasoner.cbml.model.base.data.CComputedAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mbehr on 30.04.2015.
 */
@XmlRootElement(name="computedAttribute")
public class CComputedAttributeImpl extends CAttributeImplAbs implements CComputedAttribute {

    public CComputedAttributeImpl(@NotNull String key, @NotNull Object value, @NotNull CComponent parentComponent) {
        super(key,  value, parentComponent);
    }

    @NotNull
    @Override
    public CComputedAttributeDefinition getCDefinition() {
        return (CComputedAttributeDefinition)super.getCDefinition();
    }

    private CComputedAttributeImpl() {
        super();
    }

}
