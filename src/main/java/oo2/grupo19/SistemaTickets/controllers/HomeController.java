package oo2.grupo19.SistemaTickets.controllers;

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
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Log4j2
public class HomeController {

    private final UsuarioServiceImpl usuarioService;
    private final ITicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(UsuarioServiceImpl usuarioService, ITicketService ticketService) {
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
    public String home() {
        return ViewRouteHelper.INDEX;
    }
    
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return ViewRouteHelper.INDEX_ADMIN;
    }

    @GetMapping("/empleado/home")
    public String empleadoHome(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return ViewRouteHelper.INDEX_EMPLOYEE;
    }
    @PreAuthorize("hasRole('ROLE_USER')") 
    @GetMapping("/cliente/home")
    public String clienteHome(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<Ticket> tickets = ticketService.traerPorCliente(email);
        model.addAttribute("tickets", tickets);
        return ViewRouteHelper.INDEX_USER;
    }

}
