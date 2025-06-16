package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
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
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;

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
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        Optional<Intervencion> estadoOpt = intervencionRepository.findById(intervenciondto.getId());
        if(estadoOpt.isPresent()) {
            Intervencion intervencionDB = intervencionRepository.findById(intervenciondto.getId()).get();
            Intervencion intervencionNew = IntervencionMapper.mapToIntervencionEntity(intervenciondto, intervencionDB);
            Empleado empleado = intervencionDB.getRealizadoPor();
            intervencionNew.setRealizadoPor(empleado);
            Ticket ticket = intervencionDB.getTicket();
            intervencionNew.setTicket(ticket);
            EstadoIntervencion estado = estadoIntervencionRepository.findByEstado(intervenciondto.getEstado()).get();
            intervencionNew.setEstado(estado);
            intervencionRepository.save(intervencionNew);
        } else {
        Intervencion intervencion = IntervencionMapper.mapToIntervencionEntity(intervenciondto, new Intervencion());
        intervencion.setRealizadoPor(empleadoRepository.findByContactoEmail(intervenciondto.getEmpleadoEmail()).get());
        intervencion.setTicket(ticketRepository.traerPorEmpleadoYId(empleadoRepository.findByContactoEmail(intervenciondto.getEmpleadoEmail()).get().getId(),
                                    intervenciondto.getTicketId()).get());
        intervencion.setEstado(estadoIntervencionRepository.findByEstado(intervenciondto.getEstado()).get());
        Ticket ticket = ticketRepository.findById(intervenciondto.getTicketId()).get();
        ticket.agregarEmpleado(empleadoRepository.findByContactoEmail(intervenciondto.getEmpleadoEmail()).get());
        if(ticket.getEstado().getEstado().equals("PENDIENTE")) {
            ticket.setEstado(estadoTicketRepository.findByEstado("ABIERTO").get());
        }
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
