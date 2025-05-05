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
	public void eliminar(Usuario u) {
		if (u != null) {
			// al traerlo desde la BD, validamos de que exista.
			// al usar el dato traido, garantizamos que solo se actualice el estado "deleted".
			Usuario usuario_db = usuarioDao.traer(u.getId());
			if (usuario_db != null) {
				usuario_db.setDeleted(true);
				usuarioDao.actualizar(usuario_db);
			}
			u.setDeleted (true);
		}
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
