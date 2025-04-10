package model;

import java.util.List;

public class Dashboard {
	private Long id;
	private Empleado empleado;
	private List<Ticket> tickets;
	
	public Dashboard() {
	}
	
	public Dashboard(Empleado empleado, List<Ticket> ticket) {
		this.empleado = empleado;
		this.tickets = ticket;
	}

	public Long getId() {
		return id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setTickets(List<Ticket> ticket) {
		this.tickets = ticket;
	}

	@Override
	public String toString() {
		return "Dashboard [id=" + id + ", empleado=" + empleado + ", ticket=" + tickets + "]";
	}
	
}
