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

@RestController
@RequestMapping("/correo")
public class CorreoController {

    private final EmailService emailService;
    private final ClienteServiceImpl clienteService;
    public CorreoController(EmailService emailService,ClienteServiceImpl clienteService) {
        this.emailService = emailService;
        this.clienteService = clienteService;
    }

    @GetMapping("/texto")
    public String enviarTextoPlano(Authentication auth) {
        Cliente cliente = clienteService.findByEmail(auth.getName()).orElseThrow(() ->  new RuntimeException("El cliente no existe"));
        
        emailService.enviarCorreoPlano(cliente.getContacto().getEmail(), "Prueba texto plano", "Hola desde Spring Boot.");
        return "Correo de texto enviado.";
    }

    @GetMapping("/html")
    public String enviarCorreoHtml(Authentication auth) {
        Map<String, Object> datos = new HashMap<>();
        Cliente cliente = clienteService.findByEmail(auth.getName()).orElseThrow(() ->  new RuntimeException("El cliente no existe"));
        
        datos.put("nombre", cliente.getNombre());
        emailService.enviarCorreoHtml(cliente.getContacto().getEmail(), "Correo con HTML", "email", datos);
        return "Correo HTML enviado.";
    }
}
