package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;

/*
 * Este controlador no esta terminado.
 */
@Controller
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IIntervencion intervencionRepository;
    private final ITicket ticketRepository;
    private final IEmpleado empleadoRepository;
    private final IEstadoIntervencion estadoIntervencion;
    private final IPrioridad prioridadRepository;

    public IntervencionController(IIntervencion intervencionRepository, IEmpleado empleadoRepository, IEstadoIntervencion estadoIntervencion, ITicket ticketRepository, IPrioridad prioridadRepository) {
        this.intervencionRepository = intervencionRepository;
        this.empleadoRepository = empleadoRepository;
        this.estadoIntervencion = estadoIntervencion;
        this.ticketRepository = ticketRepository;
        this.prioridadRepository = prioridadRepository;
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
            model.addAttribute("estadosIntervencion", estadoIntervencion.findAll());
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

}
