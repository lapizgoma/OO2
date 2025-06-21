package oo2.grupo19.SistemaTickets.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.security.config.CustomUserDetails;

import static oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotAuthorizedException;

@Component
public class SecurityService {

    public Long getIdEmpleado(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new NotAuthorizedException("No hay usuario autenticado en la sesión actual.");
        }
        Object principal = auth.getPrincipal();
        if (!(principal instanceof Usuario userDetails)) {
            throw new NotAuthorizedException("El principal autenticado no es del tipo esperado.");
        }
        Usuario usuario = userDetails;
        if (!(usuario instanceof Empleado empleado)) {
            throw new NotAuthorizedException("El usuario autenticado no es un empleado válido.");
        }
        return empleado.getId();
    }

    // Mejora: método reutilizable para obtener el usuario autenticado
    public Usuario getUsuarioAutenticado(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new NotAuthorizedException("No hay usuario autenticado en la sesión actual.");
        }
        Object principal = auth.getPrincipal();
        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new NotAuthorizedException("El principal autenticado no es del tipo esperado.");
        }
        return userDetails.getUsuario();
    }
}