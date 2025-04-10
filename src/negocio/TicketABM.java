package negocio;

import java.util.List;

import dao.Dao;
import dao.TicketDao;
import model.Ticket;

public class TicketABM implements INegocio<Ticket> {
	private Dao<Ticket> ticketDao = new TicketDao();

	@Override
	public Ticket traer(Long id) {
		return ticketDao.traer(id);
	}

	@Override
	public List<Ticket> traer() {
		return ticketDao.traer();
	}

	@Override
	public Long agregar(Ticket objeto) {
		return ticketDao.agregar(objeto);
	}

	@Override
	public void actualizar(Ticket objeto) {
		ticketDao.actualizar(objeto);		
	}

	@Override
	public void eliminar(Ticket objeto) {
		ticketDao.eliminar(objeto);
	}
	
	
	
}
