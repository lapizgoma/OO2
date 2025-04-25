package model;

import java.time.LocalDateTime;

import dto.MensajeDTO;

public class Mensaje {
	
	private Long id;
	private String contenido;
	private LocalDateTime fecha;
	private Usuario usuario;
	private Ticket ticket;
	
	public Mensaje() {
	}

	public Mensaje(String contenido, LocalDateTime fecha, Usuario sender) {
		this.contenido = contenido;
		this.fecha = fecha;
		this.usuario = sender;
	}

	public Long getId() {
		return id;
	}

	public String getContenido() {
		return contenido;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public MensajeDTO mensajeToDto() {
		MensajeDTO dto = new MensajeDTO();
		dto.setId(this.id);
		dto.setContenido(this.contenido);
		return dto;
	}
	
	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", usuario=" + usuario + "]";
	}


	
}
