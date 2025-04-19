package model;

import java.time.LocalDate;

public class Mensaje {
	
	private Long id;
	private String contenido;
	private LocalDate fecha;
	private Usuario sender;
	private Ticket ticket;
	
	public Mensaje() {
	}

	public Mensaje(String contenido, LocalDate fecha, Usuario sender) {
		this.contenido = contenido;
		this.fecha = fecha;
		this.sender = sender;
	}

	public Long getId() {
		return id;
	}

	public String getContenido() {
		return contenido;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public Usuario getSender() {
		return sender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setSender(Usuario sender) {
		this.sender = sender;
	}
	
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", sender=" + sender + "]";
	}


	
}
