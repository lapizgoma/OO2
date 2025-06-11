package oo2.grupo19.SistemaTickets.controllers;
import jakarta.validation.Valid;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Usuario;

import oo2.grupo19.SistemaTickets.services.IUsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final IUsuarioService usuarioService;
    private final IRole roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(IUsuarioService usuario, IRole roleRepository) {
        this.usuarioService = usuario;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String login(Model model,
            @RequestParam(required = false) String error,
            HttpSession session) {
        session.invalidate();
        if (error != null) {
            logger.warn("Intento de login fallido: {}", error);
        }
        model.addAttribute("error", error);
        return ViewRouteHelper.LOGIN;
    }

    @GetMapping("/loginSuccess")
    public String loginCheck() {
        logger.info("Login exitoso");
        return "redirect:/"+ViewRouteHelper.INDEX;
    }

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {
        if (isUserAuthenticated(authentication)) {
            logger.warn("Intento de registro con usuario ya autenticado: {}", authentication.getName());
            throw new AlreadyExistsException("El usuario ya ha iniciado sesion");
        } else {
            Cliente cliente = new Cliente();
            Contacto contacto = new Contacto();
            cliente.setContacto(contacto);
            cliente.setOrganizacion(new PersonaJuridica());
            model.addAttribute("cliente", cliente);
        }
        return ViewRouteHelper.REGISTER;
    }

    @PostMapping("/register")
    public String registrarUsuario(@Valid @ModelAttribute Cliente cliente,
            BindingResult result,
            @RequestParam(required = false) String activo,
            Authentication currentAuth,
            Model model) {
        if(result.hasErrors()){
            logger.warn("Errores de validación en registro de usuario: {}", result.getAllErrors());
            validator(model,result);
            return ViewRouteHelper.REGISTER;
        }
        Optional<Usuario> optionalClienteBd = usuarioService.findByEmail(cliente.getContacto().getEmail());
        if(optionalClienteBd.isPresent()){
            logger.warn("Intento de registro con email ya existente: {}", cliente.getContacto().getEmail());
            throw new AlreadyExistsException("Ya existe un usuario registrado");
        }else if (isUserAuthenticated(currentAuth)) {
            logger.warn("Intento de registro con usuario ya autenticado: {}", currentAuth.getName());
            throw new AlreadyExistsException("Ya hay un usuario autenticado");
        }
        if (activo == null) {
            cliente.setOrganizacion(null);
        }
        try {
            cliente.agregarRoles(roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("Rol no encontrado")));
            usuarioService.registrarUsuario(cliente);
            logger.info("Usuario registrado exitosamente: {}", cliente.getContacto().getEmail());
        } catch (Exception e) {
            logger.error("Error inesperado al registrar usuario", e);
            throw e;
        }
        return ViewRouteHelper.INDEX;
    }

    private boolean isUserAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser");
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



