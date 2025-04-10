package model;

import java.util.List;

import enums.EstadosTicket;

public class Ticket {
	private Long id;
	private Usuario cliente;
	private String asunto;
	private List<Mensaje> chats;
	private EstadosTicket estado;
	
	public Ticket() {
	}

	public Ticket(Usuario cliente, String asunto, List<Mensaje> chats, EstadosTicket estado) {
		this.cliente = cliente;
		this.asunto = asunto;
		this.chats = chats;
		this.estado = estado;
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

	public List<Mensaje> getChats() {
		return chats;
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

	public void setChats(List<Mensaje> chats) {
		this.chats = chats;
	}

	public void setEstado(EstadosTicket estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", cliente=" + cliente + ", asunto=" + asunto + ", chats=" + chats + ", estado="
				+ estado + "]";
	}
	
	
	
}
