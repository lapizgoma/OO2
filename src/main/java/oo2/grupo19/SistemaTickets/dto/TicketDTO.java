package oo2.grupo19.SistemaTickets.dto;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(hidden = true)
	private Long id;
	private String asunto;
	private String detalle;
	@Schema(hidden = true)
	private String fechaHoraCreado;
	@Schema(hidden = true)
	private Set<IntervencionDTO> intervenciones = new TreeSet<>(Comparator.comparing(IntervencionDTO::getId)).reversed();
	@Schema(hidden = true)
	private EstadoTicketDTO estado;
	private String clienteEmail;

	@Schema(hidden = true)
	public IntervencionDTO getUltimaIntervencion() {
		if (intervenciones == null || intervenciones.isEmpty()) {
			return null;
		}

		return intervenciones.stream()
				.max(Comparator.comparing(IntervencionDTO::getId)) // o el campo que uses
				.orElse(null);
	}
}
