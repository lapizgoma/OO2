package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.TicketNotFound;
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
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido eliminar el mensaje");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Intervencion> findAll() {
        try{
            return intervencionRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar la lista de mensajes");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Intervencion> findById(Long id) {
        try{
            return intervencionRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar el Intervencion");
        }
    }

    @Override
    @Transactional
    public void save(Intervencion object) {
        try{
            intervencionRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el Intervencion");
        }
    }

    @Transactional(readOnly = true)
    public List<Intervencion> traerIntervencionPorCliente(Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
		if(usuarioOptional.isPresent()) {			
			return intervencionRepository.traerIntervencionPorCliente(idCliente);
		}
		throw new RuntimeException("El usuario no existe");
	}
	
    @Transactional(readOnly = true)
	public List<Intervencion> traerMensajePorTicket(Long idTicket){
		return intervencionRepository.traerIntervencionPorTicket(idTicket);
	}

    @Transactional(readOnly = true)
	public List<Intervencion> traer(LocalDateTime fecha,Usuario cliente) {
		// Verificamos que el usuario exista en la tabla Mensaje
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(cliente.getId()));
		if(usuarioOptional.isPresent()) {
			return intervencionRepository.traer(fecha, cliente);			
		}
		throw new RuntimeException("El usuario no existe");
	}
    
    @Transactional(readOnly = true)
	public List<Intervencion> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
		if(usuarioOptional.isPresent()) {
			return intervencionRepository.traerFecha(fechaInicio, fechaFinal,idCliente);		
		}
		throw new RuntimeException("El usuario no existe");
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
       throw new TicketNotFound("No se ha encontrado el ticket o no tiene permiso");
       }

        Intervencion intervencion = ticket.getLstIntervencion().stream().filter(i -> i.getId().equals(intervencionId)).findFirst()
        .orElseThrow(() -> new RuntimeException("Intervención no encontrada"));

    intervencion.setEstado(nuevoEstado);
    intervencionRepository.save(intervencion);
    }


}
