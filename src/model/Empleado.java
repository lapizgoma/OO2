package model;

public class Empleado extends Usuario {
	
	private String legajo;
	
	public Empleado() {
	}
	
	public Empleado(String email, String apellido, String nombre, String password,String legajo) {
		super(email, apellido, nombre, password);
		this.legajo = legajo;
	}
	
	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}


}
