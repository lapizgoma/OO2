package oo2.grupo19.SistemaTickets.dto;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

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
	private Set<IntervencionDTO> intervenciones = new TreeSet<>(Comparator.comparing(IntervencionDTO::getId)).reversed();
	private Set<EmpleadoDTO> empleados;
	private EstadoTicketDTO estado;
	private PrioridadDTO prioridad;
	private ClienteDTO cliente;
	/*
	 * Esta flag no se tiene que setear en el Mapper, el Service debe hacerlo por su lado.
	 */
	private Boolean estaAsignado;

	public IntervencionDTO getUltimaIntervencion() {
		if (intervenciones == null || intervenciones.isEmpty()) {
			return null;
		}

		return intervenciones.stream()
				.max(Comparator.comparing(IntervencionDTO::getId)) // o el campo que uses
				.orElse(null);
	}
}
