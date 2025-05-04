package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Mensaje;
import model.Usuario;

public class MensajeDao extends Dao<Mensaje> {	
	
	public Mensaje traerFecha(LocalDateTime fecha) {
		Mensaje mensaje = null;
		try {
			iniciaOperacion();
			mensaje = session.createQuery("from Mensaje m inner join fetch m.usuario where m.fecha = :fecha",Mensaje.class)
					.setParameter("fecha", fecha)
					.uniqueResult();
		}finally {
			session.close();
		}
		return mensaje;
	}
	
	public List<Mensaje> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long  idCliente){
		List<Mensaje> lstMensajes = new ArrayList<Mensaje>();
		try {
			iniciaOperacion();
			lstMensajes = session.createQuery("from Mensaje m inner join fetch m.usuario where m.usuario.id = :idCliente m.fecha BETWEEN :fechaInicio AND :fechaFinal",Mensaje.class)
					.setParameter("idCliente", idCliente)
					.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFinal", fechaFinal)
					.getResultList();
		}finally {
			session.close();
		}
		return lstMensajes;
	}
	
	public List<Mensaje> traer(LocalDateTime fecha,Usuario usuario){
		List<Mensaje> mensaje = null;
		try {
			iniciaOperacion();
			mensaje = session.createQuery("from Mensaje m inner join fetch m.usuario where m.fecha = :fecha AND m.usuario = :sender",Mensaje.class)
					.setParameter("fecha", fecha)
					.setParameter("sender", usuario)
					.getResultList();
		}finally {
			session.close();
		}
		return mensaje;
	}
	
	public List<Mensaje> traerMensajesPorCliente(Long idCliente){
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			iniciaOperacion();
			mensajes = session.createQuery("from Mensaje m inner join fetch m.usuario where m.usuario.id = :idCliente order by m.fecha asc",Mensaje.class)
					.setParameter("idCliente", idCliente)
					.getResultList();
		}finally {
			session.close();
		}
		return mensajes;
	}
	
	public Usuario traerUsuarioDesdeMensaje(Long idCliente) {
	    Usuario usuario = null;
	    try {
	        iniciaOperacion();
	        Mensaje mensaje = session.createQuery(
	                "from Mensaje m inner join fetch m.usuario where m.usuario.id = :idCliente", Mensaje.class)
	            .setParameter("idCliente", idCliente)
	            .setMaxResults(1)
	            .getSingleResult();
	        usuario = mensaje.getUsuario();
	    } finally {
	        session.close();
	    }
	    return usuario;
	}
	
	public List<Mensaje> traerMensajePorTicket(Long idTicket){
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		
		try {
			iniciaOperacion();
			mensajes = session.createQuery("from Mensaje m where m.ticket.id = :idTicket order by m.fecha asc", Mensaje.class)
					.setParameter("idTicket", idTicket)
					.getResultList();
		}finally {
			session.close();
		}
		
		return mensajes;
	}

	
}
