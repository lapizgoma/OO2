package negocio;

import java.util.List;
import java.util.Scanner;

import dao.TicketDao;
import dto.TicketDTO;
import model.Ticket;
import model.Usuario;

public class TicketABM implements INegocio<Ticket> {
	private TicketDao ticketDao = new TicketDao();

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
	
	/// METODO IMPORTANTE PARA MOSTRARLE AL CLIENTE!
	public TicketDTO traerPorCliente(Long idCliente) {
		return ticketDao.traerPorCliente(idCliente);
	}
	
	public void agregarUsuarioTicket(Ticket ticket) {
		
		if(ticket.getId() == null || ticket.getId() > 0) {
			persistirCliente(ticket);			
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
