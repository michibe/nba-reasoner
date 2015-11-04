package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.predefinition;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinitionValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * Created by mbehr on 27.04.2015.
 */
public class CPredefinitionImpl implements CPredefinition {

    @XmlAttribute
    private final String perAttributeKey;


    private final Map<String,CPredefinitionValue> ePredefinitionValues = new HashMap<>();

    public CPredefinitionImpl(String perAttributeKey, List<CPredefinitionValueImpl> predefinitionValues) {
        this.perAttributeKey = perAttributeKey;
        if(predefinitionValues!=null)
            for (CPredefinitionValue cPredefinitionValue : predefinitionValues) this.ePredefinitionValues.put(cPredefinitionValue.getPerAttributeValue(), cPredefinitionValue);


    }
    public CPredefinitionImpl(String perAttributeKey, CPredefinitionValueImpl... predefinitionValues) {
        this.perAttributeKey = perAttributeKey;
        if(predefinitionValues!=null)
            for (CPredefinitionValue cPredefinitionValue : predefinitionValues) this.ePredefinitionValues.put(cPredefinitionValue.getPerAttributeValue(), cPredefinitionValue);

    }



    public String getPerAttributeKey() {
        return perAttributeKey;
    }

    public Map<String,CPredefinitionValue> getPredefinitionValues() {
        return Collections.unmodifiableMap(this.ePredefinitionValues);
    }





    //For JAXB


    private CPredefinitionImpl() {
        this.perAttributeKey = null;
    }

    @XmlElement(type=CPredefinitionValueImpl.class, name="predefinitionValue")
    private List<CPredefinitionValue> getEPredefinitionValuesAsList()
    {
        return new ArrayList<CPredefinitionValue>(this.ePredefinitionValues.values());
    }

    private void setEPredefinitionValuesAsList(List<CPredefinitionValue> list)
    {
        for(CPredefinitionValue cPredefinitionValue : list)
        {
            this.ePredefinitionValues.put(cPredefinitionValue.getPerAttributeValue(), cPredefinitionValue);
        }
    }
}
