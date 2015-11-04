package examples.simpleinsurance;


import org.apache.commons.lang3.StringUtils;
import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.config.impl.base.NbaReasonerConfigImpl;
import org.hhz.nbareasoner.config.model.base.NbaReasonerConfig;
import org.hhz.nbareasoner.cbml.impl.base.CbmlImpl;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.util.extern.UriHelper;
import org.junit.Assert;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Created by mbehr on 08.05.2015.
 */
public class Test {

  @org.junit.Test
    public void testCbml() throws IOException, JAXBException, CException {
        String path = System.getProperty("user.dir")+"/src/test/java/examples/simpleinsurance/cbml.xml";

        Cbml cbml = CbmlImpl.loadCbml(new File(path));


        Assert.assertEquals("Case Base sollte keine Fälle enthalten.",0,cbml.getCCaseBase().getCCases().size());
        Assert.assertEquals("Case Base sollte keine Attribute enthalten.", 0, cbml.getCCaseBase().getCAttributes().size());


        Assert.assertEquals("Attribut Definition für numberOfCases konnte nicht gefunden werden", "numberOfCases", cbml.getCDefinition().getCAttributeDefinition("numberOfCases").getKey());
        Assert.assertEquals("Attribut Definition für numberOfCases konnte nicht gefunden werden", "numberOfCases", cbml.getCDefinition().getCComputedAttributeDefinition("numberOfCases").getKey());


        cbml.getCDefinition().serializeToOutputStream(System.out);



        cbml.getCCaseBase().renew();



       cbml.serializeToOutputStream(System.out);


        CCase eCase = cbml.getCCaseBase().getEOpenedCase("1429736815646");





    path = System.getProperty("user.dir")+"/src/test/java/examples/simpleinsurance/nba-reasoner-config.xml";

       NbaReasonerConfig nbaReasonerConfig = NbaReasonerConfigImpl.loadNbaReasonerConfig(new File(path), cbml.getCDefinition());

       nbaReasonerConfig.serializeToOutputStream(System.out);




    }


















}
