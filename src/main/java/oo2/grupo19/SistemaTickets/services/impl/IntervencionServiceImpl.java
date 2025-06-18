package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.IntervencionMapper;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.InvalidInputException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;

@Log4j2
@Service
public class IntervencionServiceImpl implements IIntervencionService{

    private final IIntervencion intervencionRepository;
    private final ITicket ticketRepository;
    private final IEmpleado empleadoRepository;
    private final IEstadoIntervencion estadoIntervencionRepository;
    private final IEstadoTicket estadoTicketRepository;

    public IntervencionServiceImpl(IIntervencion intervencionRepository, ITicket ticketRepository,
                                        IEmpleado empleadoRepository, IEstadoIntervencion estadoIntervencionRepository, IEstadoTicket estadoTicketRepository) {
        this.intervencionRepository = intervencionRepository;
        this.ticketRepository = ticketRepository;
        this.empleadoRepository = empleadoRepository;
        this.estadoIntervencionRepository = estadoIntervencionRepository;
        this.estadoTicketRepository = estadoTicketRepository;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Long interventionId = Long.parseLong(id);
        try{
            intervencionRepository.deleteById(interventionId);
        }catch(Exception e){
            throw new NotFoundException("Error: no se ha podido encontrar la intervención");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<IntervencionDTO> findAll() {
        return IntervencionMapper.mapToIntervencionDtoSet(intervencionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public IntervencionDTO findById(Long id) {
        return IntervencionMapper.mapToIntervencionDto(
            intervencionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado la intervención con el id: " + id))
        );
    }

    @Override
    @Transactional
    public void save(IntervencionDTO intervenciondto) {
        if (intervenciondto == null) {
            throw new IllegalArgumentException("La intervención no puede ser null");
        }
        
        log.info("Estado recibido en DTO: {}", intervenciondto.getEstado());
        
        if(intervenciondto.getId() != null) {
            // Actualización de intervención existente
            Intervencion intervencionDB = intervencionRepository.findById(intervenciondto.getId())
                .orElseThrow(() -> new NotFoundException("Intervención no encontrada"));
            Intervencion intervencionNew = IntervencionMapper.mapToIntervencionEntity(intervenciondto, intervencionDB);
            
            EstadoIntervencion estado = estadoIntervencionRepository.findByEstado(intervenciondto.getEstado())
                .orElseThrow(() -> new NotFoundException("Estado no encontrado: " + intervenciondto.getEstado()));
            
            intervencionNew.setEstado(estado);
            intervencionNew.setRealizadoPor(intervencionDB.getRealizadoPor());
            intervencionNew.setTicket(intervencionDB.getTicket());
            intervencionRepository.save(intervencionNew);
        } else {
            // Creación de nueva intervención
            log.info("Creando nueva intervención: {}", intervenciondto);
            
            Empleado empleado = empleadoRepository.findByContactoEmail(intervenciondto.getEmpleadoEmail())
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
                
            Ticket ticket = ticketRepository.findById(intervenciondto.getTicketId())
                .orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
                
            EstadoIntervencion estado = estadoIntervencionRepository.findByEstado(intervenciondto.getEstado())
                .orElseThrow(() -> new NotFoundException("Estado no encontrado: " + intervenciondto.getEstado()));
            
            if (intervenciondto.getDescripcion() == null || intervenciondto.getDescripcion().isEmpty()) {
                throw new InvalidInputException("La descripción de la intervención no puede ser null o vacía");
            }
            Intervencion intervencion = new Intervencion();
            intervencion.setDescripcion(intervenciondto.getDescripcion());
            intervencion.setFecha(LocalDateTime.now());
            intervencion.setEstado(estado);
            intervencion.setRealizadoPor(empleado);
            intervencion.setTicket(ticket);
            
            ticket.agregarEmpleado(empleado);
            if(ticket.getEstado().getEstado().equals("PENDIENTE")) {
                ticket.setEstado(estadoTicketRepository.findByEstado("ABIERTO")
                    .orElseThrow(() -> new NotFoundException("Estado de ticket no encontrado")));
            }
            
            log.info("Guardando intervención: {}", intervencion);
            intervencionRepository.save(intervencion);
        }
    }

    @Transactional(readOnly = true)
    public Set<IntervencionDTO> traerIntervencionPorEmpleado(Long idEmpleado){
        try {
            Optional<Usuario> usuarioOptional = Optional.of(traerEmpleadoDesdeIntervencion(idEmpleado));
            if(usuarioOptional.isPresent()) {            
                return IntervencionMapper.mapToIntervencionDtoSet(
                    intervencionRepository.traerIntervencionPorEmpleado(idEmpleado)
                );
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
	
    @Transactional(readOnly = true)
	public Set<IntervencionDTO> traerIntervencionPorTicket(Long idTicket){
		return IntervencionMapper.mapToIntervencionDtoSet(intervencionRepository.traerIntervencionPorTicket(idTicket));
	}

    @Transactional(readOnly = true)
	public Set<IntervencionDTO> traer(LocalDateTime fecha,Empleado empleado) {
		try {
            Optional<Usuario> usuarioOptional = Optional.of(traerEmpleadoDesdeIntervencion(empleado.getId()));
            if(usuarioOptional.isPresent()) {
                return IntervencionMapper.mapToIntervencionDtoSet(intervencionRepository.traer(fecha, empleado));            
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
    
    @Transactional(readOnly = true)
	public Set<IntervencionDTO> traerPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		try {
            Optional<Usuario> usuarioOptional = Optional.of(traerEmpleadoDesdeIntervencion(idCliente));
            if(usuarioOptional.isPresent()) {
                return IntervencionMapper.mapToIntervencionDtoSet(
                    intervencionRepository.traerFecha(fechaInicio, fechaFinal, idCliente)
                );        
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}

    @Transactional(readOnly = true)
	public IntervencionDTO traerPorFecha(LocalDateTime fecha) {
		return IntervencionMapper.mapToIntervencionDto(intervencionRepository.findByFecha(fecha).orElseThrow(() -> new NotFoundException("No se ha encontrado la intervención")));
	}

	@Transactional(readOnly = true)
	public Empleado traerEmpleadoDesdeIntervencion(Long idEmpleado) {
		return intervencionRepository.traerEmpleadoDesdeIntervencion(idEmpleado);		
	}
}
