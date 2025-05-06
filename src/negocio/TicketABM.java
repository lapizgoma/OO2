package negocio;

import java.util.List;
import java.util.Scanner;

import dao.TicketDao;
import dto.TicketDTO;
import model.Empleado;
import model.Ticket;
import model.Usuario;

public class TicketABM implements INegocio<Ticket> {
	private TicketDao ticketDao = new TicketDao();
	private UsuarioABM usuarioABM = new UsuarioABM();

	public Ticket traer(Long id) {
		return ticketDao.traer(id);
	}

	public List<Ticket> traer() {
		return ticketDao.traer();
	}

	public Long agregar(Ticket objeto) {
		return ticketDao.agregar(objeto);
	}

	public void actualizar(Ticket objeto) {
		ticketDao.actualizar(objeto);		
	}

	public void eliminar(Ticket objeto) {
		ticketDao.eliminar(objeto);
	}

	// asigna un Empleado si no lo estaba antes
	// true: se asignó, false: ya estaba asignado
	public boolean asginarEmpleado (Ticket t, Empleado e) {
		int set_len = t.getLstEmpleado ().size ();
		t.getLstEmpleado ().add (e);
		boolean added = set_len + 1 == t.getLstEmpleado ().size ();
		if (added) {
			this.actualizar (t);
		}

		return added;
	}
	
	/// METODO IMPORTANTE PARA MOSTRARLE AL CLIENTE!
	public TicketDTO traerPorCliente(Long idCliente) {
		return ticketDao.traerPorCliente(idCliente);
	}
	
	public void agregarUsuarioTicket(Ticket ticket) {
		if(ticket.getId() == null || ticket.getId() > 0) {
			if(ticket.getCliente() == null) {			
				persistirCliente(ticket);
			}
			agregar(ticket);
		}else {
			actualizar(ticket);
		}
	}
	
	private void persistirCliente(Ticket ticket) {
		if(!ticket.existeUsuario()) {
			// Simulacion en caso de que no existe el usuario
			System.out.println(" ------ Ingrese las siguiente credenciales para generar un ticket ------");
			ticket.setCliente(generarUsuario());
		}
		
		if(existeUsuarioBd(ticket.getCliente()) != null) {
			
		}
	}
	
	private Usuario existeUsuarioBd(Usuario usuario) {
		return usuarioABM.traer(usuario.getId()) != null ? usuarioABM.traer(usuario.getId()) : null;
	}
	
	private Usuario generarUsuario() {
		Scanner s = new Scanner(System.in);
		Usuario usuario = new Usuario();
		System.out.println("Ingrese su nombre: ");
		usuario.setNombre(s.next());
		System.out.println("Ingrese su apellido: ");
		usuario.setApellido(s.next());
		System.out.println("Ingrese su email: ");
		usuario.setEmail(s.next());
		System.out.println("Ingrese su password: ");
		usuario.setPassword(s.next());
		usuario.setDeleted(false);
		s.close();
		return usuario;
	}
}
