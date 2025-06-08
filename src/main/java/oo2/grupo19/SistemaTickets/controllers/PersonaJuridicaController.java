package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.impl.PersonaJuridicaService;

@Controller
@RequestMapping("/grupo")
@Log4j2
public class PersonaJuridicaController {
    private PersonaJuridicaService personaJuridicaService;

    public PersonaJuridicaController (PersonaJuridicaService personaJuridicaService) {
        this.personaJuridicaService = personaJuridicaService;
    }

    @PreAuthorize ("hasRole ('ADMIN')")
    @GetMapping ("/crear")
    public String addPersonaJuridica (Model model, Authentication authentication) 
    {
        PersonaJuridicaDTO dto = new PersonaJuridicaDTO ();
        model.addAttribute("personaJuridicaDTO", dto);

        return ViewRouteHelper.FORM_PERSONA_JURIDICA;
    }

    @PreAuthorize ("hasRole ('ADMIN')")
    @PostMapping ("/crear")
    public String postAddPersonaJuridica (
        @ModelAttribute PersonaJuridicaDTO personaJuridicaDTO,
        Model model, 
        Authentication authentication) 
    {
        personaJuridicaDTO = personaJuridicaService.crearPersonaJuridica (personaJuridicaDTO);

        model.addAttribute("personaJuridicaDTO", personaJuridicaDTO);

        return ViewRouteHelper.SEE_PERSONA_JURIDICA;
    }
}
