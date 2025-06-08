package oo2.grupo19.SistemaTickets.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDeniedException(AccessDeniedException ex, Model model){
        model.addAttribute("title", "Acceso Denegado");
        model.addAttribute("mensaje", "No tienes permiso para acceder a este recurso");
        return ViewRouteHelper.ERROR_INDEX;
    }

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

    @ExceptionHandler(PersonaJuridicaAlreadyExists.class)
    public String PersonaJuridicaAlreadyExists(PersonaJuridicaAlreadyExists ex, Model model){
        model.addAttribute("title", "¡Persona Jurídica ya existe!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler({UserNotFounException.class,NotFoundException.class})
    public String UserNotFounException(UserNotFounException ex, Model model){
        model.addAttribute("title", "El objeto no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler ({TicketNotFoundException.class})
    public String TicketNotFoundException(TicketNotFoundException ex, Model model){
        model.addAttribute("title", "El Ticket no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage ());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler ({PersonaJuridicaNotFound.class})
    public String PersonaJuridicaNotFound(PersonaJuridicaNotFound ex, Model model){
        model.addAttribute("title", "¡La Persona Jurídica no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage ());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
    public String UserAlreadyAuthenticatedException(UserAlreadyAuthenticatedException ex, Model model){
        model.addAttribute("title", "El usuario ya esta autenticado!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }
}
