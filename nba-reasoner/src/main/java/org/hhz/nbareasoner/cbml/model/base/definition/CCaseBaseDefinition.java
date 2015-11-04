package org.hhz.nbareasoner.cbml.model.base.definition;


import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBException;
import java.io.OutputStream;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CCaseBaseDefinition extends CComponentDefinition {

    @NotNull
    public CCaseDefinition getCCaseDefinition();

    public void serializeToOutputStream(@NotNull OutputStream outputStream) throws JAXBException;
    public void validate() throws CValidationException;

}
