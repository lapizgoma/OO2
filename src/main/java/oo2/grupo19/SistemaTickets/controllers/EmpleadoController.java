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
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@Log4j2
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
        List<Empleado> empleados = empleadoService.traerEmpleadosActivos();
        model.addAttribute("empleados", empleados);
        return ViewRouteHelper.LISTAR_EMPLEADOS;
    }
    

    @GetMapping("/agregar")
    public String obtenerVistaEmpleado(Model model) {
        model.addAttribute("rolRepository", roleRepository.findAll());
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("rol", new Role());
        return ViewRouteHelper.EMPLEADO_REGISTER;
    }

    @PostMapping("/agregar")
    public String postMethodName(@Valid @ModelAttribute Empleado empleado,
            @RequestParam("role.id") Long rolId,
            BindingResult result,
            Authentication auth,
            Model model) {
        Role role = roleRepository.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        empleado.agregarRoles(role); // Usar el método existente en Usuario
        empleadoService.agregarEmpleado(empleado);
        return ViewRouteHelper.EMPLEADO_REGISTRADO;
    }
    
    @PutMapping("/{empleadoId}")
    public String darBajaEmpleado(@PathVariable Long empleadoId) {
        empleadoService.darBajaEmpleado(empleadoId);
        return ViewRouteHelper.EMPLEADO_BORRADO;
    }

    private boolean isAuthenticated(Authentication auth){
        return auth != null && auth.isAuthenticated();
    }
}
