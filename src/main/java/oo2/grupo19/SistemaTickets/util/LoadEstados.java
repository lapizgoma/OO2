package oo2.grupo19.SistemaTickets.util;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.entities.estados.TipoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.repositories.estados.ITipoIntervencion;

@Configuration
public class LoadEstados {

@Bean
public CommandLineRunner cargarEstados(IEstadoIntervencion estadoIntervencionRepository,
                                        IEstadoTicket estadoTicketRepository,
                                        IRole roleRepository,
                                        ITipoIntervencion tipoIntervencionRepository){
        
    return args -> {

        if (tipoIntervencionRepository.count() == 0) {
            tipoIntervencionRepository.saveAll(List.of(
                new TipoIntervencion(1L,"Mensaje"),
                new TipoIntervencion(2L, "Tarea")
            ));
        }

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                new Role(1L, "Administrador"),
                new Role(2L, "Empleado")
            ));
        }

        if (estadoIntervencionRepository.count() == 0) {
            estadoIntervencionRepository.saveAll(List.of(
                new EstadoIntervencion(1L, "Pendiente"),
                new EstadoIntervencion(2L, "Terminado")
            ));
        }


        if (estadoTicketRepository.count() == 0) {
            estadoTicketRepository.saveAll(List.of(
                new EstadoTicket(1L, "Atendido"),
                new EstadoTicket(2L, "Pendiente"),
                new EstadoTicket(3L, "Cerrado")
            ));
        }

    };
}

}
