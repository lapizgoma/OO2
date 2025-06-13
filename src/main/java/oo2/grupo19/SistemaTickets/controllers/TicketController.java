package oo2.grupo19.SistemaTickets.controllers;
import java.util.Optional;



import java.util.List;
import java.time.LocalDate;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.security.SecurityService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;
import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
import oo2.grupo19.SistemaTickets.services.IPrioridadService;
@Controller
@RequestMapping("/ticket")
@Log4j2
public class TicketController {

    private final ITicketService ticketService;
    private final IClienteService clienteService;
    private final IEstadoTicketService estadoTicketService;
    private final IEstadoIntervencionService estadoIntervencionService;
    private final SecurityService securityService;
    private final IPrioridadService prioridadService;

    public TicketController(ITicketService ticketService, IPrioridadService prioridadService, IEstadoIntervencionService estadoIntervencionService, IClienteService clienteService, SecurityService securityService, IEstadoTicketService estadoTicketService) {
        this.ticketService = ticketService;
        this.estadoIntervencionService = estadoIntervencionService;
        this.clienteService = clienteService;
        this.securityService = securityService;
        this.estadoTicketService = estadoTicketService;
        this.prioridadService = prioridadService;
    }

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/create")
    public String createTicket(Model model, Authentication authentication) {
        Ticket ticket = new Ticket();
        String email = authentication.getName();
        Optional<Cliente> cliente = clienteService.findByEmail(email);
        ticket.setCreadoPor(cliente.get());
        model.addAttribute("ticket", ticket);
        return "ticket/formTicket";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute Ticket ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        String email = authentication.getName();
        Optional<Cliente> clienteOpt = clienteService.findByEmail(email);
        Cliente clienteDb = clienteOpt.get();
        EstadoTicket estado = estadoTicketService.findById(1L).orElse(null);
        if(estado == null) {
            throw new NotFoundException("Estado no encontrado");
        }
        ticket.setEstado(estado);
        ticket.setCreadoPor(clienteDb);
        ticket.setDetalle(contenido);
        ticketService.save(ticket);
        logger.info("Ticket creado exitosamente por: {}", email);
        model.addAttribute("title","Ticket create");
        model.addAttribute("titulo-h1","El ticket ha sido creado con exito!! Puede volver al home");
        return ViewRouteHelper.TICKET_SUCCESS;
    }
    
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    @GetMapping("/{idTicket}")
    public String verTicket(@PathVariable long idTicket, Authentication authentication, Model model) {

        if (authentication.getAuthorities ().stream ().anyMatch (a -> a.getAuthority ().equals ("ROLE_EMPLOYEE"))) 
        {
            TicketEmployeeDTO ticket = ticketService.getTicketparaEmpleado(idTicket, authentication.getName ());
            model.addAttribute("ticketEmployeeDTO", ticket);
        }
        else if (authentication.getAuthorities ().stream ().anyMatch (a -> a.getAuthority ().equals ("ROLE_CUSTOMER"))) 
        {
            TicketDTO ticket = ticketService.getTicketParaCliente(idTicket, authentication.getName ());
            model.addAttribute("ticketClientDTO", ticket);
        }

        return ViewRouteHelper.VIEW_TICKET;
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    // TODO: cambiar a POST cuando esté la vista de tickets
    @GetMapping("/asignar/{idTicket}")
    public String postMethodName(@PathVariable long idTicket, Authentication authentication, Model model) {
        
        TicketEmployeeDTO ticket = ticketService.asignarTicket(idTicket, authentication.getName ());
        model.addAttribute("ticketEmployeeDTO", ticket);
        
        return "redirect:/ticket/" + idTicket;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/update-ticket")
    public String showUpdateStatusForm(@RequestParam Long ticketId, Authentication auth, Model model){
        Long empleadoId = securityService.getIdEmpleado(auth);
        Ticket ticket = ticketService.findByIdAndEmpleado(empleadoId, ticketId);
        if (ticket == null) {
            logger.warn("Intento de actualizar estado de ticket no encontrado o sin permiso: {}", ticketId);
            throw new NotFoundException("No se ha encontrado el ticket o no tiene permiso");
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("estadosIntervencion", estadoIntervencionService.findAll());
        model.addAttribute("estadoPrioridad",prioridadService.findAll());
        model.addAttribute("title","Modificar prioridad");
        return ViewRouteHelper.TICKET_UPDATE_STATUS; 
    }
    // TODO: Revisar Optional DTO 
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-priority")
    public String postUpdateTicket(@RequestParam("ticketId") Long idTicket,
                                   Model model,
                                   @RequestParam("prioridad.id") Long prioridadId){
        Ticket ticket = ticketService.findById(idTicket).orElse(null);
        if(ticket == null) {
            throw new NotFoundException("Ticket no encontrado");
        }
        Prioridad prioridad = prioridadService.findById(prioridadId).orElse(null);
        if(prioridad == null) {
            throw new NotFoundException("Prioridad no encontrada");
        }
        ticket.setPrioridad(prioridad);
        logger.info("Ticket actualizado: {} con nueva prioridad: {}", idTicket, prioridadId);
        ticketService.save(ticket);
        model.addAttribute("title","Ticket update");
        model.addAttribute("tituloh1","La prioridad / estado ha sido actualizado con exito!");
        return ViewRouteHelper.TICKET_SUCCESS;
    }
    // TODO: HACE FALTA HACER DTO PARA ESTADOINTERVENCION(INFORMACION NO SENSIBLE)
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-status")
    public String processUpdateStatus(@ModelAttribute Ticket ticket, Authentication auth,Model model) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        ticketService.actualizarEstadoTicket(empleadoId, ticket.getId(), ticket.getEstado());
        Ticket actualizado = ticketService.findByIdAndEmpleado(empleadoId, ticket.getId());
        List<EstadoIntervencion> estadosIntervencion = estadoIntervencionService.findAll();
        model.addAttribute("ticket", actualizado);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        model.addAttribute("mensaje", "Estado actualizado correctamente");
        return ViewRouteHelper.TICKET_SUCCESS; 
    }

  @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-cliente")
    public String ticketListByCliente(@RequestParam String email, Model model, Authentication authentication){
        List<Ticket> tickets = ticketService.findTicketByCliente(email);
        model.addAttribute("tickets", tickets);
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-asunto")
    public String ticketListByAsunto(@RequestParam String asunto, Model model, Authentication authentication){
        List<Ticket> tickets = ticketService.findTicketByAsunto(asunto);
        model.addAttribute("tickets", tickets);
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-empleado")
    public String ticketListByEmpleado(@RequestParam String email, Model model, Authentication authentication){
        List<Ticket> tickets = ticketService.findTicketByEmpleado(email);
        model.addAttribute("tickets", tickets);
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-estado")
    public String ticketListByEstado(@RequestParam("estadoTicketId") Long estadoId, Model model, Authentication authentication){
        Optional<EstadoTicket> optionalEstado = estadoTicketService.findById(estadoId);
        if (optionalEstado.isPresent()) {
            EstadoTicket estado = optionalEstado.get();
            List<Ticket> tickets = ticketService.findTicketByEstado(estado);
            model.addAttribute("tickets", tickets);
        } else {
            model.addAttribute("error", "Estado no encontrado");
        }
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-prioridad")
    public String ticketListByPrioridad(@RequestParam("prioridadId") Long prioridadId, Model model, Authentication authentication){
        Optional<Prioridad> optionalPrioridad = prioridadService.findById(prioridadId);
        if (optionalPrioridad.isPresent()) {
            Prioridad prioridad = optionalPrioridad.get();
            List<Ticket> tickets = ticketService.findTicketByPrioridad(prioridad);
            model.addAttribute("tickets", tickets);
        } else {
            model.addAttribute("error", "Prioridad no encontrada");
        }
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-fecha")
    public String ticketListByFecha(@RequestParam LocalDate fecha, Model model, Authentication authentication){
        List<Ticket> tickets = ticketService.findTicketByFechaHora(fecha);
        model.addAttribute("tickets", tickets);
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @GetMapping("/form-filtrar-tickets")
        public String showFilterPage() {
    return "ticket/formTicketsFiltrar";  // La vista con los formularios de filtro 
    }
}
