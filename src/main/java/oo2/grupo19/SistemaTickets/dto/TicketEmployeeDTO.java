package oo2.grupo19.SistemaTickets.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;

@NoArgsConstructor @Setter @Getter
public class TicketEmployeeDTO {
    private Long id;
	private String asunto;
	private String fechaHoraCreado;
	private List<IntervencionDTO> intervenciones;
    private List<EmpleadoDTO> listEmpleados;
	private String estado;
	private Prioridad prioridad;
	private String usuarioNombre;
	private String usuarioApellido;
	private ContactoDTO usuarioContactoDTO;
	private boolean empleadoPertenece;
}
