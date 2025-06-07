package oo2.grupo19.SistemaTickets.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;

@NoArgsConstructor @Setter @Getter
public class TicketClientDTO {
    private Long id;
	private String asunto;
	private String fechaHoraCreado;
	private List<IntervencionDTO> intervenciones;
	private EstadoTicket estado;
	private String usuarioNombre;
	private String usuarioApellido;
}
