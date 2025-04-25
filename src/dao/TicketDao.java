package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import dto.EmpleadoDTO;
import dto.MensajeDTO;
import dto.TicketDTO;
import model.Empleado;
import model.Mensaje;
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
            lista= session.createQuery("from Ticket t inner join fetch t.chats where t.id = :id",Ticket.class)
            		.setParameter("id",idObjeto)
            		.uniqueResult();
            
        } finally {
            session.close();
        }

        return lista;
	}
	
	public TicketDTO traerPorCliente(Long idCliente) {
		Ticket ticket = null;
		try {
			iniciaOperacion();
			ticket = session.createQuery("from Ticket t inner join fetch t.chats where t.cliente.id = :idCliente",Ticket.class)
					.setParameter("idCliente", idCliente)
					.getSingleResult();
		}finally {
			session.close();
		}
		return toDto(ticket);
	}
	
	/*
	 * PARTE DTO's
	 */
	
	private Set<MensajeDTO> mensajesDTOS(Set<Mensaje> mensaje){
		Set<MensajeDTO> mensajesDto = new HashSet<MensajeDTO>();
		for(Mensaje m: mensaje) {
			mensajesDto.add(m.mensajeToDto());
		}
		return mensajesDto;
	}
	
	private Set<EmpleadoDTO> empleadoDTOS(Set<Empleado> empleados){
		Set<EmpleadoDTO> empleadosDto = new HashSet<EmpleadoDTO>();
		for(Empleado e: empleados) {
			empleadosDto.add(e.empleadoToDto());
		}
		return empleadosDto;
	}

	private TicketDTO toDto(Ticket ticket) {
		TicketDTO dto = new TicketDTO();
		dto.setId(ticket.getId());
		dto.setAsunto(ticket.getAsunto());
		dto.setEstado(ticket.getEstado().toString());
		dto.setMensajes(mensajesDTOS(ticket.getChats()));
		dto.setEmpleados(empleadoDTOS(ticket.getLstEmpleado()));
		dto.setCliente(ticket.getCliente().usuarioToDto());
		return dto;
	}


}
