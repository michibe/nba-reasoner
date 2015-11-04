package org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation;

import java.util.Map;

/**
 * Created by mbehr on 04.05.2015.
 *
 * Every computation is defined in the cbml-xml via the computation element (<computation class=''></computation>).
 * There must be a value for the class attribute, which contains the fully qualified name to the computation class.
 * Some computations need parameters. They can be specified within the computation element like (<computation class=''><computationParameter key='' value=''/></computation>)
 *
 */
public interface CComputationDefinition {

    /**
     * @return the fully qualified name of a class, which contains the logic for the specific computation
     */
    public String getClazz();

    /**
     * @return the parameters specified for the computation referenced via the class attribute
     */
    public Map<String,CComputationParameter> getCComputationParameters();
}
