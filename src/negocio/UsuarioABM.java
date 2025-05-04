package negocio;

import java.util.List;

import dao.UsuarioDao;
import model.Cliente;
import model.Empleado;
import model.Usuario;

public class UsuarioABM implements INegocio<Usuario> {
	
	private UsuarioDao usuarioDao = new UsuarioDao();

	@Override
	public List<Usuario> traer() {
		return usuarioDao.traer();
	}

	@Override
	public Long agregar(Usuario objeto) {
		if(objeto instanceof Cliente c) {
			Usuario usuario = usuarioDao.traer(objeto.getEmail());
			usuarioDao.eliminar(usuario);				
			return usuarioDao.agregar(c);
		}
		return usuarioDao.agregar(objeto);
	}

	@Override
	public void actualizar(Usuario objeto) {
		usuarioDao.actualizar(objeto);
	}

	@Override
	public void eliminar(Usuario objeto) {
		usuarioDao.eliminar(objeto);
	}
	
	public Empleado traerEmpleado(String email) {
		return usuarioDao.traerEmpleado(email);
	}
	
	public List<Empleado> traerEmpleado() {
		return usuarioDao.traerEmpleados();
	}
	
	public void agregarEmpleado(Empleado empleado) {
		usuarioDao.agregarEmpleado(empleado);
	}

	@Override
	public Usuario traer(Long id) {
		return usuarioDao.traer(id);
	}

}
