package com.pitchbook.bootcamp.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = Logger.getLogger(ControllerExceptionHandler.class.getName());

    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(Exception ex) {
        log.log(Level.SEVERE, ex.getMessage());
        return new ModelAndView("error").addObject("errorMessage",
                "Sorry, something went wrong!");
    }

}
