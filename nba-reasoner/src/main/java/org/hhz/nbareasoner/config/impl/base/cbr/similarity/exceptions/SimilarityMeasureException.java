package org.hhz.nbareasoner.config.impl.base.cbr.similarity.exceptions;

/**
 * Created by mbehr on 12.03.2015.
 */
public class SimilarityMeasureException extends Exception {
    public SimilarityMeasureException() {
        super();
    }

    public SimilarityMeasureException(String message) {
        super(message);
    }

    public SimilarityMeasureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimilarityMeasureException(Throwable cause) {
        super(cause);
    }

    protected SimilarityMeasureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
