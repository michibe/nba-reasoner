package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading;

import java.util.Map;

/**
 * Created by mbehr on 04.05.2015.
 */
public interface CLoadingSourceDefinition {

    public String getClazz();

    public Map<String,CLoadingSourceParameter> getCLoadingSourceParameters();
}
