package oo2.grupo19.SistemaTickets.controllers;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IUsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Log4j2
public class HomeController <T> {

    private final IUsuarioService usuarioService;
    private final ITicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(IUsuarioService usuarioService, ITicketService ticketService) {
        this.usuarioService = usuarioService;
        this.ticketService = ticketService;
    }
    
    // Inyecta el nombre del usuario en TODAS las vistas
    @ModelAttribute("nombreUsuario")
    public String getNombreUsuario(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Email usado en el login
            Usuario usuario = usuarioService.findByEmail(email).orElse(null);
            if (usuario != null) {
                logger.info("Usuario HOME: {} (email: {})", usuario.getNombre(), email);
                return usuario.getNombre();
            } else {
                logger.warn("Intento de acceso a HOME con usuario no encontrado en la base de datos: {}", email);
                return "Invitado";
            }
        }
        return "Invitado";
    }

    @GetMapping("/home")
    public String home(Authentication auth) {
        if(auth != null && auth.isAuthenticated()){
            String rol = typeUserAuthenticated(auth);
            log.info("Role Home 1: " + rol);
            String rolClean = rol.replace("ROLE_", "").toLowerCase();
            return "redirect:/" + rolClean + "/home";
        }
        return ViewRouteHelper.INDEX;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    @GetMapping("/{rol}/home")
    public String homeGlobal(@PathVariable String rol, Authentication authentication, Model model){
        String email = authentication.getName();
        simplifyGlobalHome(email,model);
        log.info(rol);
        switch (rol) {
            case "customer":
                return ViewRouteHelper.INDEX_USER;
            case "employee":
                return ViewRouteHelper.INDEX_EMPLOYEE;
            case "admin":
                return ViewRouteHelper.INDEX_ADMIN;
            default:
                throw new StatusCustomExceptions.AccessDeniedException("No tienes acceso a esta vista :/");
        }
    }

    private String typeUserAuthenticated(Authentication auth){
        return auth.getAuthorities().iterator().next().getAuthority(); // Si el usuario tiene mas de un rol, utilizar otro tipo de busqueda
    }

    private void simplifyGlobalHome(String email, Model model){
        List<Ticket> tickets = ticketService.traerPorCliente(email);
        model.addAttribute("tickets", tickets);
    }

}
