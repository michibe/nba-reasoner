package org.hhz.nbareasoner.cbml.model.base;

/**
 * Created by mbehr on 02.05.2015.
 */
public class CValidationException extends CException {

    public CValidationException() {
        super();
    }

    public CValidationException(String message) {
        super(message);
    }

    public CValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CValidationException(Throwable cause) {
        super(cause);
    }

    protected CValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
