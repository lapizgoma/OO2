package model;

public class Usuario {
	
	protected Long id;
	protected String email;
	protected String apellido;
	protected String nombre;
	protected String password;
	
	public Usuario() {
	}
	
	public Usuario(String email, String apellido, String nombre,String password) {
		this.email = email;
		this.apellido = apellido;
		this.nombre = nombre;
		this.password = password;
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

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", apellido=" + apellido + ", nombre=" + nombre + "]";
	}
}
