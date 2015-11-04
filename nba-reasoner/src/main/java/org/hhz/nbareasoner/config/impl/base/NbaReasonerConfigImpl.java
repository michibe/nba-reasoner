package org.hhz.nbareasoner.config.impl.base;

import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.CComponentDefinition;
import org.hhz.nbareasoner.config.impl.base.cbr.CbrConfigImpl;

import org.hhz.nbareasoner.config.impl.base.nba.NbaConfigImpl;
import org.hhz.nbareasoner.config.model.base.NbaReasonerConfig;
import org.hhz.nbareasoner.config.model.base.cbr.CbrConfig;
import org.hhz.nbareasoner.config.model.base.cbr.similarity.CaseSimilarityMeasureConfig;
import org.hhz.nbareasoner.config.model.base.nba.NbaConfig;
import org.hhz.nbareasoner.cbml.impl.base.casebase.CCaseImpl;
import org.hhz.nbareasoner.init.App;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;


/**
 * Created by mbehr on 12.05.2015.
 */
@XmlRootElement(name="nbaReasonerConfig")
public class NbaReasonerConfigImpl implements NbaReasonerConfig {



    @XmlElement(name="cbrConfig",type= CbrConfigImpl.class,required = true)
    private final CbrConfig cbrConfig;
    @XmlElement(name="nbaConfig", type = NbaConfigImpl.class,required = true)
    private final NbaConfig nbaConfig;


    public NbaReasonerConfigImpl(CbrConfig cbrConfig, NbaConfig nbaConfig) {
        this.cbrConfig = cbrConfig;
        this.nbaConfig = nbaConfig;
    }

    //This method should be called when xml is loaded and it should validate the xml
    //TODO Hier sollte auch validiert werden ob alle pflichattribute in cbml definiert wurden
    @Override
    public void validate(@NotNull CCaseBaseDefinition validateAgainst) throws ValidationException
    {
        //validate if all necessary elemnts in the config file alre availbale rg. </cbrConfig> </nbaConfig>
        //replace this validation with a validation against a xsd file
        if(cbrConfig==null)
        {
            throw new ValidationException("the nbe-reasoner-config.xml file does not contain the </cbrConfig> element.");

        }
        if(nbaConfig==null)
        {
            throw new ValidationException("the nbe-reasoner-config.xml file does not contain the </nbaConfig> element.");

        }

        //Chek if attributes for the similarityMeasurements exist
        // Because similaritymeasures are only invoked on case-attributes only case attributes must be checked
        for(CaseSimilarityMeasureConfig caseSimilarityMeasureConfig : cbrConfig.getCaseSimilarityMeasureConfigs())
        {

            if(validateIfAttributeInDefinition(validateAgainst.getCCaseDefinition(),caseSimilarityMeasureConfig.getAttributeKey()) )

            {

            }
            else
            {
             throw new ValidationException("the nbe-reasoner-config.xml file is using the attribute '"+caseSimilarityMeasureConfig.getAttributeKey()+"' that has no corresponding definition in the cbml file. Please create a definition for the attribute.");

            }


        }


    }



    private boolean validateIfAttributeInDefinition(CComponentDefinition cComponentDefinition,String attributeKey)
    {

            if(cComponentDefinition!=null && cComponentDefinition.getCAttributeDefinition(attributeKey)!=null)
            {
                return true;
            }
            else
            {
                return false;
            }

    }



    public CbrConfig getCbrConfig() {
        return cbrConfig;
    }

    public NbaConfig getNbaConfig() {
        return nbaConfig;
    }

    public static NbaReasonerConfig loadNbaReasonerConfig(@NotNull File file,@NotNull CCaseBaseDefinition validateAgainst) throws IOException, JAXBException {


        JAXBContext jaxbContext = JAXBContext.newInstance(NbaReasonerConfigImpl.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //Wichtig!!!!! hier properties ersetzen
        String content = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());


        StringReader reader = new StringReader(App.replaceExpressionsWithProperty(content));


        NbaReasonerConfig nbaReasonerConfig = (NbaReasonerConfigImpl) jaxbUnmarshaller.unmarshal(reader);


            nbaReasonerConfig.validate(validateAgainst);




        return nbaReasonerConfig;

    }





    @Override
    public void serializeToOutputStream(OutputStream outputStream) throws JAXBException {


        JAXBContext jaxbContext = JAXBContext.newInstance(NbaReasonerConfigImpl.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        jaxbMarshaller.marshal(this, outputStream);
    }

    @Override
    public void serializeToFile(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(NbaReasonerConfigImpl.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(this, file);
    }





//For JAXB


    public NbaReasonerConfigImpl() {
        this.cbrConfig = null;
        this.nbaConfig = null;
    }
}
