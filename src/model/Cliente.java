package model;

public class Cliente extends Usuario {
	private String telefono;
	private String direccion;
	
	public Cliente() {
		super();
	}
	public Cliente(String email, String apellido, String nombre, String password, boolean deleted) {
		super(email, apellido, nombre, password, deleted);
	}
	
	public Cliente(String email, String apellido, String nombre, String password, boolean deleted, String telefono, String direccion) {
		super(email, apellido, nombre, password, deleted);
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Cliente [telefono=" + telefono + ", direccion=" + direccion + "]";
	}
	
	
}
