package model;

public class Empleado extends UsuarioRegistrado {

	public Empleado() {
	}
	
	public Empleado(String password) {
		super(password);
	}
	
	
	public Empleado(String email, String apellido, String nombre, String password) {
		super(email, apellido, nombre, password);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
