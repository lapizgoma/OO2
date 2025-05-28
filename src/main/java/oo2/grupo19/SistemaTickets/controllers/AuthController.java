package oo2.grupo19.SistemaTickets.controllers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@Log4j2
@SessionAttributes({"cliente","logout"})
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    private final ClienteServiceImpl clienteService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UsuarioServiceImpl usuario, AuthenticationManager authenticationManager, ClienteServiceImpl clienteService) {
        this.usuarioService = usuario;
        this.authenticationManager = authenticationManager;
        this.clienteService = clienteService;
    }

    @GetMapping("/login")
    public String login(Model model,
                    @RequestParam(required = false) String error,
                    @RequestParam(required = false) String logout){
        // Utilizar otra forma del error -> Puede ser con un diccionario ej: {"email","el email es invalido"}
        model.addAttribute("error", error); 
        return ViewRouteHelper.LOGIN;
    }

    @GetMapping("/loginSuccess")
    public String loginCheck(Authentication authentication, HttpSession session) {
        
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email).orElseThrow();
        session.setAttribute("cliente", cliente);
        session.setAttribute("logout", "logout");

        return "redirect:/";
    }

    /*
     * APARTADO CREAR CUENTA PARA CLIENTE !
     */
    
    @GetMapping("/register")
    public String register(Model model){
        if (!model.containsAttribute("cliente")) {
            Cliente cliente = new Cliente();
            cliente.setOrganizacion(new PersonaJuridica());
            model.addAttribute("cliente", cliente); // Se guarda automáticamente en sesión
            return ViewRouteHelper.REGISTER;
        }
        
        log.info("Cliente en sesión: " + model.asMap().get("cliente"));
        model.addAttribute("title", "Ya hay un usuario logueado");
        return ViewRouteHelper.ERROR_404;
    }

    /*
     * Importante agregar nombre, apellido, email y password para el registro
     */
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("cliente") Cliente cliente, 
                                    @RequestParam(required = false) String activo,
                                    SessionStatus sessionStatus){
        if(activo == null){
            cliente.setOrganizacion(null);
        }
        log.info("Datos obtenidos en el post " + cliente);
        usuarioService.registrarUsuario(cliente);
        sessionStatus.setComplete();

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(cliente.getContacto().getEmail(), cliente.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
