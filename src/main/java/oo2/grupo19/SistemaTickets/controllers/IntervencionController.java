package oo2.grupo19.SistemaTickets.controllers;

import java.util.HashMap;
import java.util.Map;

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
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.EmailService;
import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IIntervencionService intervencionService;
    private final IEstadoIntervencionService estadoIntervencionService;
    private final EmailService emailService;
    private final ITicketService ticketService;
    private final IClienteService clienteService;
    private static final Logger logger = LoggerFactory.getLogger(IntervencionController.class);

    public IntervencionController(IIntervencionService intervencionService, IEstadoIntervencionService estadoIntervencionService, EmailService emailService, ITicketService ticketService, IClienteService clienteService) {
        this.intervencionService = intervencionService;
        this.estadoIntervencionService = estadoIntervencionService;
        this.emailService = emailService;
        this.ticketService = ticketService;
        this.clienteService = clienteService;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket/update-intervencion")
    public String processUpdateStatusIntervencion(@ModelAttribute IntervencionDTO intervenciondto, RedirectAttributes redirectAttributes) {
        TicketDTO ticket = ticketService.findById(intervencionService.findById(intervenciondto.getId()).getTicketId());
        if(ticket.getEstado().getEstado().equals("Cerrado") && intervenciondto.getEstado().equals("Pendiente")) {
            redirectAttributes.addFlashAttribute("mensajeAlerta", "No se puede cambiar el estado de la intervención a Pendiente, el ticket ya está cerrado.");
        }else{
            intervencionService.save(intervenciondto);
        }
        return ViewRouteHelper.TICKET_VIEW_ID(intervencionService.findById(intervenciondto.getId()).getTicketId());
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/create/{idTicket}")
    public String crearIntervencion(@PathVariable Long idTicket, Model model, Authentication auth){
        model.addAttribute("intervencionEstados", estadoIntervencionService.findAll());
        IntervencionDTO intervencion = new IntervencionDTO();
        intervencion.setTicketId(idTicket);
        model.addAttribute("intervencion", intervencion);
        logger.info("Creación de intervención para ticket: {} por empleado: {}, ticket id: {}", idTicket, auth.getName(),idTicket);
        return ViewRouteHelper.FORM_CREATE_INTERVENCION;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/create")
    public String postCrearIntervencion(@ModelAttribute IntervencionDTO intervencion,
                                        Authentication auth,
                                        RedirectAttributes redirectAttributes){
            intervencion.setEmpleadoEmail(auth.getName());
            logger.info("Intervención creada para ticket: {} por empleado: {}", intervencion);
            intervencionService.save(intervencion);
            sendEmailIntervencionCreate(intervencion);
            redirectAttributes.addFlashAttribute("mensajeExito", "Intervención creada con éxito!");
            return "redirect:/employee/home";
    }
    
    private void sendEmailIntervencionCreate(IntervencionDTO intervencion) {
        Map<String,Object> infoClient = new HashMap<>();
        TicketDTO ticketdto = ticketService.findById(intervencion.getTicketId());
        infoClient.put("nombre", clienteService.findByEmail(ticketdto.getClienteEmail()).getNombre());
        infoClient.put("mensaje","Se creo una nueva intervención para su ticket");
        infoClient.put("descripcion", intervencion.getDescripcion());
        infoClient.put("id",ticketdto.getId());
        emailService.enviarCorreoHtml(ticketdto.getClienteEmail(), "Nueva intervencion", ViewRouteHelper.INTERVENCION_SUCCESS_MAIL, infoClient);
    }

}



