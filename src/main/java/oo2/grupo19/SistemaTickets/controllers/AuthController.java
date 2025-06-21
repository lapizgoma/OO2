package oo2.grupo19.SistemaTickets.controllers;
import jakarta.validation.Valid;
import oo2.grupo19.SistemaTickets.services.IClienteService;
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
import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final IClienteService clienteService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/login")
    public String login(Model model,
            @RequestParam(required = false) String error,
            HttpSession session) {
        session.invalidate();
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, intente nuevamente.");
            logger.warn("Intento de login fallido: {}", error);
        }
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
            ClienteDTO cliente = new ClienteDTO();
            model.addAttribute("cliente", cliente);
        }
        return ViewRouteHelper.REGISTER;
    }

    @PostMapping("/register")
    public String registrarUsuario(@Valid @ModelAttribute("cliente") ClienteDTO cliente,
                                BindingResult result,
                                @RequestParam(required = false) String activo,
                                Authentication currentAuth,
                                Model model) {

            logger.info("Es vacio? " + cliente.getOrganizacion().getCodigoAcceso().isEmpty());
            logger.info("Es nulo? " + Boolean.toString(cliente.getOrganizacion().getCodigoAcceso() == null));
        if (result.hasErrors()) {
            logger.warn("Errores de validación en registro de usuario: {}", result.getAllErrors());
            return ViewRouteHelper.REGISTER; // Devolvés a la vista y Thymeleaf lo renderiza automáticamente
        }

        try {
            clienteService.save(cliente);
            logger.info("Usuario registrado exitosamente: {}", cliente.getContacto().getEmail());
        } catch (Exception e) {
            logger.error("Error inesperado al registrar usuario", e);
            model.addAttribute("codigoError", e.getMessage());
            return ViewRouteHelper.REGISTER;
        }

        return ViewRouteHelper.LOGIN;
    }


    private boolean isUserAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser");
    }

}



