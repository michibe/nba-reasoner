package org.hhz.nbareasoner.cbr.impl;

import org.springframework.http.HttpStatus;

/**
 * Created by mbehr on 01.06.2015.
 */
public class CbrRuntimeException extends RuntimeException
{

    private final String publicMessage;
    private final HttpStatus httpStatus;


    public CbrRuntimeException(String internAndPublicMessage,  HttpStatus httpStatus) {
        super(internAndPublicMessage);
        this.publicMessage = internAndPublicMessage;
        this.httpStatus = httpStatus;
    }
    public CbrRuntimeException(String internMessage, String publicMessage, HttpStatus httpStatus) {
        super(internMessage);
        this.publicMessage = publicMessage;
        this.httpStatus = httpStatus;
    }

    public CbrRuntimeException(String internMessage, String publicMessage) {
        super(internMessage);
        this.publicMessage = publicMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public CbrRuntimeException(String internMessage, String publicMessage, Throwable cause) {
        super(internMessage,cause);
        this.publicMessage = publicMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CbrRuntimeException(String internMessage, String publicMessage, HttpStatus httpStatus, Throwable cause) {
        super(internMessage,cause);
        this.publicMessage = publicMessage;
        this.httpStatus = httpStatus;
    }

    public String getPublicMessage() {
        return publicMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}