package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

@Controller
@Log4j2
public class HomeController {

    @Autowired
    private IUsuario usuarioRepository;

    // Inyecta el nombre del usuario en TODAS las vistas
    @ModelAttribute("nombreUsuario")
    public String getNombreUsuario(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Email usado en el login
            Usuario usuario = usuarioRepository.findByContactoEmail(email).orElse(null);
            log.info("Usuario HOME: " + usuario.getNombre());
            return (usuario != null) ? usuario.getNombre() : "Invitado";
        }
        return "Invitado";
    }

    @GetMapping("/home")
    public String home() {
        return ViewRouteHelper.INDEX;
    }
    

}
