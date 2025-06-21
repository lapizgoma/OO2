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
public class TicketDTO {
	private Long id;
	private String asunto;
	private String detalle;
	private String fechaHoraCreado;
	private Set<IntervencionDTO> intervenciones = new TreeSet<>(Comparator.comparing(IntervencionDTO::getId)).reversed();
	private EstadoTicketDTO estado;
	private String clienteEmail;

	public IntervencionDTO getUltimaIntervencion() {
		if (intervenciones == null || intervenciones.isEmpty()) {
			return null;
		}

		return intervenciones.stream()
				.max(Comparator.comparing(IntervencionDTO::getId)) // o el campo que uses
				.orElse(null);
	}
}
