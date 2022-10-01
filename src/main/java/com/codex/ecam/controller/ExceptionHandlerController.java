package com.codex.ecam.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.security.Principal;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({NoHandlerFoundException.class, TemplateInputException.class})
    public ModelAndView handleException(NoHandlerFoundException exception, Principal principal) {
        exception.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("error/page-not-found");
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException(AccessDeniedException exception, Principal principal) {
        exception.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("error/access-denied");
        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleException(DataIntegrityViolationException exception, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("error/foreign-key-error");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception, Principal principal) {
        exception.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("error/internal-error");
        return modelAndView;
    }

}
