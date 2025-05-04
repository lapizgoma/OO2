package test;

import enums.EstadosTicket;
import model.Cliente;
import model.Ticket;
import model.Usuario;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class TestCUKevinVittor {
	public static void main(String[] args) throws InterruptedException {
		
		// CU 1. Crear usuario
		
		UsuarioABM usuarioABM = new UsuarioABM();
		Usuario usuario = new Usuario("sergio@hotmail.com", "figueroa", "sergio", "111111", false);
		usuarioABM.agregar(usuario);
		Thread.sleep(5000);
		System.out.println("CU 1. Completado");
		
		
		// CU 2. Crear cuenta
		
		Cliente cliente = usuario.crearCuenta("54325642", "Victor Hugo");
		usuarioABM.agregar(cliente);
		Thread.sleep(5000);
		System.out.println("CU 2. Completado");
		
		// CU 3. El Cliente genera un ticket
		TicketABM ticket = new TicketABM();
		Ticket ticketNuevo = new Ticket(cliente, "Problemas tecnicos", EstadosTicket.PENDIENTE);
		ticket.agregar(ticketNuevo);
		Thread.sleep(5000);
		System.out.println("CU 3. Completado");
	}
}
