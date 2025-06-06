package oo2.grupo19.SistemaTickets.controllers;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import oo2.grupo19.SistemaTickets.exceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.UserNotFounException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

import javax.swing.text.html.Option;

@Controller
@RequestMapping("/ticket")
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final IEmpleado empleadoRepository;
    private final ICliente clienteRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final IPrioridad prioridadTicket;

    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IEstadoTicket estadoTicketRepository, IPrioridad prioridadTicket, ICliente clienteRepository,IEmpleado empleadoRepository) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
        this.prioridadTicket = prioridadTicket;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // ... (otros campos y constructor permanecen igual)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/create")
    public String createTicket(Model model, Authentication authentication) {
        Ticket ticket = new Ticket();
        log.info("Cliente: " + authentication.getName());
        if (isAuthenticated(authentication)) {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();
            
            // Buscar el cliente por email
            Optional<Cliente> cliente = clienteRepository.findByContactoEmail(email);
            
            if (cliente.isPresent()) {
                // Asociar el cliente al ticket si es necesario
                ticket.setCreadoPor(cliente.get());
                model.addAttribute("ticket", ticket);
                return "ticket/formTicket";
            } else {
                throw new UserNotFounException("Usted no es un cliente para realizar esta accion");
            }
        } else {
            throw new NotAuthorizedException("No tienes permiso para entrar aquí");
        }
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute Ticket ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        
        if (!isAuthenticated(authentication)) {
            throw new NotAuthorizedException("Debes iniciar sesión");
        }

        // Obtener el usuario autenticado
        String email = authentication.getName();
        Usuario clienteDb = usuarioService.findByEmail(email)
            .orElseThrow(() -> new UserNotFounException("Usuario no encontrado"));
        
        if (!(clienteDb instanceof Cliente)) {
            throw new NotAuthorizedException("Solo clientes pueden crear tickets");
        }
        
        
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.setCreadoPor(clienteDb);
        ticket.setDetalle(contenido);
        // Ser cuidadoso con esto. No esta probado, asi que puede fallar.
        ticket.setListEmpleado(empleadoRepository.findAll());
        ticketService.save(ticket);
        model.addAttribute("title","Ticket create");
        model.addAttribute("titulo-h1","El ticket ha sido creado con exito!! Puede volver al home");
        return ViewRouteHelper.TICKET_SUCCESS;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/update-ticket/{ticketId}")
    public String updateTicket(@PathVariable Long ticketId, Model model){
        Ticket ticket = ticketService.findById(ticketId).orElseThrow();
        if(ticket.getLstIntervencion().isEmpty()){
            throw new NotFoundException("No existe ninguna intervencion previa! No se puede actualizar el ticket");
        }
        model.addAttribute("ticketEstado",estadoTicketRepository.findAll());
        model.addAttribute("estadoPrioridad",prioridadTicket.findAll());
        model.addAttribute("ticket",ticket);
        model.addAttribute("title","Modificar prioridad");
        return "ticket/formTicketUpdate";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/update-ticket")
    public String postUpdateTicket(@RequestParam("ticketId") Long idTicket,
                                   Model model,
                                   @RequestParam("prioridad.id") Long prioridadId){

        Optional<Ticket> ticket = ticketService.findById(idTicket);
        if(ticket.isPresent()){
            ticket.get().setPrioridad(prioridadTicket.findById(prioridadId).orElseThrow());
            log.info("Ticket: " + ticket.get());
            ticketService.save(ticket.get());
            model.addAttribute("title","Ticket update");
            model.addAttribute("tituloh1","La prioridad / estado ha sido actualizado con exito!");
            return ViewRouteHelper.TICKET_SUCCESS;
        }
        throw new NotFoundException("Ticket no encontrado en la base de datos");
    }

    private boolean isAuthenticated(Authentication authentication){
        return authentication != null && authentication.isAuthenticated();
    }
}
