package oo2.grupo19.SistemaTickets.util;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;

@Configuration
@Log4j2
public class LoadEstados {
    private final IEmpleado empleadoRepository;
    private final ICliente clienteRepository;
    private final PasswordEncoder passwordEncoder;
    
    public LoadEstados(IEmpleado empleadoRepository, ICliente clienteRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
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
                new Role(1L, RoleType.CUSTOMER),
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
                new EstadoTicket(1L, "Pendiente"),
                new EstadoTicket(2L, "Atendido"),
                new EstadoTicket(3L, "Cerrado")
            ));
        }

        if (empleadoRepository.findByContactoEmail("empleado@empleado.com").isEmpty()) {
            log.info("Encontrado!!!!!!!!");
            Empleado empleado = new Empleado();
            Contacto contacto = new Contacto();
            contacto.setCalle("2312312");
            contacto.setEmail("empleado@empleado.com");
            contacto.setLocalidad("SDASDAS");
            contacto.setNroPuerta("33");
            contacto.setTelefono("3333333");
            empleado.setNombre("empleado");
            empleado.setApellido("de prueba");
            empleado.setPassword(passwordEncoder.encode("empleado"));
            empleado.setNroLegajo("13333");
            empleado.setDni("11111111");
            empleado.agregarRoles(roleRepository.findById(2L).get());
            empleado.setContacto(contacto);
            empleado.asignarContactoUsuario();
            // ... otros campos necesarios

            empleadoRepository.save(empleado);
            System.out.println("Empleado de prueba creado.");
        } else {
            System.out.println("Empleado de prueba ya existe.");
        }
        if (clienteRepository.findByContactoEmail("cliente@cliente.com").isEmpty()) {
            Cliente cliente = new Cliente();
            Contacto contacto = new Contacto();
            contacto.setCalle("2312");
            contacto.setEmail("cliente@cliente.com");
            contacto.setLocalidad("asdsad");
            contacto.setNroPuerta("323");
            contacto.setTelefono("443333");
            cliente.setNombre("cliente");
            cliente.setApellido("de prueba");
            cliente.setPassword(passwordEncoder.encode("cliente"));
            cliente.setDni("22222222");
            cliente.agregarRoles(roleRepository.findById(1L).get());
            cliente.setContacto(contacto);
            cliente.asignarContactoUsuario();
            
            // ... otros campos necesarios

            clienteRepository.save(cliente);
            System.out.println("Cliente de prueba creado.");
        } else {
            System.out.println("Cliente de prueba ya existe.");
        }

        if (empleadoRepository.findByContactoEmail("admin@admin.com").isEmpty()) {
            Empleado admin = new Empleado();
            Contacto contacto = new Contacto();
            contacto.setCalle("123123");
            contacto.setEmail("admin@admin.com");
            contacto.setLocalidad("aaaaaa");
            contacto.setNroPuerta("44");
            contacto.setTelefono("44442244");
            admin.setNombre("admin");
            admin.setApellido("de prueba");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setNroLegajo("4444");
            admin.setDni("44444444");
            admin.agregarRoles(roleRepository.findById(3L).get());
            admin.setContacto(contacto);
            admin.asignarContactoUsuario();
            // ... otros campos necesarios

            empleadoRepository.save(admin);
            System.out.println("admin de prueba creado.");
        } else {
            System.out.println("admin de prueba ya existe.");
        }

    };

    
}

}
