package model;

import java.time.LocalDateTime;

import dto.EmpleadoDTO;
import enums.EnumRole;

public class Empleado extends Usuario {
	
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private EnumRole role;
	
	public Empleado() {
		super();
	}
	public Empleado(String email, String apellido, String nombre, String password, boolean deleted) {
		super(email, apellido, nombre, password, deleted);
	}
	
	public Empleado(String email, String apellido, String nombre, String password, boolean deleted, EnumRole role) {
		super(email, apellido, nombre, password, deleted);
		this.createdAt = LocalDateTime.now();
		this.role = role;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	
	public EnumRole getRole() {
		return role;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	
	public void setRole(EnumRole role) {
		this.role = role;
	}
	
	public EmpleadoDTO empleadoToDto() {
		EmpleadoDTO empleadoDto = new EmpleadoDTO();
		empleadoDto.setId(this.id);
		empleadoDto.setNombre(this.nombre);
		empleadoDto.setRole(this.role.toString());
		return empleadoDto;
	}
	
	
	@Override
	public String toString() {
		return "Empleado [createdAt=" + createdAt + ", updateAt=" + updateAt + ", role=" + role + ", id=" + id
				+ ", email=" + email + ", apellido=" + apellido + ", nombre=" + nombre + ", password=" + password + "]";
	}
	
	
	
	
	

}
