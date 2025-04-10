package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import model.Empleado;
import model.Usuario;
import model.UsuarioRegistrado;

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
	
	public UsuarioRegistrado traerUsuarioRegistrado(String email) throws HibernateException{
		UsuarioRegistrado usuarioRegistrado = null;
		try {
			iniciaOperacion();
			usuarioRegistrado = session.createQuery("from UsuarioRegistrado u where e.email = :email",UsuarioRegistrado.class)
					.setParameter("email", email)
					.getSingleResult();
		}finally {
			session.close();
		}
		return usuarioRegistrado;
	}
	
	public List<UsuarioRegistrado> traerUsuarioRegistrados() throws HibernateException{
		List<UsuarioRegistrado> usuariosRegistrados = new ArrayList<UsuarioRegistrado>();
		try {
			iniciaOperacion();
			usuariosRegistrados = session.createQuery("from UsuarioRegistrado e",UsuarioRegistrado.class)
					.getResultList();
		}finally {
			session.close();
		}
		return usuariosRegistrados;
	}
}
