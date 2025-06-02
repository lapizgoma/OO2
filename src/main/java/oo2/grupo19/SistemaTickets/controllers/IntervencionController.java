package oo2.grupo19.SistemaTickets.controllers;

import java.util.Optional;

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

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.UserNotFounException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.impl.IntervencionServiceImpl;

/*
 * Este controlador no esta terminado.
 */
@Controller
@Log4j2
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IntervencionServiceImpl intervencionRepository;
    private final ITicket ticketRepository;
    private final IEmpleado empleadoRepository;
    private final IEstadoIntervencion estadoIntervencion;
    private final IPrioridad prioridadRepository;

    public IntervencionController(IntervencionServiceImpl intervencionRepository, IEmpleado empleadoRepository, IEstadoIntervencion estadoIntervencion, ITicket ticketRepository, IPrioridad prioridadRepository) {
        this.intervencionRepository = intervencionRepository;
        this.empleadoRepository = empleadoRepository;
        this.estadoIntervencion = estadoIntervencion;
        this.ticketRepository = ticketRepository;
        this.prioridadRepository = prioridadRepository;
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
                model.addAttribute("intervencionEstados", estadoIntervencion.findAll());
                model.addAttribute("intervencion", new Intervencion());
                return ViewRouteHelper.FORM_CREATE_INTERVENCION;
            }else{
                throw new UserNotFounException("No existe ningun ticket");
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
            EstadoIntervencion estado = estadoIntervencion.findById(estadoId).orElseThrow();
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
