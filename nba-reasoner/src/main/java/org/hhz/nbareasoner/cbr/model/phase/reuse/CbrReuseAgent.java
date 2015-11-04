package org.hhz.nbareasoner.cbr.model.phase.reuse;

import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by mbehr on 01.04.2015.
 */
public interface CbrReuseAgent <RETRIEVE_RESULT extends CbrRetrieveResult, REUSE_RESULT extends CbrReuseResult> {

    public REUSE_RESULT reuse(RETRIEVE_RESULT retrieveResult, @NotNull Map<String,Double> customRatingMeasuresWeights) throws CbrReuseException;




    public class CbrReuseException extends Exception
    {
        protected CbrReuseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        public CbrReuseException() {
            super();
        }

        public CbrReuseException(String message) {
            super(message);
        }

        public CbrReuseException(String message, Throwable cause) {
            super(message, cause);
        }

        public CbrReuseException(Throwable cause) {
            super(cause);
        }
    }

}
