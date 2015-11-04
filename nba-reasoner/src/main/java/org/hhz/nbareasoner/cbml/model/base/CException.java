package org.hhz.nbareasoner.cbml.model.base;

/**
 * Created by mbehr on 02.05.2015.
 */
public class CException extends Exception {

    public CException() {
        super();
    }

    public CException(String message) {
        super(message);
    }

    public CException(String message, Throwable cause) {
        super(message, cause);
    }

    public CException(Throwable cause) {
        super(cause);
    }

    protected CException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
