package org.hhz.nbareasoner.cbr.model.phase.retain;


import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;

/**
 * Created by mbehr on 01.04.2015.
 *
 * Basis erneuern
 */
public interface CbrRetainAgent {

    //Wen cahceId vorhanden sollte chaing möglichkeit genutzt werden
    public void retain(CCaseBase eCaseBase) throws CbrRetainException;

    //Er braucht die agents um diese eventuell zu locken dass nicht mehr funktioniert
    public boolean lockCaseBaseNecessary();
    public boolean lockRetrieveNecessary();
    public boolean lockReuseNecessary();


    public class CbrRetainException extends Exception
    {
        protected CbrRetainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        public CbrRetainException() {
            super();
        }

        public CbrRetainException(String message) {
            super(message);
        }

        public CbrRetainException(String message, Throwable cause) {
            super(message, cause);
        }

        public CbrRetainException(Throwable cause) {
            super(cause);
        }
    }

}
