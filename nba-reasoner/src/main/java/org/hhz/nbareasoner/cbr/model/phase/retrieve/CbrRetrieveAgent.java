package org.hhz.nbareasoner.cbr.model.phase.retrieve;


import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Created by mbehr on 01.04.2015.
 */
public interface CbrRetrieveAgent <RETRIEVE_RESULT extends CbrRetrieveResult> {

    //Wen cahceId vorhanden sollte chaing möglichkeit genutzt werden
    public RETRIEVE_RESULT retrieve(@NotNull CCaseBase eCaseBase, @NotNull CCase retrieveForCase,@NotNull Map<String,Double> customWeights) throws CbrRetrieveException;


    //wenn er geschlossen ist darf er nciht arbeiten, dann werden eventuell von anderem agent zb. retain die datenbasis verändert
    //darauf aufpassen wenn er gelocked ist wird er vom CbrManager nicht zurückgegeben



    public class CbrRetrieveException extends Exception
    {
        protected CbrRetrieveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        public CbrRetrieveException() {
            super();
        }

        public CbrRetrieveException(String message) {
            super(message);
        }

        public CbrRetrieveException(String message, Throwable cause) {
            super(message, cause);
        }

        public CbrRetrieveException(Throwable cause) {
            super(cause);
        }
    }

}
