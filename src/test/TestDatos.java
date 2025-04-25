package test;

import model.Empleado;
import model.Mensaje;
import negocio.MensajeABM;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class TestDatos {
	public static void main(String[] args) {
		
		MensajeABM mensajes = new MensajeABM();
		UsuarioABM usuarios = new UsuarioABM();
		TicketABM ticket = new TicketABM();
		
		for(Mensaje m: mensajes.traerMensajePorTicket(1L)) {
			System.out.println(m);
		}
		
		System.out.println();
		
		for(Empleado e: usuarios.traerEmpleado()) {
			System.out.println(e);
		}
		
		System.out.println();
		
//		System.out.println(ticket.traerPorCliente(3L));
//		System.out.println(ticket.traer(1L));
		System.out.println();
		
		
		// CREO UN TICKET
//		ticket.agregar(new Tick)
		
	}
}
