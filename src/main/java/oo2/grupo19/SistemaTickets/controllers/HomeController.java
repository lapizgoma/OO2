package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import oo2.grupo19.SistemaTickets.entities.Usuario;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
    
        // Obtener el principal de la autenticación
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Verificar si el principal es una instancia de Usuario
        if (principal instanceof Usuario) {
            Usuario usuario = (Usuario) principal; // Realizar el cast de forma segura
            mav.addObject("name", usuario.getNombre());
        } else {
            // Manejar el caso en que el principal no es un Usuario
            mav.addObject("name", "Invitado");
        }
    
        return mav;
    }
    

}
