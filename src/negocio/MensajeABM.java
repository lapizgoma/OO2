package negocio;

import java.util.List;

import dao.Dao;
import dao.MensajeDao;
import model.Mensaje;

public class MensajeABM implements INegocio<Mensaje>{
	
	private Dao<Mensaje> mensajeDao = new MensajeDao();
	
	@Override
	public Mensaje traer(Long id) {
		return mensajeDao.traer(id);
	}

	@Override
	public List<Mensaje> traer() {
		return mensajeDao.traer();
	}

	@Override
	public Long agregar(Mensaje objeto) {
		return mensajeDao.agregar(objeto);
	}

	@Override
	public void actualizar(Mensaje objeto) {
		mensajeDao.actualizar(objeto);
	}

	@Override
	public void eliminar(Mensaje objeto) {
		mensajeDao.eliminar(objeto);
	}
}
