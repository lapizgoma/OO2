package oo2.grupo19.SistemaTickets.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oo2.grupo19.SistemaTickets.services.EmailService;
import oo2.grupo19.SistemaTickets.services.IClienteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/correo")
public class CorreoController {

    private final EmailService emailService;
    private final IClienteService clienteService;
    private static final Logger logger = LoggerFactory.getLogger(CorreoController.class);

    public CorreoController(EmailService emailService,IClienteService clienteService) {
        this.emailService = emailService;
        this.clienteService = clienteService;
    }

    @GetMapping("/texto")
    public String enviarTextoPlano(Authentication auth) {
        String email = clienteService.findByEmail(auth.getName()).getEmail();
        try {
            emailService.enviarCorreoPlano(email, "Prueba texto plano", "Hola desde Spring Boot.");
            logger.info("Correo de texto enviado a: {}", email);
        } catch (Exception e) {
            logger.error("Error al enviar correo de texto a: {}", email, e);
            throw e;
        }
        return "Correo de texto enviado.";
    }

    @GetMapping("/html")
    public Integer enviarCorreoHtml(Authentication auth) {
        Map<String, Object> datos = new HashMap<>();
         String email = clienteService.findByEmail(auth.getName()).getEmail();
        datos.put("nombre", email);
        try {
            emailService.enviarCorreoHtml(email, "Correo con HTML", "email", datos);
            logger.info("Correo HTML enviado a: {}", email);
        } catch (Exception e) {
            logger.error("Error al enviar correo HTML a: {}", email, e);
            throw e;
        }
        return 1;
    }

}
