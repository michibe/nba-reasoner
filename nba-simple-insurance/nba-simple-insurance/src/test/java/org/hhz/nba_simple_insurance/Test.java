package org.hhz.nba_simple_insurance;


import org.hhz.nbareasoner.cbml.impl.base.CbmlImpl;
import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.Cbml;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by mbehr on 08.05.2015.
 */
public class Test {

    @org.junit.Test
    public void test() throws IOException, JAXBException, CException {

        String cbmlPath = System.getProperty("user.dir")+"/src/test/java/org/hhz/nba_simple_insurance/cbml.xml";

        Cbml cbml = CbmlImpl.loadCbml(new File(cbmlPath));



        cbml.getCDefinition().serializeToOutputStream(System.out);


        cbml.getCCaseBase().renew();


        // cbml.serializeToOutputStream(System.out);

        String path = "file:///"+System.getProperty("user.dir")+"/src/test/java/org/hhz/nba_simple_insurance/generatedCaseBaseTmp.xml";

        cbml.serializeToFile(new File(URI.create(path.replace("\\", "/"))));




    }





}
