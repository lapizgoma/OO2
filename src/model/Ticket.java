package model;

import java.util.HashSet;
import java.util.Set;

import enums.EstadosTicket;

public class Ticket {
	private Long id;
	private Usuario cliente;
	private String asunto;
	// Relacion bidireccional con mensaje
	private Set<Mensaje> chats;
	private Set<Empleado> lstEmpleado;
	private EstadosTicket estado;
	
	public Ticket() {
	}

	public Ticket(Usuario cliente, String asunto,
			EstadosTicket estado) {
		this.cliente = cliente;
		this.asunto = asunto;
		this.estado = estado;
		this.chats = new HashSet<Mensaje>();
		this.lstEmpleado = new HashSet<Empleado>();
	}

	public Long getId() {
		return id;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public String getAsunto() {
		return asunto;
	}

	public Set<Mensaje> getChats() {
		return chats;
	}

	public Set<Empleado> getLstEmpleado() {
		return lstEmpleado;
	}

	public EstadosTicket getEstado() {
		return estado;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void setChats(Set<Mensaje> chats) {
		this.chats = chats;
	}

	public void setLstEmpleado(Set<Empleado> lstEmpleado) {
		this.lstEmpleado = lstEmpleado;
	}

	public void setEstado(EstadosTicket estado) {
		this.estado = estado;
	}
	
	public void agregarEmpleado(Empleado empleado) {
	    if (lstEmpleado == null) {
	        lstEmpleado = new HashSet<>();
	    }
	    lstEmpleado.add(empleado);
	}
	
	public void agregarMensaje(Mensaje chat) {
	    if (chats == null) {
	    	chats = new HashSet<>();
	    }
	    // Seteamos la relacion bidireccional desde esta clase
	    chats.add(chat);
	    chat.setTicket(this);
	}
	
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", cliente=" + cliente + ", asunto=" + asunto + ", chats=" + chats
				+ ", lstEmpleado=" + lstEmpleado + ", estado=" + estado + "]";
	}

	
}
