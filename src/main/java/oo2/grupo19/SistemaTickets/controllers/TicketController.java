package oo2.grupo19.SistemaTickets.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/ticket")
@SessionAttributes("cliente")
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final IEstadoTicket estadoTicketRepository;
    private final IEstadoIntervencion estadoIntervencion;

    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IEstadoTicket estadoTicketRepository, IEstadoIntervencion estadoIntervencion) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
        this.estadoIntervencion = estadoIntervencion;
    }

    @GetMapping("/create")
    public String createTicket(Model model,HttpSession session){
        Ticket ticket = new Ticket();
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        log.info(cliente);
        if(cliente != null){
            model.addAttribute("ticket",ticket);
            // Aqui deberia redirigir a un HTML de error.
            return "ticket/formTicket";
        }
        model.addAttribute("title", "No tienes permiso para entrar aqui");
        return ViewRouteHelper.ERROR_404;
    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute Ticket ticket,
                            HttpSession session,
                            Model model,
                            @RequestParam("mensaje") String contenido){
        
        
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if(cliente == null){
            model.addAttribute("title", "El cliente no esta logueado o no existe");
            return ViewRouteHelper.ERROR_404;
        }
        log.info("POST TICKET: " + cliente);
        Usuario clienteDb = usuarioService.findById(cliente.getId()).orElseThrow();
        Intervencion mensaje = new Intervencion();
        mensaje.setDescripcion(contenido);
        mensaje.setRealizadoPor(clienteDb);
        mensaje.setTipo(null);
        mensaje.setEstado(estadoIntervencion.findById(1L).get());
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.agregarMensaje(mensaje);
        ticket.setDetalle("BLA BLA");
        ticket.setCreadoPor(clienteDb);
        log.info("MENSAJE: " + mensaje);
        ticketService.save(ticket);
        
        return "notify/ticketSuccess";
    }

}
