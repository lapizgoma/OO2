package negocio;

import java.util.List;

import dao.Dao;
import dao.DashboardDao;
import model.Dashboard;

public class DashboardABM implements INegocio<Dashboard>{
	
	private Dao<Dashboard> dashboardDao = new DashboardDao();
	
	@Override
	public Dashboard traer(Long id) {
		return dashboardDao.traer(id);
	}

	@Override
	public List<Dashboard> traer() {
		return dashboardDao.traer();
	}

	@Override
	public Long agregar(Dashboard objeto) {
		return dashboardDao.agregar(objeto);
	}

	@Override
	public void actualizar(Dashboard objeto) {
		dashboardDao.actualizar(objeto);
	}

	@Override
	public void eliminar(Dashboard objeto) {
		dashboardDao.eliminar(objeto);
	}
}
