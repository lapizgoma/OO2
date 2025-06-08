package oo2.grupo19.SistemaTickets.util;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Configuration
public class LoadEstados {
    private final UsuarioServiceImpl empleadoRepository;
    
    public LoadEstados(UsuarioServiceImpl empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

@Bean
public CommandLineRunner cargarEstados(IEstadoIntervencion estadoIntervencionRepository,
                                        IEstadoTicket estadoTicketRepository,
                                        IRole roleRepository,
                                        IPrioridad prioridadRepository){
        
    return args -> {

        if(prioridadRepository.count() == 0){
            prioridadRepository.saveAll(List.of(
                new Prioridad(1L,"Alta"),
                new Prioridad(2L,"Media"),
                new Prioridad(3L,"Baja")
            ));
        }

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                new Role(1L, RoleType.USER),
                new Role(2L, RoleType.EMPLOYEE),
                new Role(3L, RoleType.ADMIN)
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
            empleado.agregarRoles(roleRepository.findById(2L).get());
            empleado.setContacto(contacto);
            empleado.asignarContactoUsuario();
            // ... otros campos necesarios

            empleadoRepository.registrarUsuario(empleado);
            System.out.println("Empleado de prueba creado.");
        } else {
            System.out.println("Empleado de prueba ya existe.");
        }

        if (empleadoRepository.findByEmail("admin@admin.com").isEmpty()) {
            Empleado admin = new Empleado();
            Contacto contacto = new Contacto();
            contacto.setCalle("123123");
            contacto.setEmail("admin@admin.com");
            contacto.setLocalidad("aaaaaa");
            contacto.setNroPuerta("44");
            contacto.setTelefono("44442244");
            admin.setNombre("admin");
            admin.setApellido("de prueba");
            admin.setPassword("admin");
            admin.setNroLegajo("4444");
            admin.setDni("44444444");
            admin.agregarRoles(roleRepository.findById(3L).get());
            admin.setContacto(contacto);
            admin.asignarContactoUsuario();
            // ... otros campos necesarios

            empleadoRepository.registrarUsuario(admin);
            System.out.println("admin de prueba creado.");
        } else {
            System.out.println("admin de prueba ya existe.");
        }

        if (empleadoRepository.findByEmail("cliente@cliente.com").isEmpty()) {
            Cliente cliente = new Cliente ();
            Contacto contacto = new Contacto ();
            contacto.setCalle ("2312312");
            contacto.setEmail ("cliente@cliente.com");
            contacto.setLocalidad ("SDASDAS");
            contacto.setNroPuerta ("33");
            contacto.setTelefono ("3333333");
            cliente.setNombre ("Viernes");
            cliente.setApellido ("de prueba");
            cliente.setPassword ("cliente");
            cliente.setDni ("1231123");
            cliente.agregarRoles (roleRepository.findById (1L).get ());
            cliente.setContacto (contacto);
            cliente.asignarContactoUsuario ();
            // ... otros campos necesarios

            empleadoRepository.registrarUsuario (cliente);
            System.out.println ("Usuario de prueba creado.");
        } else {
            System.out.println ("Usuario de prueba ya existe.");
        }

    };

    
}

}
