package oo2.grupo19.SistemaTickets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/grupo")
@Log4j2
public class PersonaJuridicaController 
{
    private IPersonaJuridicaService personaJuridicaService;
    private static final Logger logger = LoggerFactory.getLogger(PersonaJuridicaController.class);

    public PersonaJuridicaController (IPersonaJuridicaService personaJuridicaService) 
    {
        this.personaJuridicaService = personaJuridicaService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping ("/crear")
    public String addPersonaJuridica (Model model, Authentication authentication) 
    {
        PersonaJuridicaDTO dto = new PersonaJuridicaDTO ();
        model.addAttribute("personaJuridicaDTO", dto);

        return ViewRouteHelper.FORM_PERSONA_JURIDICA;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping ("/crear")
    public String postAddPersonaJuridica (
        @Valid @ModelAttribute PersonaJuridicaDTO personaJuridicaDTO,BindingResult result,Model model,
        Authentication authentication) 
    {
        if(result.hasErrors()){
            logger.warn("Errores de validación en registro de usuario: {}", result.getAllErrors());
            validator(model,result);
            return ViewRouteHelper.FORM_PERSONA_JURIDICA;
        }
        personaJuridicaService.crearPersonaJuridica (personaJuridicaDTO);
        logger.info("Persona jurídica creada con código: {} por: {}", personaJuridicaDTO.getCodigoAcceso(), authentication.getName());
        // Es necesario mandar el codigo de acceso por la url?
        return "redirect:/grupo/" + personaJuridicaDTO.getCodigoAcceso ();
    }

    // El codigo se puede pasar a traves de un redirect y obtenerlo desde ahi, es arriesgado pasar el codigo por url. 
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping ("/{code}")
    public String verPersonaJuridica (@PathVariable String code, Model model) 
    {
        PersonaJuridicaDTO dto = personaJuridicaService.findByCode(code);
        model.addAttribute("personaJuridicaDTO", dto);
        model.addAttribute("code", code);   
        logger.info("Vista de persona jurídica accedida para código: {}", code);

        return ViewRouteHelper.VIEW_PERSONA_JURIDICA;
    }

    private void validator(Model model,BindingResult result){
        Map<String,String> errors = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors().forEach(err ->{
                errors.put(err.getField(),err.getDefaultMessage());
            });
            model.addAttribute("errors",errors);
        }

    }
}
