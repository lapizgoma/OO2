package dto;

public class EmpleadoDTO {
	private Long id;
	private String nombre;
	private String role;

	public EmpleadoDTO() {
	}
	
	public EmpleadoDTO(String nombre, String role) {
		this.nombre = nombre;
		this.role = role;
	}

	
	public String getNombre() {
		return nombre;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "EmpleadoDTO [nombre=" + nombre + ", role=" + role + "]";
	}
}
