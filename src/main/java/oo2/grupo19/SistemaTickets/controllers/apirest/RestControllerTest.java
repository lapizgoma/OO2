package oo2.grupo19.SistemaTickets.controllers.apirest;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.LoginDTO;
import oo2.grupo19.SistemaTickets.services.IClienteService;
import oo2.grupo19.SistemaTickets.services.IUsuarioService;
import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class RestControllerTest {

    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public RestControllerTest(IClienteService clienteService,IUsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        if(usuarioService.validarCredenciales(login.getEmail(),login.getPassword())){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logueado con exito!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe en el sistema");
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> mostrarClientes(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clienteService.findAll());
    }

}
