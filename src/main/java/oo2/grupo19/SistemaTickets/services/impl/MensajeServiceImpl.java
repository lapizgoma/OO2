package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import oo2.grupo19.SistemaTickets.entities.Mensaje;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IMensaje;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Qualifier("mensajeService")
public class MensajeServiceImpl implements IService<Mensaje> {

    private final IMensaje mensajeRepository;

    public MensajeServiceImpl(IMensaje mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    @Override
    public void delete(Long id) {
        try{
            mensajeRepository.deleteById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido eliminar el mensaje");
        }
    }

    @Override
    public List<Mensaje> findAll() {
        try{
            return mensajeRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar la lista de mensajes");
        }
    }

    @Override
    public Optional<Mensaje> findById(Long id) {
        try{
            return mensajeRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar el mensaje");
        }
    }

    @Override
    public void save(Mensaje object) {
        try{
            mensajeRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el mensaje");
        }
    }

    public List<Mensaje> traerMensajesPorCliente(Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(idCliente));
		if(usuarioOptional.isPresent()) {			
			return mensajeRepository.traerMensajesPorCliente(idCliente);
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Mensaje> traerMensajePorTicket(Long idTicket){
		return mensajeRepository.traerMensajePorTicket(idTicket);
	}
	
	public List<Mensaje> traer(LocalDateTime fecha,Usuario cliente) {
		// Verificamos que el usuario exista en la tabla Mensaje
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(cliente.getId()));
		if(usuarioOptional.isPresent()) {
			return mensajeRepository.traer(fecha, cliente);			
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Mensaje> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(idCliente));
		if(usuarioOptional.isPresent()) {
			return mensajeRepository.traerFecha(fechaInicio, fechaFinal,idCliente);		
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public Mensaje traerFecha(LocalDateTime fecha) {
		return mensajeRepository.findByFecha(fecha);
	}
	
	public Usuario traerUsuarioDesdeMensaje(Long idCliente) {
		// Si esta presente nos devuelve el usuario
		return mensajeRepository.traerUsuarioDesdeMensaje(idCliente);		
	}
    
}
