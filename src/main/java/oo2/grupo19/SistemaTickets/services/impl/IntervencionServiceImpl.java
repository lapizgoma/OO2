package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.IIntervencionService;

@Service
@Qualifier("mensajeService")
public class IntervencionServiceImpl implements IIntervencionService{

    private final IIntervencion intervencionRepository;
    private final ITicket ticketRepository;

    public IntervencionServiceImpl(IIntervencion intervencionRepository, ITicket ticketRepository) {
        this.intervencionRepository = intervencionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try{
            intervencionRepository.deleteById(id);
        }catch(Exception e){
            throw new NotFoundException("Error: no se ha podido encontrar la intervención");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Intervencion> findAll() {
        try{
            return intervencionRepository.findAll();
        }catch(Exception e){
            throw new NotFoundException("Error: no se ha podido encontrar la lista de intervenciones");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Intervencion> findById(Long id) {
        try{
            return intervencionRepository.findById(id);
        }catch(Exception e){
            throw new NotFoundException("Error: no se ha podido encontrar la intervención");
        }
    }

    @Override
    @Transactional
    public void save(Intervencion object) {
        try{
            intervencionRepository.save(object);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Intervencion> traerIntervencionPorCliente(Long idCliente){
        try {
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
            if(usuarioOptional.isPresent()) {            
                return intervencionRepository.traerIntervencionPorCliente(idCliente);
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
	
    @Transactional(readOnly = true)
	public List<Intervencion> traerMensajePorTicket(Long idTicket){
		return intervencionRepository.traerIntervencionPorTicket(idTicket);
	}

    @Transactional(readOnly = true)
	public List<Intervencion> traer(LocalDateTime fecha,Usuario cliente) {
		try {
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(cliente.getId()));
            if(usuarioOptional.isPresent()) {
                return intervencionRepository.traer(fecha, cliente);            
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
    
    @Transactional(readOnly = true)
	public List<Intervencion> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		try {
            Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
            if(usuarioOptional.isPresent()) {
                return intervencionRepository.traerFecha(fechaInicio, fechaFinal,idCliente);        
            }
            throw new NotFoundException("El usuario no existe");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}

	@Transactional(readOnly = true)
	public Intervencion traerFecha(LocalDateTime fecha) {
		return intervencionRepository.findByFecha(fecha);
	}

	@Transactional(readOnly = true)
	public Usuario traerUsuarioDesdeIntervencion(Long idCliente) {
		// Si esta presente nos devuelve el usuario
		return intervencionRepository.traerClienteDesdeIntervencion(idCliente);		
	}

    @Override
    @Transactional
    public void actualizarEstadoIntervencion(Long empleadoId, Long ticketId, Long intervencionId, EstadoIntervencion nuevoEstado) {
       Ticket ticket = ticketRepository.traerPorEmpleadoYId(empleadoId, ticketId);
       if (ticket == null) {
           throw new NotFoundException("No se ha encontrado el ticket o no tiene permiso");
       }

        Intervencion intervencion = ticket.getLstIntervencion().stream().filter(i -> i.getId().equals(intervencionId)).findFirst()
        .orElseThrow(() -> new NotFoundException("Intervención no encontrada"));

        intervencion.setEstado(nuevoEstado);
        intervencionRepository.save(intervencion);
    }


}
