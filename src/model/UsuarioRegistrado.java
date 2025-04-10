package model;

public class UsuarioRegistrado extends Usuario {
	
	private String password;

	public UsuarioRegistrado() {
	}
	
	public UsuarioRegistrado(String email, String apellido, String nombre,String password) {
		super(email, apellido, nombre);
		this.password = password;
	}
	
	public UsuarioRegistrado(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "UsuarioRegistrado [password=" + password + "]";
	}

	
}
