package oo2.grupo19.SistemaTickets.controllers;

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
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/intervencion")
public class IntervencionController {

    private final IIntervencionService intervencionService;
    private final IEstadoIntervencionService estadoIntervencionService;
    private static final Logger logger = LoggerFactory.getLogger(IntervencionController.class);

    public IntervencionController(IIntervencionService intervencionService, IEstadoIntervencionService estadoIntervencionService) {
        this.intervencionService = intervencionService;
        this.estadoIntervencionService = estadoIntervencionService;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/processing-ticket/update-intervencion")
    public String processUpdateStatusIntervencion(@ModelAttribute IntervencionDTO intervenciondto) {
        intervencionService.save(intervenciondto);
        return ViewRouteHelper.VIEW_TICKET;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/create/{idTicket}")
    public String crearIntervencion(@PathVariable Long idTicket, Model model, Authentication auth){
        model.addAttribute("intervencionEstados", estadoIntervencionService.findAll());
        IntervencionDTO intervencion = new IntervencionDTO();
        intervencion.setTicketId(idTicket);
        model.addAttribute("intervencion", intervencion);
        logger.info("Creación de intervención para ticket: {} por empleado: {}", idTicket, auth.getName());
        return ViewRouteHelper.FORM_CREATE_INTERVENCION;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping("/create")
    public String postCrearIntervencion(@Valid @ModelAttribute IntervencionDTO intervencion,
                                        Authentication auth,
                                        RedirectAttributes redirectAttributes){
            intervencion.setEmpleadoEmail(auth.getName());
            logger.info("Intervención creada para ticket: {} por empleado: {}", intervencion.getTicketId(), intervencion.getEmpleadoEmail());
            intervencionService.save(intervencion);
            redirectAttributes.addFlashAttribute("mensajeExito", "Intervención creada con éxito!");
            return "redirect:/empleado/home";
    }

}



