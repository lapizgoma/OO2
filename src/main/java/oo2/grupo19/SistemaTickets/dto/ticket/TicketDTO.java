package oo2.grupo19.SistemaTickets.dto.ticket;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TicketDTO 
{
    protected Long id;
    protected String asunto;
    protected String detalle;
    protected String fechaHoraCreado;
    protected Set<IntervencionDTO> intervenciones;
    protected String estado;
}