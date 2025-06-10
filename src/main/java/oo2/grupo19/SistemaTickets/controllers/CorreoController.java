package oo2.grupo19.SistemaTickets.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.services.EmailService;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;

import static oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/correo")
public class CorreoController {

    private final EmailService emailService;
    private final ClienteServiceImpl clienteService;
    private static final Logger logger = LoggerFactory.getLogger(CorreoController.class);

    public CorreoController(EmailService emailService, ClienteServiceImpl clienteService) {
        this.emailService = emailService;
        this.clienteService = clienteService;
    }



    @GetMapping("/texto")
    public String enviarTextoPlano(Authentication auth) {
        Cliente cliente = clienteService.findByEmail(auth.getName())
            .orElseThrow(() -> {
                logger.warn("Intento de envío de correo de texto a un cliente inexistente: {}", auth.getName());
                return new UserNotFoundException("El cliente no existe");
            });
        try {
            emailService.enviarCorreoPlano(cliente.getContacto().getEmail(), "Prueba texto plano", "Hola desde Spring Boot.");
            logger.info("Correo de texto enviado a: {}", cliente.getContacto().getEmail());
        } catch (Exception e) {
            logger.error("Error al enviar correo de texto a: {}", cliente.getContacto().getEmail(), e);
            throw e;
        }
        return "Correo de texto enviado.";
    }

    @GetMapping("/html")
    public Integer enviarCorreoHtml(Authentication auth) {
        Map<String, Object> datos = new HashMap<>();
        Cliente cliente = clienteService.findByEmail(auth.getName())
            .orElseThrow(() -> {
                logger.warn("Intento de envío de correo HTML a un cliente inexistente: {}", auth.getName());
                return new UserNotFoundException("El cliente no existe");
            });
        datos.put("nombre", cliente.getNombre());
        try {
            emailService.enviarCorreoHtml(cliente.getContacto().getEmail(), "Correo con HTML", "email", datos);
            logger.info("Correo HTML enviado a: {}", cliente.getContacto().getEmail());
        } catch (Exception e) {
            logger.error("Error al enviar correo HTML a: {}", cliente.getContacto().getEmail(), e);
            throw e;
        }
        return 1;
    }

}
