package model;

public class Usuario {
	
	protected String email;
	protected String apellido;
	protected String nombre;
	
	public Usuario() {
	}
	
	public Usuario(String email, String apellido, String nombre) {
		this.email = email;
		this.apellido = apellido;
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", apellido=" + apellido + ", nombre=" + nombre + "]";
	}
	
}
