package org.hhz.nbareasoner.config.model.advanced.nba.ratings.exceptions;

/**
 * Created by mbehr on 13.05.2015.
 */
public class NbaRatingException extends NbaException {
    public NbaRatingException() {
        super();
    }

    public NbaRatingException(String message) {
        super(message);
    }

    public NbaRatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NbaRatingException(Throwable cause) {
        super(cause);
    }

    protected NbaRatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
