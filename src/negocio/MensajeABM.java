package negocio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import dao.MensajeDao;
import model.Mensaje;
import model.Usuario;

public class MensajeABM implements INegocio<Mensaje>{
	
	private MensajeDao mensajeDao = new MensajeDao();
	
	public Mensaje traer(Long id) {
		return mensajeDao.traer(id);
	}

	public List<Mensaje> traer() {
		return mensajeDao.traer();
	}

	public Long agregar(Mensaje objeto) {
		return mensajeDao.agregar(objeto);
	}

	public void actualizar(Mensaje objeto) {
		mensajeDao.actualizar(objeto);
	}
	
	public void eliminar(Mensaje objeto) {
		mensajeDao.eliminar(objeto);
	}
	
	public List<Mensaje> traerMensajesPorCliente(Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(idCliente));
		if(usuarioOptional.isPresent()) {			
			return mensajeDao.traerMensajesPorCliente(idCliente);
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Mensaje> traerMensajePorTicket(Long idTicket){
		return mensajeDao.traerMensajePorTicket(idTicket);
	}
	
	public List<Mensaje> traer(LocalDateTime fecha,Usuario cliente) {
		// Verificamos que el usuario exista en la tabla Mensaje
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(cliente.getId()));
		if(usuarioOptional.isPresent()) {
			return mensajeDao.traer(fecha, cliente);			
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public List<Mensaje> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente){
		Optional<Usuario> usuarioOptional = Optional.of(traerUsuarioDesdeMensaje(idCliente));
		if(usuarioOptional.isPresent()) {
			return mensajeDao.traerFecha(fechaInicio, fechaFinal,idCliente);		
		}
		throw new RuntimeException("El usuario no existe");
	}
	
	public Mensaje traerFecha(LocalDateTime fecha) {
		return mensajeDao.traerFecha(fecha);
	}
	
	public Usuario traerUsuarioDesdeMensaje(Long idCliente) {
		// Si esta presente nos devuelve el usuario
		return mensajeDao.traerUsuarioDesdeMensaje(idCliente);		
	}
}
