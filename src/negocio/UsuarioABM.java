package negocio;

import java.util.List;

import dao.UsuarioDao;
import model.Empleado;
import model.Usuario;

public class UsuarioABM implements INegocio<Usuario> {
	
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	
	public Usuario traer(String email) {
		return usuarioDao.traer(email);
	}

	@Override
	public List<Usuario> traer() {
		return usuarioDao.traer();
	}

	@Override
	public Long agregar(Usuario objeto) {
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

	@Override
	public Usuario traer(Long id) {
		return null;
	}
}
