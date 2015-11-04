package org.hhz.nbareasoner.cbml.impl.base;

import org.hhz.nbareasoner.cbml.impl.base.casebase.*;
import org.hhz.nbareasoner.cbml.impl.base.definition.CCaseBaseDefinitionImpl;
import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.cbml.model.base.data.CActivity;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.init.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;


/**
 * Created by mbehr on 27.04.2015.
 * cbml = case base modelling language
 *
**/

@XmlRootElement(name = "cbml")
public class CbmlImpl implements Cbml {

    private final static Logger logger = LoggerFactory.getLogger(CbmlImpl.class);

    @XmlElement(name="caseBaseDefinition", type=CCaseBaseDefinitionImpl.class)
    private final CCaseBaseDefinition eCaseBaseDefinition;


    @XmlJavaTypeAdapter(CCaseBaseJaxbAdapter.class)
    @XmlElement(name="caseBase")
    private final CCaseBase cCaseBase;

    public CbmlImpl(CCaseBaseDefinition eCaseBaseDefinition, CCaseBase cCaseBase) {
        this.eCaseBaseDefinition = eCaseBaseDefinition;
        this.cCaseBase = cCaseBase;
    }



    //This method should be called when xml is loaded and it should validate the xml
    //Hier sollte auch validiert werden ob alle pflichattribute definiert wurden

    @Override
    public void validate() throws CValidationException {

        //TODO implement validation against a xsd file for cbml
        //TODO implement validation of this file if all necessary attributes are loaded etc.
        eCaseBaseDefinition.validate();
        cCaseBase.validateAfterComputations();

    }

    @Override
    public CCaseBaseDefinition getCDefinition() {
        return this.eCaseBaseDefinition;
    }

    @Override
    public CCaseBase getCCaseBase() {
        return this.cCaseBase;
    }

    @Override
    public void serializeToOutputStream(OutputStream outputStream) throws JAXBException {


        JAXBContext jaxbContext = JAXBContext.newInstance(CbmlImpl.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        jaxbMarshaller.marshal(this, outputStream);
    }

    @Override
    public void serializeToFile(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CbmlImpl.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(this, file);
    }









    //for jaxb
    private CbmlImpl()
    {
        this.eCaseBaseDefinition = null;
        this.cCaseBase = null;
    }


    public static Cbml loadCbml(File file) throws IOException, JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(CbmlImpl.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //Properties wie Beispielsweise ${System.app.dir} müssen in cbml ersetzt werden
            //Als erstes muss das XML in einen String geladen werden

        String content = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
            //JAXB kann einen reader unmarhalen, dh. die espressions werden ersetzt und anschliesen der String in einen String Reader umgewandelt

        StringReader reader = new StringReader(App.replaceExpressionsWithProperty(content));

        Cbml cbml = (Cbml) jaxbUnmarshaller.unmarshal(reader);


        return cbml;
    }







}
