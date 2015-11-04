package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.CComponent;
import org.hhz.nbareasoner.cbml.model.base.data.CLoadedAttribute;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mbehr on 30.04.2015.
 */

//TODO es sollte neben loaded computed und predefined noch ein dritten attributtyp geben: precomputed, der sich auf berechnungen innerhalb des prgrammes bezieht wie zum beispiel für lifecyclestatus, welcher automasich hinzugefügt werden kann
//Todo De weiteren wäre ein liveattribut sinnvoll, welches in bestimmten zeitlichen abständen aus einer quelle immer wieder erneuert wird... So könnten zum beispiel wetterdaten oder ähnliches miteinbezogen werden
@XmlRootElement(name="loadedAttribute")
public class CLoadedAttributeImpl extends CAttributeImplAbs implements CLoadedAttribute {


    private final String originalValue;

    public CLoadedAttributeImpl(@NotNull String key, @NotNull String originalValue, @NotNull CComponent parentComponent) throws CException {


        super(key, ((CLoadedAttributeDefinition)parentComponent.getCDefinition().getCAttributeDefinition(key)).convertValueAsDeclaredType(originalValue),  parentComponent);

        this.originalValue = originalValue;
    }

    @NotNull
    @Override
    public CLoadedAttributeDefinition getCDefinition() {
        return (CLoadedAttributeDefinition)super.getCDefinition();
    }


    @Override
    public String getOriginalValue() {
        return originalValue;
    }

    //For JAXB
private CLoadedAttributeImpl() {

    super();
    this.originalValue = null;
}


}
