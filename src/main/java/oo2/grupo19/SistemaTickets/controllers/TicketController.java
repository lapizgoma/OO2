package oo2.grupo19.SistemaTickets.controllers;

import java.time.LocalDate;
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
import oo2.grupo19.SistemaTickets.entities.Mensaje;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.enums.EstadosTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/ticket")
@SessionAttributes({"usuario"})
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
    }

    // Se le agrega este mini constructor para poder seguir navegando con las sessiones
    @ModelAttribute("usuario")
    public Usuario usuario() {
        return new Usuario();
    }

    @GetMapping("/create/user")
    public String createUser(Model model){
        model.addAttribute("usuario", usuario());
        return "ticket/formCliente";
    }

    @PostMapping("/create/user")
    public String createUser(@ModelAttribute Usuario usuario, Model model, RedirectAttributes redirectAttributes){
        Optional<Usuario> existente = usuarioService.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El usuario ya existe");
            return "redirect:/ticket/create/user";
        }
        usuario.setDeleted(false);
        usuario.setPassword(null);
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
                            @ModelAttribute("usuario") Usuario usuario,
                            @RequestParam("mensaje") String contenido){
        
        
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setFecha(LocalDate.now());
        mensaje.setUsuario(usuario);
        ticket.setEstado(EstadosTicket.PENDIENTE);
        ticket.agregarMensaje(mensaje);
        ticket.setCliente(usuario);
        log.info("ticket completo: " + ticket + " cliente: " + ticket.getCliente().toString());
        ticketService.save(ticket);
        
        return "notify/ticketSuccess";
    }

}
