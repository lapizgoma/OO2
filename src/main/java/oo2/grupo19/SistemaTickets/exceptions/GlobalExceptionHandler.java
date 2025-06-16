package oo2.grupo19.SistemaTickets.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AccessDeniedException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AlreadyExistsException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.InvalidInputException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDeniedException(AccessDeniedException ex, Model model){
        model.addAttribute("title", "Access Denied");
        model.addAttribute("message", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public String NotAuthorizedException(NotAuthorizedException ex, Model model){
        model.addAttribute("title", "Not Authorized");
        model.addAttribute("message", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public String AlreadyExistsException(AlreadyExistsException ex, Model model){
        model.addAttribute("title", "Already Exists");
        model.addAttribute("message", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(NotFoundException.class)
    public String NotFoundException(NotFoundException ex, Model model){
        model.addAttribute("title", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }
    
    // @ExceptionHandler(Exception.class)
    // public String CustomException(Exception ex, Model model){
    //     model.addAttribute("title", "Error");
    //     model.addAttribute("message", String.format("Unexpected error: %s (%s)", ex.getMessage(), ex.getCause()));
    //     return ViewRouteHelper.ERROR_INDEX;
    // }

    @ExceptionHandler(InvalidInputException.class)
    public String InvalidInputException(InvalidInputException ex, Model model) {
        model.addAttribute("title", "Invalid Input");
        model.addAttribute("message", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }
}
