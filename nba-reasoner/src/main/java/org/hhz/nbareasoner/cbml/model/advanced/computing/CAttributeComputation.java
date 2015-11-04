package org.hhz.nbareasoner.cbml.model.advanced.computing;


import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mbehr on 11.05.2015.
 *
 * A computation that can be used in the cbml for computing computedAttributes
 */
public interface CAttributeComputation {


    /**
     * Every Computation has a definition element (<computation class='' />) inside the cbml-definition section.
     * Every registered Computation needs access to its definition element primarily to access potential registered parameters
     * @return the definition of a AttributeComputation that contains the class and specified parameters
     */
    @NotNull
    public CComputationDefinition getCComputationDefinition();

    /**
     * @return a description of the computation (what it does, what kind of parameters it needs)
     */
    @NotNull
    public String getDescription();

    //welche Computing Attribute sind notwendig

    /**
     * Every computation can specify some obligated parameters, which are necessary to run the computation.
     * These parameters must be present as computationParameter inside the computation definition (<computation></computation>) of a computation in the cbml.xml.
     * Thus it is guaranteed that the computation has access to the paramters inside the computation-logic.
     * @return  a list of obligated parameters
     */
    @NotNull
    public List<CObligatedParameter> getCObligatedParameters();

    //Welche Attribute insgesamt, also casebase, case oder incident attribute sind notwendig für die berechnungen

    /**
     * Every computation can be based on the presence of other attributes inside the cbml. By specifying the obligated Attributes for a computation
     * a availability-check on these attributes can be done before running the computation.
     * @return the obligated attributes this computation is based on
     */
    @NotNull
    public List<CObligatedAttribute> getObligatedAttributes();

    /**
     * This method checks if this computation is applicable for the given cbml data. It must be checked if all obligated attributes, and all obligated parameters are available.
     * @param cCaseBaseDefinition The casebase-definition of the underlying cbml
     * @return true if the computation is applicable for the given caseBasedefinition. If not a CNotApplicableException will be thrown
     * @throws CNotApplicableException
     */
    public boolean isApplicable(@NotNull CCaseBaseDefinition cCaseBaseDefinition) throws CNotApplicableException;
}
