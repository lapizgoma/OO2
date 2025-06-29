package oo2.grupo19.SistemaTickets.controllers.apirest;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.controllers.apirest.dto.personaJuridica.PersonaJuridicaRequestDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.dto.api.LoginRequestDTO;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class RestControllerTest {

    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;
    private final IEmpleadoService empleadoService;
    private final ITicketService ticketService;
    private final IPersonaJuridicaService personaJuridicaService;
    private final IEstadoTicketService estadoTicketService;

    public RestControllerTest(IClienteService clienteService,
                            IUsuarioService usuarioService,
                            IEmpleadoService empleadoService,
                            ITicketService ticketService,
                            IPersonaJuridicaService personaJuridicaService,
                              IEstadoTicketService estadoTicketService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
        this.ticketService = ticketService;
        this.personaJuridicaService = personaJuridicaService;
        this.estadoTicketService = estadoTicketService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            if (usuarioService.validarCredenciales(loginRequest.getEmail(), loginRequest.getPassword())) {
                UsuarioDTO usuario = usuarioService.findByEmail(loginRequest.getEmail());
                String role = usuario.getRole();
                    
                return ResponseEntity.ok(Map.of(
                    "username", usuario,
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
            return ResponseEntity.ok(clienteService.findAll());
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
            log.info("Empleado registrado exitosamente: {}", empleado.getContacto().getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Empleado registrado exitosamente");
        } catch (Exception e) {
            log.error("Error al registrar empleado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar empleado: " + e.getMessage());
        }
    }

    @PostMapping("/crear-ticket")
    public ResponseEntity<?> crearTicket(@Valid @RequestBody TicketDTO ticketDTO){
        try{
            UsuarioDTO usuarioBd = usuarioService.findByEmail(ticketDTO.getClienteEmail());
            if(usuarioBd != null){
                EstadoTicketDTO estado = estadoTicketService.findById(1L);
                ticketDTO.setEstado(estado);
                ticketService.save(ticketDTO);
                log.info("Ticket creado con exito!");
                TicketDTO ticketDtoBd = ticketService.findUltimoPorEmailYAsunto(ticketDTO.getClienteEmail(),ticketDTO.getAsunto());
                return ResponseEntity.status(HttpStatus.OK).body(ticketDtoBd);
            }else{
                return ResponseEntity.badRequest().body("El cliente con ese email no existe!");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al crear un ticket" + e.getMessage());
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> obtenerEmpleados() {
        try {
            return ResponseEntity.ok(empleadoService.findAllActive());
        } catch (Exception e) {
            log.error("Error al obtener empleados: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener empleados: " + e.getMessage());
        }
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> obtenerTickets() {
        try {
            return ResponseEntity.ok(ticketService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener tickets: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener tickets: " + e.getMessage());
        }
    }

    @GetMapping("/grupo")
    public ResponseEntity<?> getPersonaJuridica(@Valid @RequestBody PersonaJuridicaRequestDTO param)
    {
        try
        {
            return ResponseEntity.ok(personaJuridicaService.findByCode(param.codigo()));
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
