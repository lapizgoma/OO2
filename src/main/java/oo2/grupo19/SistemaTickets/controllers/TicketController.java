package oo2.grupo19.SistemaTickets.controllers;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;

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
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.TicketNotFound;
import oo2.grupo19.SistemaTickets.exceptions.UserNotFounException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.security.SecurityService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
import oo2.grupo19.SistemaTickets.services.IPrioridadService;
@Controller
@RequestMapping("/ticket")
@Log4j2
public class TicketController {

    private final ITicketService ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final IEmpleado empleadoRepository;
    private final ICliente clienteRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final IEstadoIntervencionService estadoIntervencionService;
    private final SecurityService securityService;
    private final IEstadoTicketService estadoTicketService;
    private final IPrioridadService prioridadService;
    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(ITicketService ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IPrioridadService prioridadService, IEstadoTicket estadoTicketRepository, IEstadoIntervencionService estadoIntervencionService, ICliente clienteRepository,IEmpleado empleadoRepository, SecurityService securityService, IEstadoTicketService estadoTicketService) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
        this.estadoIntervencionService = estadoIntervencionService;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
        this.securityService = securityService;
        this.estadoTicketService = estadoTicketService;
        this.prioridadService = prioridadService;
    }

    // ... (otros campos y constructor permanecen igual)

@GetMapping("/create")
public String createTicket(Model model, Authentication authentication) {
    Ticket ticket = new Ticket();
    
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

    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute Ticket ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        
        if (isAuthenticated(authentication)) {
            throw new NotAuthorizedException("Debes iniciar sesión");
        }

        // Obtener el usuario autenticado
        String email = authentication.getName();
        Usuario clienteDb = usuarioService.findByEmail(email)
            .orElseThrow(() -> new UserNotFounException("Usuario no encontrado"));
        
        if (!(clienteDb instanceof Cliente)) {
            throw new NotAuthorizedException("Solo clientes pueden crear tickets");
        }

        // Detalle y realizadoPor No lo utilzamos por el momento!
        // Intervencion mensaje = new Intervencion();
        // mensaje.setDescripcion(contenido);
        // mensaje.setEstado(estadoIntervencion.findById(1L).get())
        
        
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.setCreadoPor(clienteDb);
        // Ser cuidadoso con esto. No esta probado, asi que puede fallar.
        ticket.setListEmpleado(new HashSet<>(empleadoRepository.findAll()));
        ticketService.save(ticket);
        
        return ViewRouteHelper.TICKET_SUCCESS;
    }

    

    // Esto actualizaria el Ticket los datos de prioridad y estado. NO TERMINADO

    private boolean isAuthenticated(Authentication authentication){
        return authentication == null || !authentication.isAuthenticated();
    }

    @GetMapping("/update-ticket")
    public String showUpdateStatusForm(@RequestParam Long ticketId, Authentication auth, Model model){
        Long empleadoId = securityService.getIdEmpleado(auth);
        Ticket ticket = ticketService.findByIdAndEmpleado(empleadoId, ticketId);
        List<EstadoIntervencion> estadosIntervencion = estadoIntervencionService.findAll();
        if (ticket == null) {
            throw new TicketNotFound("No se ha encontrado el ticket o no tiene permiso");
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        return "ticket/formTicketUpdateStatus";
    }

    @PostMapping("/update-ticket")
    public String processUpdateStatus(@ModelAttribute Ticket ticket, Authentication auth,Model model) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        ticketService.actualizarEstadoTicket(empleadoId, ticket.getId(), ticket.getEstado());
        Ticket actualizado = ticketService.findByIdAndEmpleado(empleadoId, ticket.getId());
        List<EstadoIntervencion> estadosIntervencion = estadoIntervencionService.findAll();
        model.addAttribute("ticket", actualizado);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        model.addAttribute("mensaje", "Estado actualizado correctamente");
        return "ticket/formTicketUpdateStatus"; 
    }

    @GetMapping("/list-ticket-por-cliente")
    public String ticketListByCliente(@RequestParam String email, Model model){
        List<Ticket> tickets = ticketService.findTicketByCliente(email);
        model.addAttribute("tickets", tickets);
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-por-asunto")
    public String ticketListByAsunto(@RequestParam String asunto, Model model){
        List<Ticket> tickets = ticketService.findTicketByAsunto(asunto);
        model.addAttribute("tickets", tickets);
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-por-empleado")
    public String ticketListByEmpleado(@RequestParam String email, Model model){
        List<Ticket> tickets = ticketService.findTicketByEmpleado(email);
        model.addAttribute("tickets", tickets);
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-por-estado")
    public String ticketListByEstado(@RequestParam("estadoTicketId") Long estadoId, Model model){
        Optional<EstadoTicket> optionalEstado = estadoTicketService.findById(estadoId);
        if (optionalEstado.isPresent()) {
            EstadoTicket estado = optionalEstado.get();
            List<Ticket> tickets = ticketService.findTicketByEstado(estado);
            model.addAttribute("tickets", tickets);
        } else {
            model.addAttribute("error", "Estado no encontrado");
        }
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-por-prioridad")
    public String ticketListByPrioridad(@RequestParam("prioridadId") Long prioridadId, Model model){
        Optional<Prioridad> optionalPrioridad = prioridadService.findById(prioridadId);
        if (optionalPrioridad.isPresent()) {
            Prioridad prioridad = optionalPrioridad.get();
            List<Ticket> tickets = ticketService.findTicketByPrioridad(prioridad);
            model.addAttribute("tickets", tickets);
        } else {
            model.addAttribute("error", "Prioridad no encontrada");
        }
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-por-fecha")
    public String ticketListByFecha(@RequestParam LocalDate fecha, Model model){
        List<Ticket> tickets = ticketService.findTicketByFechaHora(fecha);
        model.addAttribute("tickets", tickets);
        return "ticket/listTickets";
    }

    @GetMapping("/list-ticket-cerrado-por-cliente")
    public String ticketListByClienteCerrado(@RequestParam String email, Model model){
        List<Ticket> tickets = ticketService.traerPorClienteCerrado(email);
        model.addAttribute("tickets", tickets);
        return "ticket/listTicketsAntiguos";
    }

    @GetMapping("/form-filtrar-tickets")
        public String showFilterPage() {
    return "ticket/formTicketsFiltrar";  // La vista con los formularios de filtro 
    }
}
    