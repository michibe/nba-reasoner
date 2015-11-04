package org.hhz.nbareasoner.cbml.impl.base.definition;


import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseDefinition;

import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by mbehr on 27.04.2015.
 */
@XmlRootElement(name="caseBaseDefinition")
public class CCaseBaseDefinitionImpl extends CComponentDefinitionImplAbs implements CCaseBaseDefinition {


    @XmlElement(name="caseDefinition",type=CCaseDefinitionImpl.class)
    private final CCaseDefinition eCaseDefinition;


    private CCaseBaseDefinitionImpl() {
        super(null,null,null);
        this.eCaseDefinition = null;
    }

    public CCaseBaseDefinitionImpl(List<CLoadedAttributeDefinition> loadedAttributeDefinitionsTmp, List<CPredefinedAttributeDefinition> predefinedAttributeDefinitionsTmp, List<CComputedAttributeDefinition> computedAttributeDefinitionsTmp, CCaseDefinition eCaseDefinition) {
        super(loadedAttributeDefinitionsTmp, predefinedAttributeDefinitionsTmp, computedAttributeDefinitionsTmp);
        this.eCaseDefinition = eCaseDefinition;
    }

    @Override
    public CCaseDefinition getCCaseDefinition() {
        return eCaseDefinition;
    }

    @Override
    public void serializeToOutputStream(@NotNull OutputStream outputStream) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        jaxbMarshaller.marshal(this, outputStream);
    }

    @Override
    public void validate() throws CValidationException {

    }


}
