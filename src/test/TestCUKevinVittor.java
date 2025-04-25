package test;

import enums.EstadosTicket;
import model.Cliente;
import model.Ticket;
import negocio.TicketABM;

public class TestCUKevinVittor {
	public static void main(String[] args) {
		TicketABM ticket = new TicketABM();
		System.out.println("HOLA");
		Ticket ticketNuevo = new Ticket(new Cliente("jaime@hotmail.com", "lorez", "jaime", "saracatunga", false,"333333","av Corrientes"), "Consultaas2", EstadosTicket.PENDIENTE);
		ticket.agregarUsuarioTicket(ticketNuevo);
	}
}
