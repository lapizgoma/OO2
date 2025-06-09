package oo2.grupo19.SistemaTickets.controllers;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import oo2.grupo19.SistemaTickets.exceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
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
import org.springframework.web.bind.annotation.RequestBody;


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

    
    @PreAuthorize("hasAnyRole('USER', 'EMPLOYEE', 'ADMIN')")
    @GetMapping("/{idTicket}")
    public String verTicket(@PathVariable long idTicket, Authentication authentication, Model model) {

        if (authentication.getAuthorities ().stream ().anyMatch (a -> a.getAuthority ().equals ("ROLE_EMPLOYEE"))) 
        {
            TicketEmployeeDTO ticket = ticketService.getTicketparaEmpleado(idTicket, authentication.getName ());
            model.addAttribute("ticketEmployeeDTO", ticket);
        }
        else if (authentication.getAuthorities ().stream ().anyMatch (a -> a.getAuthority ().equals ("ROLE_USER"))) 
        {
            TicketClientDTO ticket = ticketService.getTicketParaCliente(idTicket, authentication.getName ());
            model.addAttribute("ticketClientDTO", ticket);
        }

        return ViewRouteHelper.VIEW_TICKET;
    }
    

    @PreAuthorize("hasRole ('EMPLOYEE')")
    // TODO: cambiar a POST cuando esté la vista de tickets
    @GetMapping("/asignar/{idTicket}")
    public String postMethodName(@PathVariable long idTicket, Authentication authentication, Model model) {
        
        TicketEmployeeDTO ticket = ticketService.asignarTicket(idTicket, authentication.getName ());
        model.addAttribute("ticketEmployeeDTO", ticket);
        
        return "redirect:/ticket/" + idTicket;
    }
    

    // @GetMapping("/pendientes")
    // public String getTicketsPendientes() {
    //     return ViewRouteHelper.INDEX;
    // }

    // @PostMapping("/pendientes")
    // public String asignarTicket() {
    //     //TODO: process POST request
        
    //     return "hola";
    // }
    

    private boolean isAuthenticated(Authentication authentication){
        return authentication != null && authentication.isAuthenticated();
    }
}
