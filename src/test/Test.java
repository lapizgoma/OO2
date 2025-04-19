package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import enums.EstadosTicket;
import model.Empleado;
import model.Mensaje;
import model.Ticket;
import model.Usuario;
import negocio.MensajeABM;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class Test {
	public static void main(String[] args) {
		UsuarioABM usuario = new UsuarioABM();
		MensajeABM mensajeABM = new MensajeABM();
		TicketABM ticketABM = new TicketABM();
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Set<Mensaje> mensajes = new HashSet<Mensaje>();
		Set<Mensaje> mensajes2 = new HashSet<Mensaje>();
		Set<Mensaje> mensajes3 = new HashSet<Mensaje>();
		
		usuario.agregarEmpleado(new Empleado("kevin@hotmail.com","Vittor","Kevin","222222","10101010"));
		usuario.agregarEmpleado(new Empleado("robert@hotmail.com","Asafg","Roberto","222222","20202020"));
		List<Empleado> empleados = usuario.traerEmpleado();
		
		usuarios.add(new Usuario("jose@hotmail.com", "Rodriguez", "Jose","10000000"));
		usuarios.add(new Usuario("hernan@hotmail.com", "Perez", "Hernan","111111"));
		usuarios.add(new Usuario("leonel@hotmail.com", "Aguirre", "Leonel","rodkkkk"));
		
		mensajes.add(new Mensaje("Tuve problemas con el ticket", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("No estas resolviendo mi duda", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("Entendido", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("Muchas gracias !!", LocalDate.now(), usuarios.get(0)));
		
		mensajes2.add(new Mensaje("Tuve problemas con el ticket", LocalDate.now(), usuarios.get(1)));
		mensajes2.add(new Mensaje("Ok esperare", LocalDate.now(), usuarios.get(1)));
		
		mensajes3.add(new Mensaje("Tengo problemas con el producto", LocalDate.now(), usuarios.get(2)));
		mensajes3.add(new Mensaje("Tienen que gestionar mi ticket?", LocalDate.now(), usuarios.get(2)));
		
		tickets.add(new Ticket(usuarios.get(0),"Incovenientes",EstadosTicket.CERRADO));
		tickets.add(new Ticket(usuarios.get(1),"Incovenientes",EstadosTicket.ATENDIDO));
		tickets.add(new Ticket(usuarios.get(2),"Incovenientes",EstadosTicket.PENDIENTE));
		
		for(Usuario u: usuarios) {
			usuario.agregar(u);
		}
		
		Random rand = new Random();
		
		for(Ticket t: tickets) {
			long indice = rand.nextLong(1L,usuario.traerEmpleado().size());
			t.agregarEmpleado((Empleado)usuario.traer(indice));
			ticketABM.agregar(t);
			System.out.println(t);
		}
		
		for(Mensaje m: mensajes) {
		    tickets.get(0).agregarMensaje(m);
		    m.setTicket(tickets.get(0)); // Establecer la relación inversa
		    mensajeABM.agregar(m);
		}
		for(Mensaje m: mensajes2) {
		    tickets.get(1).agregarMensaje(m);
		    m.setTicket(tickets.get(1)); // Establecer la relación inversa
		    mensajeABM.agregar(m);
		}
		for(Mensaje m: mensajes3) {
		    tickets.get(2).agregarMensaje(m);
		    m.setTicket(tickets.get(2)); // Establecer la relación inversa
		    mensajeABM.agregar(m);
		}

		
		
	}
}
