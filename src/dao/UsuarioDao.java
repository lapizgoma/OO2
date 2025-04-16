package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import model.Empleado;
import model.Usuario;

public class UsuarioDao extends Dao<Usuario> {

	public Usuario traer(String email) throws HibernateException {
		Usuario usuario = null;
		try {
			iniciaOperacion();
			usuario = (Usuario) session.createQuery("from Usuario u where u.email = :email")
					.setParameter("email", email).uniqueResult();
		}finally {
			session.close();
		}
		return usuario;
	}
	
	public Empleado traerEmpleado(String email) throws HibernateException{
		Empleado empleado = null;
		try {
			iniciaOperacion();
			empleado = session.createQuery("from Empleado e where e.email = :email",Empleado.class)
					.setParameter("email", email)
					.getSingleResult();
		}finally {
			session.close();
		}
		return empleado;
	}
	
	public List<Empleado> traerEmpleados() throws HibernateException{
		List<Empleado> empleados = new ArrayList<Empleado>();
		try {
			iniciaOperacion();
			empleados = session.createQuery("from Empleado e",Empleado.class)
					.getResultList();
		}finally {
			session.close();
		}
		return empleados;
	}
	
	public void agregarEmpleado(Empleado empleado) {
		try {
			iniciaOperacion();
			session.save(empleado);
			tx.commit();
		}catch(HibernateException e) {
			manejaExcepcion(e);
			throw e;
		}finally {
			session.close();
		}
	}
	
}
