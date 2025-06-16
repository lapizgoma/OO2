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
public class TicketEmployeeDTO {
	private Long id;
	private String asunto;
	private String detalle;
	private String fechaHoraCreado;
	private Set<IntervencionDTO> intervenciones;
	private Set<EmpleadoDTO> empleados;
	private EstadoTicketDTO estado;
	private PrioridadDTO prioridad;
	private ClienteDTO cliente;
}
