package oo2.grupo19.SistemaTickets.controllers;
import java.util.Set;
import java.time.LocalDate;

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
import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
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
    public String createTicket(Model model) {
        TicketDTO ticket = new TicketDTO();
        model.addAttribute("ticket", ticket);
        return ViewRouteHelper.FORM_TICKET;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute TicketDTO ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        String email = authentication.getName();
        ClienteDTO cliente = clienteService.findByEmail(email);
        EstadoTicketDTO estado = estadoTicketService.findById(1L);
        ticket.setCliente(cliente);
        ticket.setEstado(estado);
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
            TicketClientDTO ticket = ticketService.getTicketParaCliente(idTicket, authentication.getName ());
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
        TicketDTO ticket = ticketService.findByIdAndEmpleado(empleadoId, ticketId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("estadosIntervencion", estadoIntervencionService.findAll());
        model.addAttribute("estadoPrioridad",prioridadService.findAll());
        model.addAttribute("title","Modificar prioridad");
        return ViewRouteHelper.TICKET_UPDATE_STATUS; 
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-priority")
    public String postUpdateTicket(@RequestParam("ticketId") Long idTicket,
                                   Model model,
                                   Authentication auth,
                                   @RequestParam("prioridad.prioridad") String prioridad){
        Long empleadoId = securityService.getIdEmpleado(auth);
        PrioridadDTO prioridadticket = prioridadService.findByPrioridad(prioridad);
        ticketService.actualizarPrioridadTicket(empleadoId, idTicket, prioridadticket);
        logger.info("Ticket actualizado: {} con nueva prioridad: {}", idTicket, prioridad);
        model.addAttribute("title","Ticket update");
        model.addAttribute("tituloh1","La prioridad ha sido actualizado con exito!");
        return ViewRouteHelper.TICKET_SUCCESS;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-status")
    public String processUpdateStatus(@RequestParam("ticketId") Long idTicket, 
                                        Authentication auth,Model model,
                                        @RequestParam("estado.estado") String estado) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        EstadoTicketDTO estadoTicket = estadoTicketService.findByEstado(estado);
        ticketService.actualizarEstadoTicket(empleadoId, idTicket, estadoTicket);
        model.addAttribute("mensaje", "Estado actualizado correctamente");
        return ViewRouteHelper.TICKET_SUCCESS; 
    }

  @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-cliente")
    public String ticketListByCliente(@RequestParam String email, Model model, Authentication authentication){
        Set<TicketDTO> tickets = ticketService.findTicketByCliente(email);
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
        Set<TicketDTO> tickets = ticketService.findTicketByAsunto(asunto);
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
        Set<TicketDTO> tickets = ticketService.findTicketByEmpleado(email);
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
    public String ticketListByEstado(@RequestParam("estado.estado") String estado, Model model, Authentication authentication){
        EstadoTicketDTO estadoticket = estadoTicketService.findByEstado(estado);
        Set<TicketDTO> tickets = ticketService.findTicketByEstado(estadoticket);
        model.addAttribute("tickets", tickets);
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ViewRouteHelper.INDEX_ADMIN;
        } else if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return ViewRouteHelper.INDEX_EMPLOYEE;
        }
        return "ticket/listTickets";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-prioridad")
    public String ticketListByPrioridad(@RequestParam("priodridad.prioridad") String prioridad, Model model, Authentication authentication){
        PrioridadDTO prioridadTicket = prioridadService.findByPrioridad(prioridad);
        Set<TicketDTO> tickets = ticketService.findTicketByPrioridad(prioridadTicket);
        model.addAttribute("tickets", tickets);
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
        Set<TicketDTO> tickets = ticketService.findTicketByFechaHora(fecha);
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
