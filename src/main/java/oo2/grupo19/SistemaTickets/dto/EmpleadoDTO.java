package oo2.grupo19.SistemaTickets.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmpleadoDTO {
	private String nombre;
	private String dni;
	private String nroLegajo;
	private String apellido;
	private String password;
	private ContactoDTO contacto;
	private String role;

	public EmpleadoDTO(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}

}
