package org.hhz.nbareasoner.cbr.impl;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mbehr on 13.08.2015.
 */

@ControllerAdvice
public class CbrExceptionManager {




    @ResponseBody
    @ExceptionHandler(value =  CbrException.class)
    public String defaultCbrExceptionHandler(HttpServletResponse response,HttpServletRequest request, CbrException e) {




        response.setStatus(e.getHttpStatus().value());


            // return as JSON
            String jsonString =
                    "{'exception': '"+e.getClass().getName()+"', 'message':'"+e.getPublicMessage()+"'}";


            return jsonString;



    }


    @ResponseBody
    @ExceptionHandler(value =  CbrRuntimeException.class)
    public String defaultCbrRuntimeExceptionHandler(HttpServletResponse response,HttpServletRequest request, CbrRuntimeException e) {




        response.setStatus(e.getHttpStatus().value());


            // return as JSON
            String jsonString =
                    "{'exception': '"+e.getClass().getName()+"', 'message':'"+e.getPublicMessage()+"'}";


            return jsonString;



    }


}
