package oo2.grupo19.SistemaTickets.controllers;

import java.util.Optional;

import oo2.grupo19.SistemaTickets.exceptions.NotFoundException;
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
import java.util.Set;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.UserNotFounException;
import oo2.grupo19.SistemaTickets.exceptions.TicketNotFound;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.impl.IntervencionServiceImpl;
import oo2.grupo19.SistemaTickets.security.SecurityService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
/*
 * Este controlador no esta terminado.
 */
@Controller
@Log4j2
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IIntervencionService intervencionRepository;
    private final ITicket ticketRepository;
    private final IEmpleado empleadoRepository;
    private final IPrioridad prioridadRepository;
    private final SecurityService securityService;
    private final ITicketService ticketService;
    private final IIntervencionService intervencionService;
    private final IEstadoIntervencionService estadoIntervencionService;

    
    public IntervencionController(IIntervencionService intervencionRepository, ITicket ticketRepository,
            IEmpleado empleadoRepository, IPrioridad prioridadRepository, SecurityService securityService,
            ITicketService ticketService, IIntervencionService intervencionService,
            IEstadoIntervencionService estadoIntervencionService) {
        this.intervencionRepository = intervencionRepository;
        this.ticketRepository = ticketRepository;
        this.empleadoRepository = empleadoRepository;
        this.prioridadRepository = prioridadRepository;
        this.securityService = securityService;
        this.ticketService = ticketService;
        this.intervencionService = intervencionService;
        this.estadoIntervencionService = estadoIntervencionService;
    }

    /*
     * ATENCION!
     * Esto deberia obtener el ticket en base al TicketController. El empleado va a poder ver los tickets y seleccionar los tickets
     * para modificar la intervencion
     */
    @GetMapping("/form-processing-ticket")
    public String getProcesarTicket(Model model, @RequestParam Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        if(ticket != null){
            Intervencion intervencion = new Intervencion();
            model.addAttribute("intervencion", intervencion);
            model.addAttribute("estadosIntervencion",  estadoIntervencionService.findAll());
            model.addAttribute("ticket", ticket);
            
        }
            return ViewRouteHelper.FORM_INTERVENCION;
    }

    @PostMapping("/processing-ticket")
    public String procesarTicket(@ModelAttribute Intervencion intervencion,
                                Authentication authentication,
                                Model model,
                                @RequestParam Long ticketId){

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotAuthorizedException("Debes iniciar sesion, no estas autorizado");
        }

        Empleado empleado = empleadoRepository.findByContactoEmail(authentication.getName()).orElseThrow();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        if(ticket != null){
            intervencion.setRealizadoPor(empleado);
            ticket.agregarMensaje(intervencion);
        }

        return "ticket/update-ticket?ticketId=" + ticketId;
        
    }

    @PostMapping("/processing-ticket/update-intervencion")
    public String processUpdateStatusIntervencion(@ModelAttribute Ticket ticket, @RequestParam Long intervencionId, Authentication auth, Model model,  @RequestParam Long estado) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        intervencionService.actualizarEstadoIntervencion(empleadoId, ticket.getId(), intervencionId, estadoIntervencionService.findById(estado).orElseThrow());
        Ticket ticketDB = ticketService.findByIdAndEmpleado(empleadoId, ticket.getId());
        List<EstadoIntervencion> estadosIntervencion = estadoIntervencionService.findAll();
        if (ticketDB == null) {
            throw new TicketNotFound("No se ha encontrado el ticket");
        }
        model.addAttribute("ticket", ticketDB);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        model.addAttribute("mensaje", "Intervención actualizada correctamente");

        return "ticket/formTicketUpdateStatus"; 
}

   /*  
     * ATENCION!
     * Esto deberia obtener el ticket en base al TicketController. El empleado va a poder ver los tickets y seleccionar los tickets para modificar la intervencion
     */
    // ARREGLAR LAS EXCEPCIONES!
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/create/{idTicket}")
    public String crearIntervencion(@PathVariable Long idTicket, Model model, Authentication auth){
        Optional<Ticket> tOptional = ticketRepository.findById(idTicket);
        if(isAuthenticated(auth)){
            if(tOptional.isPresent()){
                model.addAttribute("ticketId", idTicket);
                model.addAttribute("empleadoId", empleadoRepository.findByContactoEmail(auth.getName()).orElseThrow().getId());
                model.addAttribute("intervencionEstados", estadoIntervencionService.findAll());
                model.addAttribute("intervencion", new Intervencion());
                return ViewRouteHelper.FORM_CREATE_INTERVENCION;
            }else{
                throw new NotFoundException("No existe ningun ticket");
            }
        }
        throw new UserNotFounException("No hay un empleado autorizado");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/create")
    public String postCrearIntervencion(@Valid @ModelAttribute Intervencion intervencion, 
                                        @RequestParam Long ticketId,
                                        @RequestParam Long empleadoId,
                                        @RequestParam("estado.id") Long estadoId, 
                                        Authentication auth){
        if(isAuthenticated(auth)){
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
            Empleado empleado = empleadoRepository.findById(empleadoId).orElseThrow();
            EstadoIntervencion estado = estadoIntervencionService.findById(estadoId).orElseThrow();
            ticket.agregarEmpleado(empleado);
            ticket.agregarMensaje(intervencion);
            intervencion.setRealizadoPor(empleado);
            intervencion.setEstado(estado);
            log.info("Ticket: " + intervencion.getTicket());
            intervencionRepository.save(intervencion);
            ticketRepository.save(ticket);
            return ViewRouteHelper.INTERVENCION_SUCCESS;
        }else{
            throw new NotAuthorizedException("No estas autorizado para realizar una intervencion");
        }
    }

    private boolean isAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated();
    }

}



