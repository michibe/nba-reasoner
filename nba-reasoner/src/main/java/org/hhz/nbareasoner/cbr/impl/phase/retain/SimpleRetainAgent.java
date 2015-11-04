package org.hhz.nbareasoner.cbr.impl.phase.retain;


import org.hhz.nbareasoner.cbr.model.phase.retain.CbrRetainAgent;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;


/**
 * Created by m on 04.04.2015.
 */
public class SimpleRetainAgent implements CbrRetainAgent {
    @Override
    public void retain(CCaseBase eCaseBase) throws CbrRetainException {
        try {
            eCaseBase.renew();
        }catch (Exception e)
        {
            throw new CbrRetainException("Could not retain Closed Cases due to ", e );
        }
    }

    @Override
    public boolean lockCaseBaseNecessary() {
        return false;
    }

    @Override
    public boolean lockRetrieveNecessary() {
        return false;
    }

    @Override
    public boolean lockReuseNecessary() {
        return false;
    }
}
