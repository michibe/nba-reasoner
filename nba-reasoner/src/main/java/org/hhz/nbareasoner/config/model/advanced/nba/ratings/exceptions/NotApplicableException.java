package org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NotApplicableException extends NbaRatingException {
    public NotApplicableException() {
        super();
    }

    public NotApplicableException(String message) {
        super(message);
    }

    public NotApplicableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotApplicableException(Throwable cause) {
        super(cause);
    }

    protected NotApplicableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
