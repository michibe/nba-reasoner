package org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NbaException extends RuntimeException {
    public NbaException() {
        super();
    }

    public NbaException(String message) {
        super(message);
    }

    public NbaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NbaException(Throwable cause) {
        super(cause);
    }

    protected NbaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
