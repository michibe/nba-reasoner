package org.hhz.nbareasoner.cbml.model.advanced;

import org.hhz.nbareasoner.cbml.model.base.CException;

/**
 * Created by mbehr on 07.05.2015.
 */
public class CLoadingSourceException extends CException {
    public CLoadingSourceException() {
        super();
    }

    public CLoadingSourceException(String message) {
        super(message);
    }

    public CLoadingSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CLoadingSourceException(Throwable cause) {
        super(cause);
    }

    protected CLoadingSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
