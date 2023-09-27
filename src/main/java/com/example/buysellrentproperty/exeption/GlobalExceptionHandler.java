package com.example.buysellrentproperty.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        logger.error("An error occurred:", ex);
        model.addAttribute("errorType", "InternalError");
        model.addAttribute("errorMessage", "An error occurred.");
        return "error-page";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException ex, Model model) {
        logger.info("Resource not found:", ex);
        model.addAttribute("errorType", "ResourceNotFound");
        model.addAttribute("errorMessage", "Resource not found.");
        return "error-page";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        logger.info("Resource not found:", ex);
        model.addAttribute("errorType", "ResourceNotFound");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-page";
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException ex) {
        logger.error("Input/Output Error:", ex);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("errorType", "IOError");
        modelAndView.addObject("errorMessage", "An input/output error occurred.");
        return modelAndView;
    }

    // Метод для проверки, отправлен ли уже ответ
    private boolean isResponseCommitted(HttpServletRequest request) {
        return (request.getAttribute("javax.servlet.error.exception") != null ||
                request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR") != null);
    }

}
