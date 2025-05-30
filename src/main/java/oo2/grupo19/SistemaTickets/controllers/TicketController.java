package oo2.grupo19.SistemaTickets.controllers;

import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/ticket")
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final ICliente clienteRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final IEstadoIntervencion estadoIntervencion;

    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IEstadoTicket estadoTicketRepository, IEstadoIntervencion estadoIntervencion, ICliente clienteRepository) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
        this.estadoIntervencion = estadoIntervencion;
        this.clienteRepository = clienteRepository;
    }

    // ... (otros campos y constructor permanecen igual)

    @GetMapping("/create")
    public String createTicket(Model model, Authentication authentication) {
        Ticket ticket = new Ticket();
        
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();
            
            // Buscar el cliente por email (ajusta según tu implementación)
            Cliente cliente = clienteRepository.findByContactoEmail(email).orElseThrow();
                
            if (cliente != null) {
                model.addAttribute("ticket", ticket);
                return "ticket/formTicket";
            }
        }
        
        model.addAttribute("title", "No tienes permiso para entrar aquí");
        return ViewRouteHelper.ERROR_404;
    }

    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute Ticket ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("title", "Debes iniciar sesión");
            return ViewRouteHelper.ERROR_404;
        }

        // Obtener el usuario autenticado
        String email = authentication.getName();
        Usuario clienteDb = usuarioService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!(clienteDb instanceof Cliente)) {
            model.addAttribute("title", "Solo clientes pueden crear tickets");
            return ViewRouteHelper.ERROR_404;
        }

        // Resto de la lógica...
        Intervencion mensaje = new Intervencion();
        mensaje.setDescripcion(contenido);
        mensaje.setRealizadoPor(clienteDb);
        mensaje.setTipo(null);
        mensaje.setEstado(estadoIntervencion.findById(1L).get());
        
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.agregarMensaje(mensaje);
        ticket.setDetalle("BLA BLA");
        ticket.setCreadoPor(clienteDb);
        
        ticketService.save(ticket);
        
        return "notify/ticketSuccess";
    }
}
