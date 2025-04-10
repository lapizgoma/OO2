package model;

import java.util.List;

public class Sistema {
	
	private List<Empleado> empleados;
	private List<Ticket> tickets;
	private Dashboard dashboard;
	
	public Sistema() {
	}
	
	public Sistema(List<Empleado> empleados, List<Ticket> tickets, Dashboard dashboard) {
		this.empleados = empleados;
		this.tickets = tickets;
		this.dashboard = dashboard;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	@Override
	public String toString() {
		return "Sistema [empleados=" + empleados + ", tickets=" + tickets + ", dashboard=" + dashboard + "]";
	}
	
}
