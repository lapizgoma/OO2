package dto;

import model.Mensaje;

public class MensajeDTO {
	private Long id;
	private String contenido;
	
	public MensajeDTO() {
	}

	public MensajeDTO(Long id, String contenido) {
		this.id = id;
		this.contenido = contenido;
	}

	public Long getId() {
		return id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	@Override
	public String toString() {
		return "MensajeDTO [id=" + id + ", contenido=" + contenido + "]";
	}
	
}
