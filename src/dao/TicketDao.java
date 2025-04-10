package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import model.Ticket;

public class TicketDao extends Dao<Ticket>{

	@Override
	public List<Ticket> traer() throws HibernateException {
		List<Ticket> lista = new ArrayList<Ticket>();
		try {
            iniciaOperacion();                    
            lista= session.createQuery("from Ticket t inner join fetch t.chats",Ticket.class).getResultList();
            
        } finally {
            session.close();
        }

        return lista;
	}

	@Override
	public Ticket traer(Long idObjeto) throws HibernateException {
		Ticket lista = null;
		try {
            iniciaOperacion();                    
            lista= session.createQuery("from Ticket t inner join fetch t.chats",Ticket.class)
            		.setParameter("id",idObjeto)
            		.uniqueResult();
            
        } finally {
            session.close();
        }

        return lista;
	}
	
}
