package org.hhz.nbareasoner.cbml.model.base;

import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.data.CParent;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.OutputStream;

/**
 * Created by mbehr on 04.05.2015.
 *
 * Eine besodnere Form des EComponents
 */
public interface Cbml extends CParent {

    public CCaseBaseDefinition getCDefinition();
    public CCaseBase getCCaseBase();

    //check if all necessary (eg. timestamp) and all decalred attributes   are available for all elements
    public void validate() throws CValidationException;

    public void serializeToFile(File file) throws  JAXBException;
    public void serializeToOutputStream(OutputStream outputStream) throws JAXBException;


}
