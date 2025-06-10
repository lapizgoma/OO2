package oo2.grupo19.SistemaTickets.controllers;
import java.util.List;

import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;
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
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;
import static oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;



@Controller
@Log4j2
@RequestMapping("/empleados")
public class EmpleadoController {
    
    private final EmpleadoServiceImpl empleadoService;
    private final IRole roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    public EmpleadoController(EmpleadoServiceImpl empleadoService, IRole roleRepository) {
        this.empleadoService = empleadoService;
        this.roleRepository = roleRepository;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public String listarEmpleados(Model model, Authentication auth) {
        List<Empleado> empleados = empleadoService.traerEmpleadosActivos();
        model.addAttribute("empleados", empleados);
        logger.info("Listado de empleados accedido por: {}", auth.getName());
        return ViewRouteHelper.LISTAR_EMPLEADOS;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/agregar")
    public String obtenerVistaEmpleado(Model model, Authentication auth) {
        model.addAttribute("rolRepository", roleRepository.findByTypeNot(RoleType.USER));
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("rol", new Role());
        logger.info("Vista de registro de empleado accedida por: {}", auth.getName());
        return ViewRouteHelper.EMPLEADO_REGISTER;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public String postMethodName(@Valid @ModelAttribute Empleado empleado,
                                 BindingResult result,
                                @RequestParam("roles") Long rolId,
                                Authentication auth,
                                Model model) {
        if(result.hasErrors()){
            logger.warn("Errores de validación en registro de empleado: {}", result.getAllErrors());
            model.addAttribute("rolRepository", roleRepository.findAll());
            return ViewRouteHelper.EMPLEADO_REGISTER;
        }
        Role role = roleRepository.findById(rolId).orElseThrow(() -> {
            logger.error("Rol no encontrado para id: {}", rolId);
            return new NotFoundException("Rol no encontrado");
        });
        empleado.agregarRoles(role);
        empleadoService.agregarEmpleado(empleado);
        logger.info("Empleado registrado exitosamente (ID): {} por {}", empleado.getId(), auth.getName());
        return ViewRouteHelper.EMPLEADO_REGISTRADO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{empleadoId}")
    public String darBajaEmpleado(@PathVariable Long empleadoId, Authentication auth) {
        empleadoService.darBajaEmpleado(empleadoId);
        logger.info("Empleado dado de baja: {} por {}", empleadoId, auth.getName());
        return ViewRouteHelper.EMPLEADO_BORRADO;
    }

}
