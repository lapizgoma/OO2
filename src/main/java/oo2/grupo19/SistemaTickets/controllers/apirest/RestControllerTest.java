package oo2.grupo19.SistemaTickets.controllers.apirest;

import oo2.grupo19.SistemaTickets.services.impl.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestControllerTest {

    private final ClienteServiceImpl clienteService;

    public RestControllerTest(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/prueba")
    public ResponseEntity<?> enviar2(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clienteService.findAll());
    }

}
