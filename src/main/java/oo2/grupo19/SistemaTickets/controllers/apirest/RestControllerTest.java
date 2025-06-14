package oo2.grupo19.SistemaTickets.controllers.apirest;

import lombok.val;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.dto.api.LoginRequestDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.ClienteMapper;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.services.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Log4j2
public class RestControllerTest {

    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;
    private final IEmpleadoService empleadoService;
    private final ITicketService ticketService;
    private final IPersonaJuridicaService personaJuridicaService;

    public RestControllerTest(IClienteService clienteService,
            IUsuarioService usuarioService,
            IEmpleadoService empleadoService,
            ITicketService ticketService,
            IPersonaJuridicaService personaJuridicaService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
        this.ticketService = ticketService;
        this.personaJuridicaService = personaJuridicaService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            if (usuarioService.validarCredenciales(loginRequest.getEmail(), loginRequest.getPassword())) {
                UsuarioDTO usuario = usuarioService.findByEmail(loginRequest.getEmail());
                String role = usuario.get().getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
                    
                return ResponseEntity.ok(Map.of(
                    "username", usuario.get().getUsername(),
                    "role", role
                ));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            log.error("Error en login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> mostrarClientes(@AuthenticationPrincipal Usuario usuario) {
        try {
            if (!usuario.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos para ver esta información");
            }

            return ResponseEntity.ok(clienteService.findAll().stream()
                    .map(cliente -> {
                        var dto = ClienteMapper.mapToClienteDto(cliente);
                        if (cliente instanceof Cliente c) {
                            dto.setOrganizacion(
                                    c.tieneOrganizacion() ? c.getOrganizacion().getRazonSocial() : "Sin organización");
                        }
                        return dto;
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error al obtener clientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de clientes: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarEmpleado(@Valid @RequestBody EmpleadoDTO empleado) {
        try {
            empleadoService.save(empleado);
            log.info("Empleado registrado exitosamente: {}", empleado.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Empleado registrado exitosamente");
        } catch (Exception e) {
            log.error("Error al registrar empleado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar empleado: " + e.getMessage());
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> obtenerEmpleados() {
        try {
            return ResponseEntity.ok(empleadoService.findAllActive());
        } catch (Exception e) {
            log.error("Error al obtener empleados: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener empleados: " + e.getMessage());
        }
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> obtenerTickets() {
        try {
            return ResponseEntity.ok(ticketService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener tickets: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener tickets: " + e.getMessage());
        }
    }

    @GetMapping("/grupo")
    public ResponseEntity<?> getPersonaJuridica(@Valid @ModelAttribute PersonaJuridicaRequestDTO param)
    {
        try
        {
            return ResponseEntity.ok(personaJuridicaService.buscarPersonaJuridica(param.codigo()));
        }
        catch (NotFoundException e)
        {
            log.error("Persona Jurídica no encontrada", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona Jurídica no encontrada: " + e.getMessage());
        }
        catch (ConstraintViolationException e)
        {
            log.error("ERROR de validación", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR de validación: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error("ERROR interno del servidor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR al obtener Persona Jurídica: " + e.getMessage());
        }
    }

}
