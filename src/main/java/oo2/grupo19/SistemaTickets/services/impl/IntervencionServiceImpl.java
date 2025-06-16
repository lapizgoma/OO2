package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.IntervencionMapper;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;

@Service
public class IntervencionServiceImpl implements IIntervencionService{

    private final IIntervencion intervencionRepository;
    private final ITicket ticketRepository;

    public IntervencionServiceImpl(IIntervencion intervencionRepository, ITicket ticketRepository) {
        this.intervencionRepository = intervencionRepository;
        this.ticketRepository = ticketRepository;
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
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        Optional<Intervencion> estadoOpt = intervencionRepository.findByFecha(
            LocalDateTime.parse(intervenciondto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );
        if(estadoOpt.isPresent()) {
            intervenciondto.setId(estadoOpt.get().getId());
            Intervencion intervencion = IntervencionMapper.mapToIntervencionEntity(intervenciondto);
            intervencionRepository.save(intervencion);
        } else {
        Intervencion intervencion = IntervencionMapper.mapToIntervencionEntity(intervenciondto);
        intervencionRepository.save(intervencion);
        }
    }

    @Transactional(readOnly = true)
    public Set<IntervencionDTO> traerIntervencionPorCliente(Long idCliente){
        try {
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
            if(usuarioOptional.isPresent()) {            
                return IntervencionMapper.mapToIntervencionDtoSet(
                    intervencionRepository.traerIntervencionPorCliente(idCliente)
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
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(empleado.getId()));
            if(usuarioOptional.isPresent()) {
                return IntervencionMapper.mapToIntervencionDtoSet(intervencionRepository.traer(fecha, empleado));            
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
    
    @Transactional(readOnly = true)
	public Set<IntervencionDTO> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		try {
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
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
	public IntervencionDTO traerFecha(LocalDateTime fecha) {
		return IntervencionMapper.mapToIntervencionDto(intervencionRepository.findByFecha(fecha).orElseThrow(() -> new NotFoundException("No se ha encontrado la intervención")));
	}

	@Transactional(readOnly = true)
	public Usuario traerUsuarioDesdeIntervencion(Long idCliente) {
		return intervencionRepository.traerClienteDesdeIntervencion(idCliente);		
	}

    @Override
    @Transactional
    public void actualizarEstadoIntervencion(Long empleadoId, Long ticketId, Long intervencionId, EstadoIntervencion nuevoEstado) {
        Ticket ticket = ticketRepository.traerPorEmpleadoYId(empleadoId, ticketId)
            .orElseThrow(() -> new NotFoundException("No se ha encontrado el ticket con el id: " + ticketId + " o no tiene permiso"));
        if (ticket == null) {
            throw new NotFoundException("No se ha encontrado el ticket o no tiene permiso");
        }

        Intervencion intervencion = ticket.getLstIntervencion().stream().filter(i -> i.getId().equals(intervencionId)).findFirst()
        .orElseThrow(() -> new NotFoundException("Intervención no encontrada"));

        intervencion.setEstado(nuevoEstado);
        intervencionRepository.save(intervencion);
    }


}
