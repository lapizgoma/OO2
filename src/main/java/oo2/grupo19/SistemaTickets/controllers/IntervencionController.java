package oo2.grupo19.SistemaTickets.controllers;

import java.util.Optional;

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
import java.util.List;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.security.SecurityService;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IIntervencionService intervencionService;
    private final ITicketService ticketServiceImpl;
    private final IEmpleadoService empleadoService;
    private final SecurityService securityService;
    private final IEstadoIntervencionService estadoIntervencionService;
    private final IEstadoTicketService estadoTicketService;
    private static final Logger logger = LoggerFactory.getLogger(IntervencionController.class);

    public IntervencionController(IIntervencionService intervencionService, ITicketService ticketServiceImpl,
                                  IEmpleadoService empleadoService, SecurityService securityService,
                                  IEstadoIntervencionService estadoIntervencionService, IEstadoTicketService estadoTicketService) {
        this.intervencionService = intervencionService;
        this.ticketServiceImpl = ticketServiceImpl;
        this.empleadoService = empleadoService;
        this.securityService = securityService;
        this.estadoIntervencionService = estadoIntervencionService;
        this.estadoTicketService = estadoTicketService;
    }

    /*
     * ATENCION!
     * Esto deberia obtener el ticket en base al TicketController. El empleado va a poder ver los tickets y seleccionar los tickets
     * para modificar la intervencion
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/form-processing-ticket")
    public String getProcesarTicket(Model model, @RequestParam Long ticketId){
        Ticket ticket = ticketServiceImpl.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
        Intervencion intervencion = new Intervencion();
        model.addAttribute("intervencion", intervencion);
        model.addAttribute("estadosIntervencion",  estadoIntervencionService.findAll());
        model.addAttribute("ticket", ticket);
        logger.info("Acceso a procesamiento de ticket: {}", ticketId);
        return ViewRouteHelper.FORM_CREATE_INTERVENCION;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket")
    public String procesarTicket(@ModelAttribute Intervencion intervencion,
                                Authentication authentication,
                                Model model,
                                @RequestParam Long ticketId){
        Empleado empleado = empleadoService.traerEmpleado(authentication.getName()).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
        Ticket ticket = ticketServiceImpl.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
        intervencion.setRealizadoPor(empleado);
        ticket.agregarIntervencion(intervencion);
        logger.info("Intervención procesada para ticket: {} por empleado: {}", ticketId, empleado.getId());
        return "ticket/update-ticket?ticketId=" + ticketId;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket/update-intervencion")
    public String processUpdateStatusIntervencion(@ModelAttribute Ticket ticket, @RequestParam Long intervencionId, Authentication auth, Model model,  @RequestParam Long estado) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        intervencionService.actualizarEstadoIntervencion(empleadoId, ticket.getId(), intervencionId, estadoIntervencionService.findById(estado).orElseThrow());
        Ticket ticketDB = ticketServiceImpl.findByIdAndEmpleado(empleadoId, ticket.getId());
        List<EstadoIntervencion> estadosIntervencion = estadoIntervencionService.findAll();
        if (ticketDB == null) {
            throw new NotFoundException("No se ha encontrado el ticket");
        }
        model.addAttribute("ticket", ticketDB);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        model.addAttribute("mensaje", "Intervención actualizada correctamente");
        return "ticket/formTicketUpdateStatus"; 
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/create/{idTicket}")
    public String crearIntervencion(@PathVariable Long idTicket, Model model, Authentication auth){
        Optional<Ticket> tOptional = ticketServiceImpl.findById(idTicket);
            if(tOptional.isPresent()){
                model.addAttribute("ticketId", idTicket);
                model.addAttribute("empleadoId", empleadoService.traerEmpleado(auth.getName()).orElseThrow(() -> new NotFoundException("Empleado no encontrado")).getId());
                model.addAttribute("intervencionEstados", estadoIntervencionService.findAll());
                model.addAttribute("intervencion", new Intervencion());
                logger.info("Creación de intervención para ticket: {} por empleado: {}", idTicket, auth.getName());
                return ViewRouteHelper.FORM_CREATE_INTERVENCION;
            }else{
                logger.warn("Intento de crear intervención para ticket inexistente: {}", idTicket);
                throw new NotFoundException("No existe ningun ticket");
            }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/create")
    public String postCrearIntervencion(@Valid @ModelAttribute Intervencion intervencion, 
                                        @RequestParam Long ticketId,
                                        @RequestParam Long empleadoId,
                                        @RequestParam("estado.id") Long estadoId, 
                                        Authentication auth,
                                        RedirectAttributes redirectAttributes){
            Ticket ticket = ticketServiceImpl.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
            Empleado empleado = empleadoService.findById(empleadoId).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
            EstadoIntervencion estadoIntervencion = estadoIntervencionService.findById(estadoId).orElseThrow(() -> new NotFoundException("Estado de intervención no encontrado"));
            EstadoTicket estadoTicket = estadoTicketService.findById(2L).orElseThrow();
            ticket.agregarEmpleado(empleado);
            if (ticket.getEstado().getEstado()=="PENDIENTE") {
                ticket.setEstado(estadoTicket);
            }
            intervencion.setRealizadoPor(empleado);
            intervencion.setEstado(estadoIntervencion);
            logger.info("Intervención creada para ticket: {} por empleado: {}", ticketId, empleadoId);
            ticket.agregarIntervencion(intervencion);
            intervencionService.save(intervencion);
            redirectAttributes.addFlashAttribute("mensajeExito", "Intervención creada con éxito!");
            return "redirect:/empleado/home";
    }

}



