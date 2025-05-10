package oo2.grupo19.SistemaTickets.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class TicketDTO {
    private Long id;
	private String asunto;
	private List<EmpleadoDTO> empleados;
	private List<IntervencionDTO> intervencion;
	private String estado;
	private UsuarioDTO cliente;
}
