package org.hhz.nbareasoner.cbml.model.advanced;

import org.hhz.nbareasoner.cbml.model.base.CException;

/**
 * Created by mbehr on 07.05.2015.
 */
public class CNotApplicableException extends CException {
    public CNotApplicableException() {
        super();
    }

    public CNotApplicableException(String message) {
        super(message);
    }

    public CNotApplicableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CNotApplicableException(Throwable cause) {
        super(cause);
    }

    protected CNotApplicableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
