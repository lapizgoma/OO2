package oo2.grupo19.SistemaTickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class UsuarioDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private String email;
}
