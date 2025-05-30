package oo2.grupo19.SistemaTickets.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@Log4j2
@SessionAttributes({ "cliente", "logout" })
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    public AuthController(UsuarioServiceImpl usuario,
            ClienteServiceImpl clienteService) {
        this.usuarioService = usuario;
    }

    @GetMapping("/login")
    public String login(Model model,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            HttpSession session) {

        // Limpiar la sesión completamente al acceder al login
        session.invalidate();

        model.addAttribute("error", error);
        return ViewRouteHelper.LOGIN;
    }

    @GetMapping("/loginSuccess")
    public String loginCheck() {

        return ViewRouteHelper.INDEX;
    }

    @GetMapping("/fetch")
    public String loading() {
        return "redirect:/".concat(ViewRouteHelper.INDEX);
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session, Authentication authentication) {

        // Verificar si hay un usuario autenticado
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            log.info("Usuario ya autenticado: " + authentication.getName());
            model.addAttribute("title", "Ya hay un usuario logueado");
            return ViewRouteHelper.ERROR_404;
        }else {
            Cliente cliente = new Cliente();
            cliente.setOrganizacion(new PersonaJuridica());
            model.addAttribute("cliente", cliente);
        }

        return ViewRouteHelper.REGISTER;
    }

    /*
     * Importante agregar nombre, apellido, email y password para el registro
     */
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Cliente cliente,
            @RequestParam(required = false) String activo,
            Authentication currentAuth) {

        // Verificar que no haya un usuario ya autenticado
        if (currentAuth != null && currentAuth.isAuthenticated() &&
                !currentAuth.getName().equals("anonymousUser")) {
            return "redirect:/";
        }

        if (activo == null) {
            cliente.setOrganizacion(null);
        }

        log.info("Datos obtenidos en el post " + cliente);
        usuarioService.registrarUsuario(cliente);

        return ViewRouteHelper.INDEX;
    }

}