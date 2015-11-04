package org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions;

/**
 * Created by mbehr on 12.03.2015.
 */
public class NotComputableException extends SimilarityMeasureException {
    public NotComputableException() {
        super();
    }

    public NotComputableException(String message) {
        super(message);
    }

    public NotComputableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotComputableException(Throwable cause) {
        super(cause);
    }

    protected NotComputableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
