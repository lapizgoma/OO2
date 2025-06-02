package oo2.grupo19.SistemaTickets.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.UserAlreadyAuthenticatedException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@Log4j2
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    private final IRole roleRepository;
    public AuthController(UsuarioServiceImpl usuario,
            ClienteServiceImpl clienteService, IRole roleRepository) {
        this.usuarioService = usuario;
        this.roleRepository = roleRepository;
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

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {

        // Verificar si hay un usuario autenticado
        if (isUserAuthenticated(authentication)) {
            throw new UserAlreadyAuthenticatedException("El usuario ya ha iniciado sesion");
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
        if (isUserAuthenticated(currentAuth)) {
            throw new UserAlreadyAuthenticatedException("Ya hay un usuario autenticado");
        }

        if (activo == null) {
            cliente.setOrganizacion(null);
        }
        cliente.agregarRoles(roleRepository.findById(1L).get());
        usuarioService.registrarUsuario(cliente);
        return ViewRouteHelper.INDEX;
    }

    private boolean isUserAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser");
    }

}