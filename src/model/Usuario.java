package model;

import java.util.Objects;

import dto.UsuarioDTO;

public class Usuario {
	
	protected Long id;
	protected String email;
	protected String apellido;
	protected String nombre;
	protected String password;
	protected boolean deleted;
	
	public Usuario() {
	}
	
	public Usuario(String email, String apellido, String nombre,String password,boolean deleted) {
		this.email = email;
		this.apellido = apellido;
		this.nombre = nombre;
		this.password = password;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public UsuarioDTO usuarioToDto() {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(this.id);
		usuarioDto.setApellido(this.apellido);
		usuarioDto.setNombre(this.nombre);
		usuarioDto.setEmail(this.email);
		return usuarioDto;
	}
	
	public Cliente crearCuenta(String numTelefono, String direccion) {

		if (this.isDeleted()) {
			System.out.println("El usuario ha sido eliminado, no podemos crear la cuenta");
			return null;
		}
		Cliente cliente = new Cliente(this.email, this.apellido, this.nombre, this.password, this.deleted);
		cliente.setTelefono(numTelefono);
		cliente.setDireccion(direccion);
		return cliente;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", apellido=" + apellido + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, deleted, email, id, nombre, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellido, other.apellido) && deleted == other.deleted
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password);
	}
	
	
}
