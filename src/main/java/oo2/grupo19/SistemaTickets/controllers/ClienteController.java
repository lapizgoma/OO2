package oo2.grupo19.SistemaTickets.controllers;

import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;

import static oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Log4j2
@RequestMapping("/cuenta")
public class ClienteController {
    private IClienteService clienteService;
    private IPersonaJuridicaService personaJuridicaService;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController (IClienteService clienteService, IPersonaJuridicaService personaJuridicaService) {
        this.clienteService = clienteService;
        this.personaJuridicaService = personaJuridicaService;
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String perfil(Model model, Authentication authentication) {
        model.addAttribute("cliente", clienteService.findByEmail(authentication.getName()));
        return ViewRouteHelper.PROFILE;
    }

    @PostMapping("/editar")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String editarPerfil(@ModelAttribute ClienteDTO cliente,
                            @RequestParam(required = false) String codigoAcceso,
                            RedirectAttributes redirectAttributes) {
        try {
            PersonaJuridicaDTO personaJuridicaDTO = null;
            if (codigoAcceso != null && !codigoAcceso.isBlank()) {
                personaJuridicaDTO = personaJuridicaService.findByCode(codigoAcceso);
                personaJuridicaDTO.setCodigoAcceso(codigoAcceso);
                cliente.setOrganizacion(personaJuridicaDTO);
            }
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("mensajeExito", "El perfil se ha actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("codigoError", e.getMessage());
            return "redirect:/cuenta/perfil";
        }
        return "redirect:/" + ViewRouteHelper.INDEX_USER;
    }

    @PreAuthorize ("hasRole ('CUSTOMER')")
    @GetMapping("/eliminar")
    // Se puede hacer una alerta en el navegador para confirmar y despues reenviar al home
    //      vale la pena que le salga un mensaje? se puede hacer una alerta de eliminado correcto tambien y mejorar el flujo de la web sin tanto click
    public String confirmationPage(@RequestParam(required = false) Boolean confirmed, Authentication authentication, HttpServletRequest request) {
        if (confirmed == null || !confirmed) 
        {
            logger.info("Solicitud de confirmación para eliminar cuenta del usuario: {}", authentication != null ? authentication.getName() : "desconocido");
            return ViewRouteHelper.CONFIRMATION_QUESTION;
        }
        try {
            String email = authentication.getName();
            clienteService.delete(email);
            logger.info("Cuenta eliminada exitosamente para el usuario: {}", authentication.getName());
        } catch (Exception e) {
            logger.error("Error al eliminar la cuenta del usuario: {}", authentication.getName(), e);
            throw new NotFoundException("No se pudo eliminar la cuenta del usuario: " + authentication.getName());
        }
        new SecurityContextLogoutHandler().logout(request, null, null);
        return ViewRouteHelper.REMOVAL_SUCCESS;
    }

}
