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

import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketClientMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketEmployeeMapper;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions;
import oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions.UserNotFoundException;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;


@Service
@Primary
public class TicketServiceImpl implements ITicketService{
    
    private final ITicket ticketRepository;
    private final IUsuario usuarioRepository;
    private final ICliente clienteRepository;
    private final IEmpleado empleadoRepository;
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    public TicketServiceImpl(ITicket ticketRepository, IUsuario usuarioRepository, ICliente clienteRepository, IEmpleado empleadoRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository= empleadoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketDeleteException("Error no se ha podido eliminar el ticket", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error no se ha podido mostrar la lista de tickets", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(Long id) {
        try {
            return ticketRepository.findById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketNotFoundException("Error no se ha podido mostrar el ticket", e);
        }
    }

    @Override
    @Transactional
    public void save(Ticket object) {
        try {
            Usuario userDb = usuarioRepository.findById(object.getCreadoPor().getId()).orElseThrow();
            object.setCreadoPor(userDb);
            ticketRepository.save(object);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketSaveException("Error no se ha podido actualizar/insertar el ticket", e);
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket findByIdAndEmpleado(Long idEmpleado, Long idTicket) {
        try {
            return ticketRepository.traerPorEmpleadoYId(idEmpleado, idTicket);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketNotFoundException("No se ha podido mostrar el ticket", e);
        }
    }

    @Override
    @Transactional
    public void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicket nuevoEstado) {
        Ticket ticket = this.findByIdAndEmpleado(idEmpleado, idTicket);
        if (ticket == null) {
            throw new TicketCustomExceptions.TicketNotFoundException("No se ha encontrado el ticket o no tiene permiso");
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
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets del cliente", e);
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByAsunto(String asunto) {
        try {
            return ticketRepository.traerPorAsunto(asunto);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets ", e);
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByEmpleado(String email) {
        try {
            return ticketRepository.traerPorEmpleado(email);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets del empleado", e);
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByEstado(EstadoTicket estado) {
        try {
            return ticketRepository.traerPorEstado(estado.getId());
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets del cliente", e);
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByPrioridad(Prioridad prioridad) {
        try {
            return ticketRepository.traerPorPrioridad(prioridad.getId());
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets", e);
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
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets", e);
        }
    }

    @Transactional(readOnly = true)
    public TicketDTO traerPorCliente(Long idCliente) {
        try {
            return convertirATicketDTO(ticketRepository.findByCreadoPor_Id(idCliente));
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketNotFoundException("No se ha podido mostrar el ticket", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> traerPorCliente(String email) {
        try {
            return ticketRepository.traerPorCliente(email);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketListException("Error al intentar mostrar los tickets del cliente", e);
        }
    }

    private TicketDTO convertirATicketDTO(Ticket ticket) {
    TicketDTO dto = new TicketDTO();
    dto.setId(ticket.getId());
    dto.setAsunto(ticket.getAsunto());
    dto.setEstado(ticket.getEstado().toString());

    dto.setCliente(ticket.getCreadoPor().usuarioToDto());
    dto.setEmpleados(ticket.getListEmpleado().stream()
        .map(t -> t.empleadoToDto())
        .collect(Collectors.toSet()));
    dto.setIntervencion(ticket.getLstIntervencion().stream()
        .map(m -> m.mensajeToDto())
        .collect(Collectors.toSet()));

    return dto;
}
    @Transactional (readOnly = true)
    public List<Ticket> traerPorEstados (long idEstado) {
        return ticketRepository.traerPorEstado(idEstado);
    }


    @Transactional(readOnly = true)
    public TicketClientDTO getTicketParaCliente(Long ticketId, String clienteEmail) {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketCustomExceptions.TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Cliente clienteEntity = clienteRepository.findByContactoEmail(clienteEmail)
                .orElseThrow(() -> new UserNotFoundException("Cliente no encontrado :/"));

            if (!ticketEntity.usuarioPertenece(clienteEntity)) {
                throw new StatusCustomExceptions.NotAuthorizedException("No pudimos encontrar el Ticket que buscás :/");
            }

            return TicketClientMapper.mapToTicketClientDto(ticketEntity);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketNotFoundException("Error al obtener el ticket para el cliente", e);
        }
    }

    @Transactional(readOnly = true)
    public TicketEmployeeDTO getTicketparaEmpleado (Long ticketId, String empleadoEmail) {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketCustomExceptions.TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
                .orElseThrow(() -> new UserNotFoundException("Empleado no encontrado :/"));

            return TicketEmployeeMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketNotFoundException("Error al obtener el ticket para el empleado", e);
        }
    }

    @Transactional(readOnly = false)
    public TicketEmployeeDTO asignarTicket (Long ticketId, String empleadoEmail) 
    {
        try {
            Ticket ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketCustomExceptions.TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
            Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
                .orElseThrow(() -> new UserNotFoundException("Empleado no encontrado :/"));

            if (!ticketEntity.usuarioPertenece(empleadoEntity)) 
            {
                ticketEntity.agregarEmpleado(empleadoEntity);
            }

            ticketRepository.save(ticketEntity);

            return TicketEmployeeMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
        } catch (Exception e) {
            throw new TicketCustomExceptions.TicketSaveException("Error al asignar el ticket al empleado", e);
        }
    }
}
