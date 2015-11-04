package org.hhz.nbareasoner.cbml.model.advanced.computing;


import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;


/**
 * Created by mbehr on 11.05.2015.
 */
public interface CCaseAttributeComputation <E> extends CAttributeComputation {

    public E compute(CCCase eCase);
    
  
}
