package oo2.grupo19.SistemaTickets.controllers;

import oo2.grupo19.SistemaTickets.services.IClienteService;
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

import static oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Log4j2
@RequestMapping("/cuenta")
public class ClienteController {
    private IClienteService clienteService;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController (IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PreAuthorize ("hasRole ('CUSTOMER')")
    @GetMapping("/eliminar")
    public String confirmationPage(@RequestParam(required = false) Boolean confirmed, Authentication authentication, HttpServletRequest request) {
        if (confirmed == null || !confirmed) 
        {
            logger.info("Solicitud de confirmación para eliminar cuenta del usuario: {}", authentication != null ? authentication.getName() : "desconocido");
            return ViewRouteHelper.CONFIRMATION_QUESTION;
        }
        try {
            Long id = clienteService.findByEmail(authentication.getName()).getId();
            clienteService.delete(id);
            logger.info("Cuenta eliminada exitosamente para el usuario: {}", authentication.getName());
        } catch (Exception e) {
            logger.error("Error al eliminar la cuenta del usuario: {}", authentication.getName(), e);
            throw new NotFoundException("No se pudo eliminar la cuenta del usuario: " + authentication.getName());
        }
        new SecurityContextLogoutHandler().logout(request, null, null);
        return ViewRouteHelper.REMOVAL_SUCCESS;
    }

}
