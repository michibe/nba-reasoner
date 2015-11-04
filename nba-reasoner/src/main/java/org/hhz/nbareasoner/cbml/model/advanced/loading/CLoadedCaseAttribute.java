package org.hhz.nbareasoner.cbml.model.advanced.loading;

/**
 * Created by mbehr on 07.05.2015.
 *
 * This class will be returned from a loading source when they are loading a case attribut from an external source
 *
 */
public interface CLoadedCaseAttribute {

    public String getBelongingCaseId();

    public String getAttributeValue();
}
