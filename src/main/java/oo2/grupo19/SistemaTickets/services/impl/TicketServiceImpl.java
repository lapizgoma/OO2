package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.mappers.ClienteMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.IntervencionMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketMapper;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;


@Service
@Primary
public class TicketServiceImpl implements ITicketService{
    
    private final ITicket ticketRepository;
    private final ICliente clienteRepository;
    private final IEmpleado empleadoRepository;
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    public TicketServiceImpl(ITicket ticketRepository, ICliente clienteRepository, IEmpleado empleadoRepository) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository= empleadoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Error no se ha podido eliminar el ticket");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("Error no se ha podido encontrar la lista de tickets");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(Long id) {
        try {
            return ticketRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Error no se ha podido encontrar el ticket");
        }
    }

    @Override
    @Transactional
    public void save(Ticket object) {
        try {
            Cliente userDb = clienteRepository.findById(object.getCreadoPor().getId()).orElseThrow();
            object.setCreadoPor(userDb);
            ticketRepository.save(object);
        } catch (Exception e) {
            throw new RuntimeException("Error no se ha podido actualizar/insertar el ticket", e);
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket findByIdAndEmpleado(Long idEmpleado, Long idTicket) {
        try {
            return ticketRepository.traerPorEmpleadoYId(idEmpleado, idTicket);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    //TODO: HACE FALTA USAR DTO PARA ACTUALIZAR ESTADO?
    @Override
    @Transactional
    public void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicket nuevoEstado) {
        Ticket ticket = this.findByIdAndEmpleado(idEmpleado, idTicket);
        if (ticket == null) {
            throw new NotFoundException("No se ha encontrado el ticket o no tiene permiso");
        }
        
        ticket.setEstado(nuevoEstado);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByCliente(String email) {
        try {
            List<Ticket> tickets = ticketRepository.traerPorCliente(email);
        tickets.forEach(ticket -> log.debug("Ticket ID: {}, Fecha_hora: {}, Tipo: {}", 
            ticket.getId(), ticket.getFechaHora(), ticket.getFechaHora() != null ? ticket.getFechaHora().getClass().getName() : "null"));
        return tickets;
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets del cliente");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByAsunto(String asunto) {
        try {
            return ticketRepository.traerPorAsunto(asunto);
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets ");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByEmpleado(String email) {
        try {
            return ticketRepository.traerPorEmpleado(email);
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets del empleado");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByEstado(EstadoTicket estado) {
        try {
            return ticketRepository.traerPorEstado(estado.getId());
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets del cliente");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByPrioridad(Prioridad prioridad) {
        try {
            return ticketRepository.traerPorPrioridad(prioridad.getId());
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByFechaHora(LocalDate fecha) {
        try {
            LocalDateTime inicio = fecha.atStartOfDay();
            LocalDateTime fin = inicio.plusDays(1);
            return ticketRepository.traerPorRangoFecha(inicio, fin);
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets");
        }
    }

    @Transactional(readOnly = true)
    public TicketDTO traerPorCliente(Long idCliente) {
        try {
            return TicketMapper.mapToTicketDto(ticketRepository.findByCreadoPor_Id(idCliente));
        } catch (Exception e) {
            throw new NotFoundException("No se ha podido mostrar el ticket");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> traerPorCliente(String email) {
        try {
            return ticketRepository.traerPorCliente(email);
        } catch (Exception e) {
            throw new NotFoundException("Error al intentar mostrar los tickets del cliente");
        }
    }

    @Transactional (readOnly = true)
    public List<Ticket> traerPorEstados (long idEstado) {
        return ticketRepository.traerPorEstado(idEstado);
    }


    @Transactional(readOnly = true)
    public TicketDTO getTicketParaCliente(Long ticketId, String clienteEmail) {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Cliente clienteEntity = clienteRepository.findByContacto_Email(clienteEmail)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado :/"));

            if (!ticketEntity.usuarioPertenece(clienteEntity)) {
                throw new StatusCustomExceptions.NotAuthorizedException("No pudimos encontrar el Ticket que buscás :/");
            }

            return TicketMapper.mapToTicketDto(ticketEntity);
        } catch (Exception e) {
            throw new NotFoundException("Error al obtener el ticket para el cliente");
        }
    }

    @Transactional(readOnly = true)
    public TicketEmployeeDTO getTicketparaEmpleado (Long ticketId, String empleadoEmail) {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado :/"));

            return TicketMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
        } catch (Exception e) {
            throw new NotFoundException("Error al obtener el ticket para el empleado");
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

            return TicketMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
        } catch (Exception e) {
            throw new NotFoundException("Error al asignar el ticket al empleado");
        }
    }
}
