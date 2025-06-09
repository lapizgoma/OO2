package oo2.grupo19.SistemaTickets.util;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Configuration

public class LoadEstados {
    private final UsuarioServiceImpl empleadoRepository;
    private final UsuarioServiceImpl clienteRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final ITicket ticketRepository;
    
    public LoadEstados(UsuarioServiceImpl empleadoRepository, UsuarioServiceImpl clienteRepository,IEstadoTicket estadoTicketRepository, ITicket ticketRepository) {
        this.empleadoRepository = empleadoRepository;
        this.estadoTicketRepository = estadoTicketRepository;
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
    }

@Bean
public CommandLineRunner cargarEstados(IEstadoIntervencion estadoIntervencionRepository,
                                        IEstadoTicket estadoTicketRepository,
                                        IRole roleRepository,
                                        IPrioridad prioridadRepository){
        
    return args -> {

        if(prioridadRepository.count() == 0){
            prioridadRepository.saveAll(List.of(
                new Prioridad(1L,"Baja"),
                new Prioridad(2L,"Media"),
                new Prioridad(3L,"Alta")
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
                new EstadoTicket(1L, "Pendiente"),
                new EstadoTicket(2L, "Atendido"),
                new EstadoTicket(3L, "Cerrado")
            ));
        }

        if (empleadoRepository.findByEmail("empleado@empleado.com").isEmpty()) {
            Empleado empleado = new Empleado();
            Contacto contacto = new Contacto();
            contacto.setCalle("2312312");
            contacto.setEmail("empleado@empleado.com");
            contacto.setLocalidad("SDASDAS");
            contacto.setNroPuerta("33");
            contacto.setTelefono("3333333");
            empleado.setNombre("empleado");
            empleado.setApellido("de prueba");
            empleado.setPassword("empleado");
            empleado.setNroLegajo("13333");
            empleado.setDni("11111111");
            empleado.setRole(roleRepository.findById(2L).orElseThrow());
            empleado.setContacto(contacto);
            empleado.asignarContactoUsuario();
            // ... otros campos necesarios

            empleadoRepository.registrarUsuario(empleado);
            System.out.println("Empleado de prueba creado.");
        } else {
            System.out.println("Empleado de prueba ya existe.");
        }
        if (clienteRepository.findByEmail("cliente@cliente.com").isEmpty()) {
            Cliente cliente = new Cliente();
            Contacto contacto = new Contacto();
            contacto.setCalle("2312");
            contacto.setEmail("cliente@cliente.com");
            contacto.setLocalidad("asdsad");
            contacto.setNroPuerta("323");
            contacto.setTelefono("443333");
            cliente.setNombre("cliente");
            cliente.setApellido("de prueba");
            cliente.setPassword("cliente");
            cliente.setDni("22222222");
            cliente.setContacto(contacto);
            cliente.asignarContactoUsuario();
            // ... otros campos necesarios

            clienteRepository.registrarUsuario(cliente);
            System.out.println("Cliente de prueba creado.");
        } else {
            System.out.println("Cliente de prueba ya existe.");
        }

    };

    
}

}
