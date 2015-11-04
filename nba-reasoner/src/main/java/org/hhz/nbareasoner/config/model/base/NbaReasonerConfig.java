package org.hhz.nbareasoner.config.model.base;


import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.config.model.base.cbr.CbrConfig;
import org.hhz.nbareasoner.config.model.base.nba.NbaConfig;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.*;
import java.io.File;
import java.io.OutputStream;

/**
 * Created by mbehr on 12.05.2015.
 */
public interface NbaReasonerConfig {



    //This method should be called when xml is loaded and it should validate the xml
    //Hier sollte auch validiert werden ob alle pflichattribute definiert wurden
    public void validate(@NotNull CCaseBaseDefinition validateAgainst) throws ValidationException;


    public CbrConfig getCbrConfig();

    public NbaConfig getNbaConfig();


    public void serializeToOutputStream(OutputStream outputStream) throws JAXBException;


    public void serializeToFile(File file) throws JAXBException;


}
