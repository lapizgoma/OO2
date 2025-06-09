package oo2.grupo19.SistemaTickets.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.security.util.CustomUserDetails;

@Component
public class SecurityService {

    public Long getIdEmpleado(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        if (!(usuario instanceof Empleado empleado)) {
            throw new NotAuthorizedException("El usuario no es un empleado.");
        }
        return empleado.getId();
    }
}