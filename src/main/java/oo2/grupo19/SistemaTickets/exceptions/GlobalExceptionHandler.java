package oo2.grupo19.SistemaTickets.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAuthorizedException.class)
    public String NotAuthorizeException(NotAuthorizedException ex, Model model){
        model.addAttribute("title", "Usted no tiene permiso para ingresar aqui");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public String UserAlreadyExistException(UserAlreadyExistException ex, Model model){
        model.addAttribute("title", "El usuario ya existe!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(UserNotFounException.class)
    public String UserNotFounException(UserNotFounException ex, Model model){
        model.addAttribute("title", "El usuario no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
    public String UserAlreadyAuthenticatedException(UserAlreadyAuthenticatedException ex, Model model){
        model.addAttribute("title", "El usuario ya esta autenticado!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(TicketNotFound.class)
    public String TicketNotFound(TicketNotFound ex, Model model){
        model.addAttribute("title", "El ticket no se ha encontrado!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }
}
