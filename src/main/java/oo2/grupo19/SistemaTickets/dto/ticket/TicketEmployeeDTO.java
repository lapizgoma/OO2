package oo2.grupo19.SistemaTickets.dto.ticket;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oo2.grupo19.SistemaTickets.dto.ContactoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class TicketEmployeeDTO extends TicketDTO 
{
    private String prioridad;
    private ContactoDTO contactoClienteDTO;
    protected Set<EmpleadoDTO> empleados;
    protected String nombreCliente;
    protected String apellidoCliente;
    @JsonIgnore
    private boolean empleadoPertenece;
}