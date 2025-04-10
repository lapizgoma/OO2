package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Mensaje;
import model.Usuario;

public class MensajeDao extends Dao<Mensaje> {	
	
	public Mensaje traerFecha(LocalDate fecha) {
		Mensaje mensaje = null;
		try {
			iniciaOperacion();
			mensaje = session.createQuery("from Mensaje m inner join fetch m.sender where m.fecha = :fecha",Mensaje.class)
					.setParameter("fecha", fecha)
					.uniqueResult();
		}finally {
			session.close();
		}
		return mensaje;
	}
	
	public List<Mensaje> traerFecha(LocalDate fechaInicio, LocalDate fechaFinal){
		List<Mensaje> lstMensajes = new ArrayList<Mensaje>();
		try {
			iniciaOperacion();
			lstMensajes = session.createQuery("from Mensaje m inner join fetch m.sender where m.fecha BETWEEN :fechaInicio AND :fechaFinal",Mensaje.class)
					.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFinal", fechaFinal)
					.getResultList();
		}finally {
			session.close();
		}
		return lstMensajes;
	}
	
	public List<Mensaje> traer(LocalDate fecha,Usuario usuario){
		List<Mensaje> mensaje = null;
		try {
			iniciaOperacion();
			mensaje = session.createQuery("from Mensaje m inner join fetch m.sender where m.fecha = :fecha AND m.sender = :sender",Mensaje.class)
					.setParameter("fecha", fecha)
					.setParameter("sender", usuario)
					.getResultList();
		}finally {
			session.close();
		}
		return mensaje;
	}
	
}
