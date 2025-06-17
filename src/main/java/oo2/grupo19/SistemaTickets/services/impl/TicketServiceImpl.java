package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketEmployeeMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketMapper;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.ITicketService;


@Service
@Log4j2
@Primary
public class TicketServiceImpl implements ITicketService{
    
    private final ITicket ticketRepository;
    private final ICliente clienteRepository;
    private final IEmpleado empleadoRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final IPrioridad prioridadRepository;

    public TicketServiceImpl(ITicket ticketRepository, ICliente clienteRepository, IEmpleado empleadoRepository,
                                IEstadoTicket estadoTicketRepository, IPrioridad prioridadRepository) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository= empleadoRepository;
        this.estadoTicketRepository = estadoTicketRepository;
        this.prioridadRepository = prioridadRepository;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Long ticketId = Long.parseLong(id);
        try {
            ticketRepository.deleteById(ticketId);
        } catch (Exception e) {
            throw new NotFoundException("Error no se ha podido eliminar el ticket");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TicketDTO> findAll() {
        return TicketMapper.mapToTicketDtoSet(ticketRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDTO findById(Long id) {
        return TicketMapper.mapToTicketDto(
            ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket con el id: " + id))
        );
    }

    @Override
    @Transactional
    public void save(TicketDTO ticketdto) {
        if(ticketdto == null) {
            throw new IllegalArgumentException("El ticket no puede ser null");
        }
        Ticket ticketNew = TicketMapper.mapToTicketEntity(ticketdto, new Ticket());
        ticketNew.setCreadoPor(clienteRepository.findByContactoEmail(ticketdto.getClienteEmail()).get());
        ticketNew.setEstado(estadoTicketRepository.findByEstado(ticketdto.getEstado().getEstado()).get());
        ticketRepository.save(ticketNew);
    } 

    @Override
    @Transactional(readOnly = true)
    public TicketEmployeeDTO findByIdAndEmpleado(Long idEmpleado, Long idTicket) {
        return TicketEmployeeMapper.mapToTicketEmployeeDto(
            ticketRepository.traerPorEmpleadoYId(idTicket, idEmpleado)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket con el id: " + idTicket + " o no tiene permiso"))
        );
    }


    @Override
    public TicketDTO findUltimoPorEmailYAsunto(String email, String asunto) {
        return TicketMapper.mapToTicketDto(
            ticketRepository.findUltimoPorEmailYAsunto(email, asunto)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket con el asunto: " + asunto + " para el cliente: " + email))
        );
    }

    @Override
    @Transactional
    public void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicketDTO nuevoEstado) {
        Ticket ticket = ticketRepository.traerPorEmpleadoYId(idEmpleado, idTicket)
                            .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket o no tiene permiso"));
        ticket.setEstado(estadoTicketRepository.findByEstado(nuevoEstado.getEstado())
            .orElseThrow(() -> new NotFoundException("No se ha encontrado el estado: " + nuevoEstado.getEstado())));
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void actualizarPrioridadTicket(Long idEmpleado, Long idTicket, PrioridadDTO prioridad) {
        Ticket ticket = ticketRepository.traerPorEmpleadoYId(idEmpleado, idTicket)
                            .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket o no tiene permiso"));
        ticket.setPrioridad(prioridadRepository.findByPrioridad(prioridad.getPrioridad())
            .orElseThrow(() -> new NotFoundException("No se ha encontrado la prioridad: " + prioridad.getPrioridad())));
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByCliente(String email) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorCliente(email));
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByAsunto(String asunto) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorAsunto(asunto));
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByEmpleado(String email) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorCliente(email));
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByEstado(EstadoTicketDTO estado) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorEstado(estado.getEstado()));
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByPrioridad(PrioridadDTO prioridad) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorPrioridad(prioridad.getPrioridad()));
    }

    @Override
    @Transactional
    public Set<TicketEmployeeDTO> findTicketByFechaHora(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.traerPorRangoFecha(inicio, fin));
    }

    @Transactional(readOnly = true)
    public Set<TicketEmployeeDTO> traerPorCliente(Long idCliente) {
        return TicketEmployeeMapper.mapToTicketEmployeeDtoSet(ticketRepository.findByCreadoPor_Id(idCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TicketDTO> traerParaCliente(String email) {
        return TicketMapper.mapToTicketDtoSet(ticketRepository.traerPorCliente(email));
    }

    @Transactional(readOnly = true)
    public TicketDTO getTicketParaCliente(Long ticketId, String clienteEmail) {
        Ticket ticketEntity = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new NotFoundException("No pudimos encontrar el Ticket que buscás"));
        Cliente clienteEntity = clienteRepository.findByContactoEmail(clienteEmail)
        .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        if (!ticketEntity.usuarioPertenece(clienteEntity)) {
            throw new StatusCustomExceptions.NotAuthorizedException("No estas autorizado para ver el ticket");
        }
        return TicketMapper.mapToTicketDto(ticketEntity);
    }

    @Transactional(readOnly = true)
    public TicketEmployeeDTO getTicketparaEmpleado(Long ticketId, String empleadoEmail) {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("No pudimos encontrar el Ticket que buscás"));
            log.info ("EMPLEADO PIDE TICKET ID: " + ticketId.toString ());
            return TicketEmployeeMapper.mapToTicketEmployeeDto(ticketEntity);
        } catch (Exception e) {
            throw new NotFoundException("Error al obtener el ticket para el empleado" + e.getMessage());
        }
    }

    @Transactional(readOnly = false)
    public TicketEmployeeDTO asignarTicket (Long ticketId, String empleadoEmail) 
    {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado :/"));

            if (!ticketEntity.usuarioPertenece(empleadoEntity)) 
            {
                ticketEntity.agregarEmpleado(empleadoEntity);
            }

            ticketRepository.save(ticketEntity);

            return TicketEmployeeMapper.mapToTicketEmployeeDto(ticketEntity);
        } catch (Exception e) {
            throw new NotFoundException("Error al asignar el ticket al empleado");
        }
    }
}
