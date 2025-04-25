package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import enums.EnumRole;
import enums.EstadosTicket;
import model.Cliente;
import model.Empleado;
import model.Mensaje;
import model.Ticket;
import model.Usuario;
import negocio.MensajeABM;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		UsuarioABM usuario = new UsuarioABM();
		MensajeABM mensajeABM = new MensajeABM();
		TicketABM ticketABM = new TicketABM();
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Set<Mensaje> mensajes = new HashSet<Mensaje>();
		Set<Mensaje> mensajes2 = new HashSet<Mensaje>();
		Set<Mensaje> mensajes3 = new HashSet<Mensaje>();
		Random rand = new Random();
		
		usuario.agregarEmpleado(new Empleado("kevin@hotmail.com","Vittor","Kevin","222222",false,EnumRole.ADMIN));
		usuario.agregarEmpleado(new Empleado("robert@hotmail.com","Asafg","Roberto","34343",false,EnumRole.EMPLEADO));
		List<Empleado> empleados = usuario.traerEmpleado();
		
		usuarios.add(new Usuario("jose@hotmail.com", "Rodriguez", "Jose","10000000",true));
		usuarios.add(new Cliente("hernan@hotmail.com", "Perez", "Hernan","111111",false,"12354577", "Jose c paz"));
		usuarios.add(new Cliente("leonel@hotmail.com", "Aguirre", "Leonel","rodkkkk",false,"12345678","Bernal"));
		

		Thread.sleep(500);
		mensajes.add(new Mensaje("Tuve problemas con el ticket", LocalDateTime.now(), usuarios.get(0)));
		Thread.sleep(500);
		mensajes.add(new Mensaje("No estas resolviendo mi duda", LocalDateTime.now(), usuarios.get(0)));
		Thread.sleep(500);
		mensajes.add(new Mensaje("Entendido", LocalDateTime.now(), usuarios.get(0)));
		Thread.sleep(500);
		mensajes.add(new Mensaje("Muchas gracias !!", LocalDateTime.now(), usuarios.get(0)));
		
		
		Thread.sleep(500);
		mensajes2.add(new Mensaje("Tuve problemas con el ticket", LocalDateTime.now(), usuarios.get(1)));
		Thread.sleep(500);
		mensajes2.add(new Mensaje("Ok esperare", LocalDateTime.now(), usuarios.get(1)));
		
		Thread.sleep(500);
		mensajes3.add(new Mensaje("Tengo problemas con el producto", LocalDateTime.now(), usuarios.get(2)));
		Thread.sleep(500);
		mensajes3.add(new Mensaje("Tienen que gestionar mi ticket?", LocalDateTime.now(), usuarios.get(2)));
		
		tickets.add(new Ticket(usuarios.get(0),"Incovenientes",EstadosTicket.CERRADO));
		tickets.add(new Ticket(usuarios.get(1),"Incovenientes",EstadosTicket.ATENDIDO));
		tickets.add(new Ticket(usuarios.get(2),"Incovenientes",EstadosTicket.PENDIENTE));
		
		for(Usuario u: usuarios) {
			usuario.agregar(u);
		}
		
		
		for(Ticket t: tickets) {
			long indice = rand.nextLong(1L,usuario.traerEmpleado().size());
			t.agregarEmpleado((Empleado)usuario.traer(indice));
			ticketABM.agregar(t);
			System.out.println(t);
		}
		
		persistenciaMensaje(mensajes, mensajeABM, tickets.get(0));
		persistenciaMensaje(mensajes2, mensajeABM, tickets.get(1));
		persistenciaMensaje(mensajes3, mensajeABM, tickets.get(2));
		
	}
	
	public static void persistenciaMensaje(Set<Mensaje> mensajes, MensajeABM mensajeABM,Ticket ticket) {
		for(Mensaje m: mensajes) {
		    ticket.agregarMensaje(m);
			mensajeABM.agregar(m);
		}
	}
}
