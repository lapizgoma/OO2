package oo2.grupo19.SistemaTickets.controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    
    private final EmpleadoServiceImpl empleadoService;
    private final UsuarioServiceImpl usuarioService;
    private final IRole roleRepository;

    public EmpleadoController(EmpleadoServiceImpl empleadoService, UsuarioServiceImpl usuarioService, IRole roleRepository) {
        this.empleadoService = empleadoService;
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
    }
    
    @GetMapping("")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.listarTodos();
        model.addAttribute("empleados", empleados);
        return ViewRouteHelper.LISTAR_EMPLEADOS;
    }
    

    @GetMapping("/agregar")
    public String obtenerVistaEmpleado(Model model) {
        model.addAttribute("empleadoRoles", roleRepository.findAll());
        model.addAttribute("empleado", new Empleado());
        return ViewRouteHelper.EMPLEADO_REGISTER;
    }

    @PostMapping("/agregar")
    public String postMethodName(@Valid @ModelAttribute Empleado empleado,
            @RequestParam("rolOption.id") Long rolId,
            BindingResult result,
            Authentication auth,
            Model model) {
        if(isAuthenticated(auth)){
            usuarioService.registrarUsuario(empleado);
            return "/empleados/";
        }else{
            throw new NotAuthorizedException("No estas autorizado para realizar una intervencion");
        }
    }
    
    @PutMapping("/{empleadoId}")
    public String darBajaEmpleado(@PathVariable Long empleadoId) {
        empleadoService.darBajaEmpleado(empleadoId);
        return "/empleados/";
    }

    private boolean isAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated();
    }
}
