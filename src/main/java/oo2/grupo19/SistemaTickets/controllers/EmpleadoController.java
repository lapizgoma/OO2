package oo2.grupo19.SistemaTickets.controllers;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.ContactoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
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
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import org.springframework.web.bind.annotation.PutMapping;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;



@Controller
@Log4j2
@RequestMapping("/employee")
public class EmpleadoController {
    
    private final IEmpleadoService empleadoService;
    private final IRole roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    public EmpleadoController(IEmpleadoService empleadoService, IRole roleRepository) {
        this.empleadoService = empleadoService;
        this.roleRepository = roleRepository;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarEmpleados(Model model, Authentication auth) {
        Set<EmpleadoDTO> empleados = empleadoService.findAllActive();
        if(!empleados.isEmpty()){
            model.addAttribute("empleados", empleados);
            logger.info("Listado de empleados accedido por: {}", auth.getName());
            return ViewRouteHelper.LISTAR_EMPLEADOS;
        }
        throw new NotFoundException("No hay empleados");
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/agregar")
    public String obtenerVistaEmpleado(Model model, Authentication auth) {
        model.addAttribute("rolRepository", roleRepository.findByTypeNot(RoleType.CUSTOMER));
        EmpleadoDTO empleado = new EmpleadoDTO();
        model.addAttribute("empleado", empleado);
        logger.info("Vista de registro de empleado accedida por: {}", auth.getName());
        return ViewRouteHelper.EMPLEADO_REGISTER;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public String postMethodName(@Valid @ModelAttribute EmpleadoDTO empleado,
                            BindingResult result,
                            Authentication auth,
                            Model model) {
    
    // Asegurar que contacto esté inicializado ANTES de verificar errores
    if (empleado.getContacto() == null) {
        empleado.setContacto(new ContactoDTO());
    }
        
    if(result.hasErrors()){
        model.addAttribute("rolRepository", roleRepository.findByTypeNot(RoleType.CUSTOMER));
        logger.warn("Errores de validación en registro de empleado: {}", result.getAllErrors());
        validator(model, result);
        return ViewRouteHelper.EMPLEADO_REGISTER;
    }

    empleadoService.save(empleado);
    logger.info("Empleado registrado exitosamente (ID): {} por {}", empleado.getContacto().getEmail(), auth.getName());
    return ViewRouteHelper.EMPLEADO_REGISTRADO;
}
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{empleadoId}")
    public String darBajaEmpleado(@PathVariable String empleadoEmail, Authentication auth) {
        empleadoService.delete(empleadoEmail);
        logger.info("Empleado dado de baja: {} por {}", empleadoEmail, auth.getName());
        return ViewRouteHelper.EMPLEADO_BORRADO;
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
