package org.hhz.nbareasoner.cbml.impl.advanced.computing;


import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.CAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationParameter;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.computation.CComputationDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedAttribute;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.computing.CAttributeComputation;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mbehr on 04.05.2015.
 */
public abstract class CAttributeComputationImplAbs implements CAttributeComputation {

    protected final CComputationDefinition eComputationDefinition;
    private final String description;


    protected CAttributeComputationImplAbs(CComputationDefinition eComputationDefinition, String description) {
        this.eComputationDefinition = eComputationDefinition;
        this.description = description;
    }

    @Override
    public CComputationDefinition getCComputationDefinition() {
        return eComputationDefinition;
    }

    //Hier wird schon einmal die validation für die benötigten attribute ausgeführt
    //diese methode sollte über super in eigener isApplicable methode aufgerufen werden
    //oder wenn keine weitere voraussetzungen zu obligatedAttriutes gegeben sind, reicht diese mthoede
    @Override
    public boolean isApplicable(CCaseBaseDefinition cCaseBaseDefinition) throws CNotApplicableException {



        //Check computation Attributes

        for(CObligatedParameter cObligatedParameter : this.getCObligatedParameters())
        {
            CComputationParameter cComputationParameter = this.eComputationDefinition.getCComputationParameters().get(cObligatedParameter.getName());
            if(cComputationParameter ==null)
            {
                throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing computationAttribute '" + cObligatedParameter +"'");

            }

            if(cObligatedParameter.getAllowedValues().size()>0 && !cObligatedParameter.getAllowedValues().contains(cComputationParameter.getValue())  )
            {


                throw new CNotApplicableException("Loading '" + this.eComputationDefinition.getClazz() + "' not applicable because value '" + cComputationParameter.getValue()+"' is not in allowed for obligatedDefinitionAttribute '"+ cObligatedParameter +"'");

            }

        }


        //Check if obligated attributes for this computation are defined in the eecd
        for(CObligatedAttribute cObligatedAttribute : this.getObligatedAttributes())
        {
           switch (cObligatedAttribute.getForCMainComponentType()) {
               case CASE_BASE: {
                   CAttributeDefinition eAttributeDefinition;
                   if((eAttributeDefinition = cCaseBaseDefinition.getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                   {
                     throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing caseBase-attribute '" + cObligatedAttribute.getKey()+"'");
                   }
                   else
                   {
                       if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                       {
                           throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for caseBase not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                       }
                   }


                   break;
               }
               case CASE: {
                   CAttributeDefinition eAttributeDefinition;
                   if((eAttributeDefinition = cCaseBaseDefinition.getCCaseDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                   {
                     throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing case-attribute '" + cObligatedAttribute.getKey()+"'");
                   }
                   else
                   {
                       if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                       {
                           throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for case not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                       }
                   }


                   break;
               }
               case CASE_INCIDENT: {
                   CAttributeDefinition eAttributeDefinition;
                   if(cCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition()!=null)
                   {
                      if((eAttributeDefinition = cCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                      {
                          throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing incident(activity)-attribute '" + cObligatedAttribute.getKey()+"'");
                      }
                      else
                      {
                          if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                          {
                              throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                          }
                      }
                   }



                   if(cCaseBaseDefinition.getCCaseDefinition().getEEventDefinition()!=null)
                   {
                       if((eAttributeDefinition = cCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                       {
                           throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing incident(event)-attribute '" + cObligatedAttribute.getKey()+"'");

                       }
                       else
                       {
                           if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                           {
                               throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                           }
                       }
                    }



                   break;
               }
               case ACTIVITY: {
                   CAttributeDefinition eAttributeDefinition;
                   if((eAttributeDefinition = cCaseBaseDefinition.getCCaseDefinition().getEActivityDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                   {
                     throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing activity-attribute '" + cObligatedAttribute.getKey()+"'");
                   }
                   else
                   {
                       if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                       {
                           throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for activity not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                       }
                   }

                   break;
               }
               case EVENT: {
                   CAttributeDefinition eAttributeDefinition;
                   if((eAttributeDefinition = cCaseBaseDefinition.getCCaseDefinition().getEEventDefinition().getCAttributeDefinition(cObligatedAttribute.getKey()))==null)
                   {
                     throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' not applicable due to missing event-attribute '" + cObligatedAttribute.getKey()+"'");
                   }
                   else
                   {
                       if(!cObligatedAttribute.getType().contains( eAttributeDefinition.getType()))
                       {
                           throw new CNotApplicableException("Computation '" + this.eComputationDefinition.getClazz() + "' for event not applicable due to missing supported type '" + eAttributeDefinition.getType()+"' for obligated attribute '" + cObligatedAttribute.getKey()+"'");

                       }
                   }

                   break;
               }
           }
        }




        return true;
    }

    @NotNull
    @Override
    public String getDescription() {
        return null;
    }
}
