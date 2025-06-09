package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController {
    @RequestMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(HttpServletRequest request) {
        // Redirige a /home para cualquier ruta no existente
        return new ModelAndView("redirect:/home");
    }
}
