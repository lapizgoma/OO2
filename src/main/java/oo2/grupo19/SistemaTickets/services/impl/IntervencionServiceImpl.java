package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IIntervencion;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Qualifier("mensajeService")
public class IntervencionServiceImpl implements IService<Intervencion> {

    private final IIntervencion intervencionRepository;

    public IntervencionServiceImpl(IIntervencion mensajeRepository) {
        this.intervencionRepository = mensajeRepository;
    }

    @Override
    public void delete(Long id) {
        try{
            intervencionRepository.deleteById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido eliminar el mensaje");
        }
    }

    @Override
    public List<Intervencion> findAll() {
        try{
            return intervencionRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar la lista de mensajes");
        }
    }

    @Override
    public Optional<Intervencion> findById(Long id) {
        try{
            return intervencionRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar el Intervencion");
        }
    }

    @Override
    public void save(Intervencion object) {
        try{
            intervencionRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el Intervencion");
        }
    }

    public List<Intervencion> traerIntervencionPorCliente(Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
		if(usuarioOptional.isPresent()) {			
			return intervencionRepository.traerIntervencionPorCliente(idCliente);
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Intervencion> traerMensajePorTicket(Long idTicket){
		return intervencionRepository.traerIntervencionPorTicket(idTicket);
	}
	
	public List<Intervencion> traer(LocalDateTime fecha,Usuario cliente) {
		// Verificamos que el usuario exista en la tabla Mensaje
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(cliente.getId()));
		if(usuarioOptional.isPresent()) {
			return intervencionRepository.traer(fecha, cliente);			
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Intervencion> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeIntervencion(idCliente));
		if(usuarioOptional.isPresent()) {
			return intervencionRepository.traerFecha(fechaInicio, fechaFinal,idCliente);		
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public Intervencion traerFecha(LocalDateTime fecha) {
		return intervencionRepository.findByFecha(fecha);
	}
	
	public Usuario traerUsuarioDesdeIntervencion(Long idCliente) {
		// Si esta presente nos devuelve el usuario
		return intervencionRepository.traerClienteDesdeIntervencion(idCliente);		
	}
    
}
