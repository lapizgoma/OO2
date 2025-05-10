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
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@Log4j2
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UsuarioServiceImpl usuario, AuthenticationManager authenticationManager) {
        this.usuarioService = usuario;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error){
        if(error != null){
            model.addAttribute(error, "Credenciales invalidas");
        }
        return "formsCredenciales/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, @RequestParam String password){
        if(usuarioService.validarCredenciales(email, password)){
            return "redirect:/";
        }else{
            return "redirect:/login?error";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("usuario", new Usuario());
        return "formsCredenciales/register";
    }

    /*
     * Importante agregar nombre, apellido, email y password para el registro
     */
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario){
        usuarioService.registrarUsuario(usuario);

        // Cuando registramos el usuario se inicia sesion automaticamente
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(usuario.getContacto().getEmail(), usuario.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
