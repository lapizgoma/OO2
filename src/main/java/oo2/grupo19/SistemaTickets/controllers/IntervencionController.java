package oo2.grupo19.SistemaTickets.controllers;

import java.util.Set;

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
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDeIntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.EstadoIntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.EmpleadoDeIntervencionMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.EstadoIntervencionMapper;
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
        TicketDTO ticket = ticketServiceImpl.findById(ticketId);
        IntervencionDTO intervencion = new IntervencionDTO();
        model.addAttribute("intervencion", intervencion);
        model.addAttribute("estadosIntervencion",  estadoIntervencionService.findAll());
        model.addAttribute("ticket", ticket);
        logger.info("Acceso a procesamiento de ticket: {}", ticketId);
        return ViewRouteHelper.FORM_CREATE_INTERVENCION;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket")
    public String procesarTicket(@ModelAttribute IntervencionDTO intervencion,
                                Authentication authentication,
                                Model model,
                                @RequestParam Long ticketId){
        EmpleadoDTO empleado = empleadoService.findByEmail(authentication.getName());
        TicketDTO ticket = ticketServiceImpl.findById(ticketId);
        EmpleadoDeIntervencionDTO empleadoDeIntervencion = EmpleadoDeIntervencionMapper.mapToEmpleadoIntervencionDto(empleadoService.findById(empleado.getId()));
        intervencion.setRealizadoPor(empleadoDeIntervencion);
        ticket.getIntervencion().add(intervencion);
        logger.info("Intervención procesada para ticket: {} por empleado: {}", ticketId, empleado.getId());
        return "ticket/update-ticket?ticketId=" + ticketId;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket/update-intervencion")
    public String processUpdateStatusIntervencion(@ModelAttribute TicketDTO ticket, @RequestParam Long intervencionId, Authentication auth, Model model,  @RequestParam Long estado) {
        Long empleadoId = securityService.getIdEmpleado(auth);
        intervencionService.actualizarEstadoIntervencion(empleadoId, ticket.getId(), intervencionId, EstadoIntervencionMapper.mapDtoToEstadoIntervencion(estadoIntervencionService.findById(estado)));
        TicketDTO ticketDB = ticketServiceImpl.findByIdAndEmpleado(empleadoId, ticket.getId());
        Set<EstadoIntervencionDTO> estadosIntervencion = estadoIntervencionService.findAll();
        model.addAttribute("ticket", ticketDB);
        model.addAttribute("estadosIntervencion", estadosIntervencion);
        model.addAttribute("mensaje", "Intervención actualizada correctamente");
        return "ticket/formTicketUpdateStatus"; 
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/create/{idTicket}")
    public String crearIntervencion(@PathVariable Long idTicket, Model model, Authentication auth){
        model.addAttribute("ticketId", idTicket);
        model.addAttribute("empleadoId", empleadoService.findByEmail(auth.getName()).getId());
        model.addAttribute("intervencionEstados", estadoIntervencionService.findAll());
        model.addAttribute("intervencion", new IntervencionDTO());
        logger.info("Creación de intervención para ticket: {} por empleado: {}", idTicket, auth.getName());
        return ViewRouteHelper.FORM_CREATE_INTERVENCION;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/create")
    public String postCrearIntervencion(@Valid @ModelAttribute IntervencionDTO intervencion, 
                                        @RequestParam Long ticketId,
                                        @RequestParam Long empleadoId,
                                        @RequestParam("estado.id") Long estadoId, 
                                        Authentication auth,
                                        RedirectAttributes redirectAttributes){
            TicketDTO ticket = ticketServiceImpl.findById(ticketId);
            EmpleadoDTO empleado = empleadoService.findById(empleadoId);
            EmpleadoDeIntervencionDTO empleadoIntervencion = EmpleadoDeIntervencionMapper.mapToEmpleadoIntervencionDto(empleadoService.findById(empleadoId));
            EstadoIntervencionDTO estadoIntervencion = estadoIntervencionService.findById(estadoId);
            EstadoTicketDTO estadoTicket = estadoTicketService.findById(2L);
            ticket.getEmpleados().add(empleado);
            if (ticket.getEstado().getEstado().equals("PENDIENTE")) {
                ticket.setEstado(estadoTicket);
            }
            intervencion.setRealizadoPor(empleadoIntervencion);
            intervencion.setEstado(estadoIntervencion);
            logger.info("Intervención creada para ticket: {} por empleado: {}", ticketId, empleadoId);
            ticket.getIntervencion().add(intervencion);
            intervencionService.save(intervencion);
            redirectAttributes.addFlashAttribute("mensajeExito", "Intervención creada con éxito!");
            return "redirect:/empleado/home";
    }

}



