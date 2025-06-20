package oo2.grupo19.SistemaTickets.controllers;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.security.SecurityService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;
import oo2.grupo19.SistemaTickets.services.EmailService;
import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
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
    private final EmailService emailService;

    public TicketController(ITicketService ticketService, IPrioridadService prioridadService, IEstadoIntervencionService    estadoIntervencionService,IClienteService clienteService, SecurityService securityService, IEstadoTicketService estadoTicketService, EmailService emailService, IEmpleadoService empleadoService) {
        this.ticketService = ticketService;
        this.estadoIntervencionService = estadoIntervencionService;
        this.clienteService = clienteService;
        this.securityService = securityService;
        this.estadoTicketService = estadoTicketService;
        this.prioridadService = prioridadService;
        this.emailService = emailService;
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
        EstadoTicketDTO estado = estadoTicketService.findById(1L);
        ticket.setClienteEmail(clienteService.findByEmail(email).getContacto().getEmail());
        ticket.setEstado(estado);
        ticket.setDetalle(contenido);
        ticketService.save(ticket);
        logger.info("Ticket creado exitosamente por: {}", email);
        sendEmailTicketCreate(ticketService.findUltimoPorEmailYAsunto(ticket.getClienteEmail(), ticket.getAsunto()));
        model.addAttribute("title","Ticket create");
        model.addAttribute("titulo-h1","El ticket ha sido creado con exito!! Puede volver al home");
        return ViewRouteHelper.TICKET_SUCCESS;
    }
    
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE','ADMIN')")
    @GetMapping("/{idTicket}")
    public String verTicket(@PathVariable long idTicket, Authentication authentication, Model model, @ModelAttribute(name = "mensaje", binding = false) String mensaje) {
        model.addAttribute("tienePendientes", !ticketService.todasLasIntervencionesFinalizadas(idTicket));
        // No se muestra el nombre del empleado que creó la intervencion. Buscar una solucion o hacer que no muestre nada.

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE") || a.getAuthority().equals("ROLE_ADMIN"))) 
        {
            TicketEmployeeDTO ticket = ticketService.getTicketparaEmpleado(idTicket, authentication.getName());
            model.addAttribute("ticketEmployeeDTO", ticket);
        }else if (authentication.getAuthorities ().stream ().anyMatch (a -> a.getAuthority ().equals ("ROLE_CUSTOMER"))) 
        {
            TicketDTO ticket = ticketService.getTicketParaCliente(idTicket, authentication.getName ());
            model.addAttribute("ticketClientDTO", ticket);
        }
            if (mensaje != null && !mensaje.isEmpty()) {
                model.addAttribute("mensaje", mensaje);
            }   
        model.addAttribute("estadosTicket", estadoTicketService.findAll());
        model.addAttribute("prioridadTicket", prioridadService.findAll());
        model.addAttribute("estadosIntervencion", estadoIntervencionService.findAll());
        return ViewRouteHelper.VIEW_TICKET;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/asignar/{idTicket}")
    public String postMethodName(@PathVariable long idTicket, Authentication authentication, Model model) {
        
        TicketEmployeeDTO ticket = ticketService.asignarTicket(idTicket, authentication.getName ());
        model.addAttribute("ticketEmployeeDTO", ticket);
        
        return ViewRouteHelper.TICKET_VIEW_ID(idTicket);
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-priority")
    public String postUpdateTicket(@RequestParam("ticketId") Long idTicket,
                                    @RequestParam("estado.estado") String estado,
                                   RedirectAttributes redirectAttributes,
                                   Authentication auth,
                                   @RequestParam("prioridad.prioridad") String prioridad){
        Long empleadoId = securityService.getIdEmpleado(auth);
        if ("CERRADO".equalsIgnoreCase(estado)){
            redirectAttributes.addFlashAttribute("mensajeAlerta", "No se puede cambiar la prioridad de un ticket cerrado.");
            logger.warn("Intento de cambio de prioridad a cerrado para ticket: {} por empleado: {}", idTicket, auth.getName());
        }else{
            PrioridadDTO prioridadticket = prioridadService.findByPrioridad(prioridad);
            ticketService.actualizarPrioridadTicket(empleadoId, idTicket, prioridadticket);
            logger.info("Ticket actualizado: {} con nueva prioridad: {}", idTicket, prioridad);
            redirectAttributes.addFlashAttribute("mensajeAlerta", "Prioridad actualizado con éxito!");
        }
        return ViewRouteHelper.TICKET_VIEW_ID(idTicket);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/update-ticket-status")
    public String processUpdateStatus(@RequestParam("ticketId") Long idTicket, 
                                        Authentication auth,RedirectAttributes redirectAttributes,
                                        @RequestParam("estado.estado") String estado,
                                        @RequestParam(name = "forzarCierre", required = false) Boolean cierre) {

        Long empleadoId = securityService.getIdEmpleado(auth);
        boolean cierreForzado = (cierre != null && cierre);                                   
        boolean todasFinalizadas = ticketService.todasLasIntervencionesFinalizadas(idTicket);
        String estadoTicketActual = ticketService.findById(idTicket).getEstado().getEstado();
        if(estado.equalsIgnoreCase(estadoTicketActual)){
            return ViewRouteHelper.TICKET_VIEW_ID(idTicket); // No se actualiza el estado si es el mismo
        }
        if ("CERRADO".equalsIgnoreCase(estado) && (!todasFinalizadas && !cierreForzado)) {
                // No forzar cierre y hay intervenciones pendientes: se espera la confirmación en frontend (modal)
                log.info("Intento de cierre de ticket {} por empleado {}, pero hay intervenciones pendientes.", idTicket, auth.getName());
                redirectAttributes.addFlashAttribute("mensajeAlerta", "Todavía hay intervenciones sin terminar, ¿desea cerrar el ticket de todas maneras?");
        }else{
            EstadoTicketDTO estadoTicket = estadoTicketService.findByEstado(estado);
            ticketService.actualizarEstadoTicket(empleadoId, idTicket, estadoTicket);
        }
        return ViewRouteHelper.TICKET_VIEW_ID(idTicket); 
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-cliente")
    public String ticketListByCliente(@RequestParam String email, RedirectAttributes redirectAttributes, Authentication authentication){
        // Revisar el service este pq nose si la query del repository es la mejor
        Set<TicketEmployeeDTO> tickets = ticketService.findTicketByCliente(email);
        redirectAttributes.addFlashAttribute("ticket", tickets);
        return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-asunto")
    public String ticketListByAsunto(@RequestParam String asunto, RedirectAttributes redirectAttributes, Authentication authentication){
        Set<TicketEmployeeDTO> tickets = ticketService.findTicketByAsunto(asunto);
        redirectAttributes.addFlashAttribute("ticket", tickets);
        return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-empleado")
    public String ticketListByEmpleado(@RequestParam String email, RedirectAttributes redirectAttributes, Authentication authentication){
        Set<TicketEmployeeDTO> tickets = ticketService.findTicketByEmpleado(email);
        log.info("Email del empleado: " + email);
        log.info("TicketS: " + tickets.isEmpty());
        redirectAttributes.addFlashAttribute("ticket", tickets);
        return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-estado")
    public String ticketListByEstado(@RequestParam String estado, RedirectAttributes redirectAttributes, Authentication authentication){
        EstadoTicketDTO estadoticket = estadoTicketService.findByEstado(estado);
        Set<TicketEmployeeDTO> tickets = ticketService.findTicketByEstado(estadoticket);
        redirectAttributes.addFlashAttribute("ticket", tickets);
        return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-prioridad")
    public String ticketListByPrioridad(@RequestParam String prioridad, RedirectAttributes redirectAttributes, Authentication authentication){
        try{
            PrioridadDTO prioridadTicket = prioridadService.findByPrioridad(prioridad);
            Set<TicketEmployeeDTO> tickets = ticketService.findTicketByPrioridad(prioridadTicket);
            redirectAttributes.addFlashAttribute("ticket", tickets);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("codigoError", e.getMessage());
            return "redirect:/form-filtrar-tickets";  
        }return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/list-ticket-por-fecha")
    public String ticketListByFecha(@RequestParam LocalDate fecha, RedirectAttributes redirectAttributes, Authentication authentication){
        Set<TicketEmployeeDTO> tickets = ticketService.findTicketByFechaHora(fecha);
        redirectAttributes.addFlashAttribute("ticket", tickets);
        return ViewRouteHelper.INDEX_REDIRECT;
    }
    
    @GetMapping("/form-filtrar-tickets")
    public String showFilterPage() {
        return ViewRouteHelper.TICKET_FORM_FILTRAR;  // La vista con los formularios de filtro 
        }

    private void sendEmailTicketCreate(TicketDTO ticket){
        Map<String,Object> infoClient = new HashMap<>();
        infoClient.put("nombre",clienteService.findByEmail(ticket.getClienteEmail()).getNombre());
        infoClient.put("mensaje","Su ticket ha sido creado con exito, aguarde la respuesta de un empleado");
        infoClient.put("asunto", ticket.getAsunto());
        infoClient.put("fechaTicket", ticket.getFechaHoraCreado());
        infoClient.put("id",ticket.getId());
        emailService.enviarCorreoHtml(ticket.getClienteEmail(), "Su ticket fue registrado en el sistema", ViewRouteHelper.TICKET_SUCCESS_MAIL, infoClient);
    }

}
