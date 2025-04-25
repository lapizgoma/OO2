package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import model.Cliente;
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
	
	public Cliente traerCliente(Long id) {
		Cliente cliente = null;
		try {
			iniciaOperacion();
			cliente = session.createQuery("from Cliente c where id = :id",Cliente.class)
					.setParameter("id", id)
					.getSingleResult();
		}finally {
			session.close();
		}
		return cliente;
	}
	
	public List<Cliente> traerClientes(){
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			iniciaOperacion();
			clientes = session.createQuery("from Cliente c where",Cliente.class).getResultList();
		}finally {
			session.close();
		}
		return clientes;
	}
	
	/*
	 * CU Kevin Vittor
	 * Aclaracion: crear Usuario ya realizado gracias al Dao.java
	 */
	public void agregarCliente(Cliente cliente) {
		try {
			iniciaOperacion();
			session.save(cliente);
			tx.commit();
		}catch (HibernateException e) {
			manejaExcepcion(e);
			throw e;
		}finally {
			session.close();
		}
	}

}
