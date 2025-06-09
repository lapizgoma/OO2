package oo2.grupo19.SistemaTickets;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.*;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.*;
import oo2.grupo19.SistemaTickets.services.impl.EmpleadoServiceImpl;

@SpringBootTest

@Transactional
@Rollback(false)
public class EmpleadoServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceTest.class);

    @Autowired
    private IPrioridad prioridadRepository;  
    @Autowired
    private IEstadoTicket estadoTicketRepository;  
    @Autowired
    private IEstadoIntervencion intervencionRepository;  
    @Autowired
    private IEmpleado empleadoRepository;  
    @Autowired
    private ICliente clienteRepository;  
    @Autowired
    private ITicket ticketRepository;  
    @Autowired
    private ITicketService ticketService;  
    @Autowired
    private EmpleadoServiceImpl empleadoService;
    @Autowired
    private EntityManager em;

    @Test
    public void casoDeUso5() {
        List<EstadoTicket> estados = estadoTicketRepository.findAll();/*
        List<Prioridad> prioridad = prioridadRepository.findAll(); 
        Empleado empleado = empleadoRepository.findByContactoEmail("empleado@empleado.com")
                        .filter(u -> u instanceof Empleado)
                        .map(u -> (Empleado) u)
                        .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Hibernate.initialize(empleado.getTickets()); */
        /*Ticket ticket = new Ticket();
        ticket.setAsunto("TestAsunto");
        ticket.setDetalle("Descripcion");
        ticket.setEstado(estados.get(0));
        ticket.setPrioridad(prioridad.get(0));
        ticket.agregarEmpleado((empleado));
        ticketRepository.save(ticket);*/
        ///Cambio estado ticket
        
        Ticket ticket = ticketService.findByIdAndEmpleado(1L, 2L);
        ticketService.actualizarEstadoTicket(1L, 2L, estados.get(2));
        
        System.out.println("Tickets encontrados: " + ticket.toString());
        
    }
    @Test
    public void casoDeUso6() {
        List<EstadoTicket> estados = estadoTicketRepository.findAll();
        List<EstadoIntervencion> estadosInt = intervencionRepository.findAll();
        List<Prioridad> prioridad = prioridadRepository.findAll(); 
        Empleado empleado = empleadoRepository.findByContactoEmail("empleado@empleado.com")
                        .filter(u -> u instanceof Empleado)
                        .map(u -> (Empleado) u)
                        .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Cliente cliente = clienteRepository.findByContactoEmail("cliente@cliente.com")
                        .filter(u -> u instanceof Cliente)
                        .map(u -> (Cliente) u)
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Hibernate.initialize(empleado.getTickets()); 
        Intervencion intervencionNueva = new Intervencion();
        intervencionNueva.setEstado(estadosInt.get(0));
        intervencionNueva.setDescripcion("IntervencionTest");
        intervencionNueva.setRealizadoPor(empleado);
        Ticket ticket = new Ticket();
        ticket.setAsunto("TestAsunto3");
        ticket.setDetalle("Descripcion3");
        ticket.setEstado(estados.get(0));
        ticket.setPrioridad(prioridad.get(0));
        ticket.agregarEmpleado((empleado));
        ticket.setCreadoPor(cliente);
        ticket.agregarMensaje(intervencionNueva);
        ticketRepository.save(ticket);
        ///Cambio estado ticket
        /* 
        Ticket ticket = ticketService.findByIdAndEmpleado(1L, 2L);
        ticketService.actualizarEstadoTicket(1L, 2L, estados.get(2));*/
        
        System.out.println("Tickets encontrados: " + ticket.toString());
        
    }
 
   /* @Test
    public void casoDeUso15() {
        Ticket ticket = ticketRepository.findById(4L);


    } */
    
}