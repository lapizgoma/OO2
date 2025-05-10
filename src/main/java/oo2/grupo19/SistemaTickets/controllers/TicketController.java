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

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/ticket")
@SessionAttributes({"cliente"})
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final IEstadoTicket estadoTicketRepository;

    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IEstadoTicket estadoTicketRepository) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
    }

    // Se le agrega este mini constructor para poder seguir navegando con las sessiones
    @ModelAttribute("cliente")
    public Cliente cliente() {
        return new Cliente();
    }

    @GetMapping("/create/user")
    public String createUser(Model model){
        model.addAttribute("cliente", cliente());
        return "ticket/formCliente";
    }

    @PostMapping("/create/user")
    public String createUser(@ModelAttribute Usuario usuario, Model model, RedirectAttributes redirectAttributes){
        Optional<Usuario> existente = usuarioService.findByEmail(usuario.getContacto().getEmail());
        if (existente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El cliente ya existe");
            return "redirect:/ticket/create/user";
        }
        usuario.setDeleted(false);
        return "redirect:/ticket/create";
    }

    @GetMapping("/create")
    public String createTicket(Model model){
        Ticket ticket = new Ticket();
        model.addAttribute("ticket",ticket);
        return "ticket/formTicket";
    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute Ticket ticket,
                            @ModelAttribute("cliente") Cliente cliente,
                            @RequestParam("mensaje") String contenido){
        
        
        Intervencion mensaje = new Intervencion();
        mensaje.setDescripcion(contenido);
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setRealizadoPor(cliente);
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.agregarMensaje(mensaje);
        ticket.setCreadoPor(cliente);
        log.info("ticket completo: " + ticket + " cliente: " + ticket.getCreadoPor().toString());
        ticketService.save(ticket);
        
        return "notify/ticketSuccess";
    }

}
