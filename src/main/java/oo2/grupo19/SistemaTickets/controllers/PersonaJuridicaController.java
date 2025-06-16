package oo2.grupo19.SistemaTickets.controllers;

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
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/grupo")
@Log4j2
public class PersonaJuridicaController {
    private IPersonaJuridicaService personaJuridicaService;
    private static final Logger logger = LoggerFactory.getLogger(PersonaJuridicaController.class);

    public PersonaJuridicaController (IPersonaJuridicaService personaJuridicaService) {
        this.personaJuridicaService = personaJuridicaService;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping ("/crear")
    public String addPersonaJuridica (Model model, Authentication authentication) 
    {
        PersonaJuridicaDTO dto = new PersonaJuridicaDTO ();
        model.addAttribute("personaJuridicaDTO", dto);

        return ViewRouteHelper.FORM_PERSONA_JURIDICA;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping ("/crear")
    public String postAddPersonaJuridica (
        @ModelAttribute PersonaJuridicaDTO personaJuridicaDTO,
        Authentication authentication) 
    {
        personaJuridicaService.save(personaJuridicaDTO);
        logger.info("Persona jurídica creada con código: {} por: {}", personaJuridicaDTO.getCodigoAcceso(), authentication.getName());
        return "redirect:/grupo/" + personaJuridicaDTO.getCodigoAcceso ();
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'CUSTOMER')")
    @GetMapping ("/{code}")
    public String verPersonaJuridica (@PathVariable String code, Model model) 
    {
        PersonaJuridicaDTO dto = personaJuridicaService.findByCode(code);
        model.addAttribute("personaJuridicaDTO", dto);
        logger.info("Vista de persona jurídica accedida para código: {}", code);

        return ViewRouteHelper.VIEW_PERSONA_JURIDICA;
    }
}
