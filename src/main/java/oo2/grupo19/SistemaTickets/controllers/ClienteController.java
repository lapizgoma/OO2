package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;

@Controller
@RequestMapping("/cuenta")
@Log4j2
public class ClienteController {
    private ClienteServiceImpl clienteService;

    public ClienteController (ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @PreAuthorize ("hasRole ('USER')")
    @GetMapping("/eliminar")
    public String confirmationPage(@RequestParam(required = false) Boolean confirmed, Authentication authentication, HttpServletRequest request) {
        if (confirmed == null || !confirmed) 
        {
            return ViewRouteHelper.CONFIRMATION_QUESTION;
        }
        
        clienteService.eliminarCliente (authentication.getName ());

        // una vez eliminada, se cierra sesión.
        new SecurityContextLogoutHandler ().logout (request, null, null);

        return ViewRouteHelper.REMOVAL_SUCCESS;
    }

}
