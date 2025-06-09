package oo2.grupo19.SistemaTickets.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class TicketClientDTO {
    private Long id;
	private String asunto;
	private String detalle;
	private String fechaHoraCreado;
	private List<IntervencionDTO> intervenciones;
	private String estado;
	private String usuarioNombre;
	private String usuarioApellido;
}
