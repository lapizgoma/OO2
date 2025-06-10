package oo2.grupo19.SistemaTickets.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AccessDeniedException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.UserAlreadyExistException;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.UserAlreadyAuthenticatedException;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.UserNotFoundException;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.PersonaJuridicaAlreadyExists;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.PersonaJuridicaNotFound;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.ClienteAlreadyExistsException;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.ClienteServiceException;
import oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions.TicketNotFoundException;
import static oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions.EstadoIntervencionDeleteException;
import static oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions.EstadoIntervencionListException;
import static oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions.EstadoIntervencionNotFoundException;
import static oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions.EstadoIntervencionSaveException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDeniedException(AccessDeniedException ex, Model model){
        model.addAttribute("title", "Acceso Denegado (Custom)");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public String NotAuthorizedException(NotAuthorizedException ex, Model model){
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

    @ExceptionHandler(UserNotFoundException.class)
    public String UserNotFoundException(UserNotFoundException ex, Model model){
        model.addAttribute("title", "El objeto no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public String ClienteAlreadyExistsException(ClienteAlreadyExistsException ex, Model model) {
        model.addAttribute("title", "El cliente ya existe!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(ClienteServiceException.class)
    public String ClienteServiceException(ClienteServiceException ex, Model model) {
        model.addAttribute("title", "Error en el servicio de cliente");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
    public String UserAlreadyAuthenticatedException(UserAlreadyAuthenticatedException ex, Model model){
        model.addAttribute("title", "El usuario ya está autenticado!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(PersonaJuridicaNotFound.class)
    public String PersonaJuridicaNotFound(PersonaJuridicaNotFound ex, Model model){
        model.addAttribute("title", "¡La Persona Jurídica no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public String TicketNotFoundException(TicketNotFoundException ex, Model model){
        model.addAttribute("title", "El Ticket no existe en la base de datos!");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(NotFoundException.class)
    public String NotFoundException(NotFoundException ex, Model model){
        model.addAttribute("title", "No encontrado");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(EstadoIntervencionDeleteException.class)
    public String handleEstadoIntervencionDeleteException(EstadoIntervencionDeleteException ex, Model model) {
        model.addAttribute("title", "Error al eliminar estado de intervención");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(EstadoIntervencionListException.class)
    public String handleEstadoIntervencionListException(EstadoIntervencionListException ex, Model model) {
        model.addAttribute("title", "Error al listar estados de intervención");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(EstadoIntervencionNotFoundException.class)
    public String handleEstadoIntervencionNotFoundException(EstadoIntervencionNotFoundException ex, Model model) {
        model.addAttribute("title", "Estado de intervención no encontrado");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(EstadoIntervencionSaveException.class)
    public String handleEstadoIntervencionSaveException(EstadoIntervencionSaveException ex, Model model) {
        model.addAttribute("title", "Error al guardar estado de intervención");
        model.addAttribute("mensaje", ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }

    @ExceptionHandler(Exception.class)
    public String CustomException(Exception ex, Model model){
        model.addAttribute("title", "Error inesperado");
        model.addAttribute("mensaje", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return ViewRouteHelper.ERROR_INDEX;
    }
}
