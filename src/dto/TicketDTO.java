package dto;

import java.util.HashSet;
import java.util.Set;


public class TicketDTO {
	private Long id;
	private String asunto;
	private Set<EmpleadoDTO> empleados;
	private Set<MensajeDTO> mensajes;
	private String estado;
	private UsuarioDTO cliente;
	
	public TicketDTO() {
	}
	
	public TicketDTO(Long id, String asunto, String estado, UsuarioDTO usuario) {
		this.id = id;
		this.asunto = asunto;
		this.estado = estado;
		this.cliente = usuario;
		this.empleados = new HashSet<EmpleadoDTO>();
		this.mensajes = new HashSet<MensajeDTO>();
	}
	
	

	public Long getId() {
		return id;
	}

	public String getAsunto() {
		return asunto;
	}

	public String getEstado() {
		return estado;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<MensajeDTO> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Set<MensajeDTO> mensajes) {
		this.mensajes = mensajes;
	}
	
	public Set<EmpleadoDTO> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(Set<EmpleadoDTO> empleados) {
		this.empleados = empleados;
	}

	public UsuarioDTO getCliente() {
		return cliente;
	}

	public void setCliente(UsuarioDTO cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "TicketDTO [id=" + id + ", asunto=" + asunto + ", empleados=" + empleados + ", mensajes=" + mensajes
				+ ", estado=" + estado + ", cliente=" + cliente + "]";
	}
	
}
