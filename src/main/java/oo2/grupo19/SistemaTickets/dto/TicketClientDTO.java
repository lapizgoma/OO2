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
public class TicketClientDTO {
	private Long id;
	private String asunto;
	private String detalle;
	private String fechaHoraCreado;
	private Set<IntervencionDTO> intervenciones;
	private String estado;
	private String usuarioNombre;
	private String usuarioApellido;
}
