package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.EstadosTicket;
import model.Dashboard;
import model.Empleado;
import model.Mensaje;
import model.Ticket;
import model.Usuario;
import model.UsuarioRegistrado;
import negocio.DashboardABM;
import negocio.MensajeABM;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class Test {
	public static void main(String[] args) {
		UsuarioABM usuario = new UsuarioABM();
		MensajeABM mensajeABM = new MensajeABM();
		DashboardABM dashboardABM = new DashboardABM();
		TicketABM ticketABM = new TicketABM();
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		List<Mensaje> mensajes2 = new ArrayList<Mensaje>();
		List<Mensaje> mensajes3 = new ArrayList<Mensaje>();
		
		usuario.agregar(new Empleado("kevin@hotmail.com","Vittor","Kevin","222222"));
		
		usuarios.add(new Usuario("jose@hotmail.com", "Rodriguez", "Jose"));
		usuarios.add(new UsuarioRegistrado("hernan@hotmail.com", "Perez", "Hernan","111111"));
		usuarios.add(new Usuario("leonel@hotmail.com", "Aguirre", "Leonel"));
		
		mensajes.add(new Mensaje("Tuve problemas con el ticket", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("No estas resolviendo mi duda", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("Entendido", LocalDate.now(), usuarios.get(0)));
		mensajes.add(new Mensaje("Muchas gracias !!", LocalDate.now(), usuarios.get(0)));
		
		mensajes2.add(new Mensaje("Tuve problemas con el ticket", LocalDate.now(), usuarios.get(1)));
		mensajes2.add(new Mensaje("Ok esperare", LocalDate.now(), usuarios.get(1)));
		
		mensajes2.add(new Mensaje("Tengo problemas con el producto", LocalDate.now(), usuarios.get(1)));
		mensajes2.add(new Mensaje("Tienen que gestionar mi ticket?", LocalDate.now(), usuarios.get(1)));
		
		tickets.add(new Ticket(usuarios.get(0),"Incovenientes",mensajes,EstadosTicket.CERRADO));
		tickets.add(new Ticket(usuarios.get(1),"Incovenientes",mensajes,EstadosTicket.ATENDIDO));
		tickets.add(new Ticket(usuarios.get(2),"Incovenientes",mensajes,EstadosTicket.PENDIENTE));
		
		Empleado empleado = usuario.traerEmpleado().get(0);
		
		Dashboard dashboard = new Dashboard(empleado,tickets);
		
		for(Usuario u: usuarios) {
			usuario.agregar(u);
		}
		
		for(Mensaje m: mensajes) {
			mensajeABM.agregar(m);
		}
		for(Mensaje m: mensajes2) {
			mensajeABM.agregar(m);
		}
		for(Mensaje m: mensajes3) {
			mensajeABM.agregar(m);
		}
		
		for(Ticket t: tickets) {
			ticketABM.agregar(t);
		}
		dashboardABM.agregar(dashboard);
		
	}
}
