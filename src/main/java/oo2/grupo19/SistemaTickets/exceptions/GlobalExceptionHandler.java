package oo2.grupo19.SistemaTickets.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(NoResourceFoundException.class)
    public String NoResourceFoundException(NoResourceFoundException ex, Model model) {
        model.addAttribute("title", "No Resource Found");
        model.addAttribute("message", "La pagina en donde te estas dirigiendo no existe o no se encuentra disponible.");
        return ViewRouteHelper.ERROR_INDEX;
    }

    // Maneja excepciones de validación para @Valid en controladores
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("atributo", ((FieldError) error).getField());
                    errorMap.put("mensaje", error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());

        Map<String, List<Map<String, String>>> response = new HashMap<>();
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Maneja excepciones de validación para ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<Map<String, String>>>> handleConstraintViolation(ConstraintViolationException ex) {
        List<Map<String, String>> errors = ex.getConstraintViolations().stream()
                .map(violation -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("atributo", violation.getPropertyPath().toString());
                    errorMap.put("mensaje", violation.getMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());

        Map<String, List<Map<String, String>>> response = new HashMap<>();
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
