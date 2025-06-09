package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Log4j2
public class HomeController {

    private final UsuarioServiceImpl usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
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
        logger.info("Acceso a HOME sin autenticación");
        return "Invitado";
    }

    @GetMapping("/home")
    public String home() {
        return ViewRouteHelper.INDEX;
    }
    

}
