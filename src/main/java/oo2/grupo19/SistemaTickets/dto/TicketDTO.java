package oo2.grupo19.SistemaTickets.dto;

import java.util.Set;

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
public class TicketDTO {
    private Long id;
	private String asunto;
	private Set<EmpleadoDTO> empleados;
	private Set<IntervencionDTO> intervencion;
	private String estado;
	private UsuarioDTO cliente;
}
