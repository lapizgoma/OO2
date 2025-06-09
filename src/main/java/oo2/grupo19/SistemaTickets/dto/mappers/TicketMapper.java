package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public class TicketMapper {
    public static TicketEmployeeDTO mapToTicketEmployeeDto(Ticket ticket, Empleado empleadoSolicitante) {
        if (ticket == null) {
            return null;
        }

        TicketEmployeeDTO dto = new TicketEmployeeDTO ();

        dto.setId (ticket.getId());
        dto.setAsunto (ticket.getAsunto());
        dto.setDetalle (ticket.getDetalle ());
        dto.setFechaHoraCreado (ticket.getFechaHora ().format (DateTimeFormatter.ofPattern ("dd/MM/yyyy HH:mm")));
        List<EmpleadoDTO> empleados = new ArrayList<>();
        if (ticket.getListEmpleado () != null)
        {
            for (Empleado empleado : ticket.getListEmpleado ()) 
            {
                empleados.add(EmpleadoMapper.mapToEmpleadoDto(empleado));
            }
        }
        dto.setListEmpleados (empleados);

        if (ticket.usuarioPertenece(empleadoSolicitante)) 
        {
            dto.setEmpleadoPertenece(true);
        }

        List<IntervencionDTO> intervenciones = new ArrayList<>();
        if (ticket.getLstIntervencion () != null) 
        {
            for (Intervencion intervencion : ticket.getLstIntervencion()) 
            {
                intervenciones.add(IntervencioMapper.mapToIntervencionDto(intervencion));
            }
        }
        dto.setIntervenciones (intervenciones);

        dto.setEstado (ticket.getEstado ().getEstado ());
        dto.setPrioridad (ticket.getPrioridad());
        // Datos del cliente
        dto.setUsuarioNombre (ticket.getCreadoPor ().getNombre ());
        dto.setUsuarioApellido (ticket.getCreadoPor ().getApellido ());
        dto.setUsuarioContactoDTO (ContactoMapper.mapToContactoDto (ticket.getCreadoPor ().getContacto ()));

        return dto;
    }
    
    public static TicketClientDTO mapToTicketClientDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketClientDTO dto = new TicketClientDTO();

        dto.setId (ticket.getId ());
        dto.setAsunto (ticket.getAsunto ());
        dto.setDetalle (ticket.getDetalle ());
        dto.setFechaHoraCreado (ticket.getFechaHora ().format (DateTimeFormatter.ofPattern ("dd/MM/yyyy HH:mm")));

        List<IntervencionDTO> intervenciones = new ArrayList<>();
        if (ticket.getLstIntervencion () != null) {
            for (Intervencion intervencion : ticket.getLstIntervencion()) {
                intervenciones.add(IntervencioMapper.mapToIntervencionDto(intervencion));
            }
        }
        dto.setIntervenciones (intervenciones);

        dto.setEstado (ticket.getEstado ().getEstado ());
        // Datos del cliente
        dto.setUsuarioNombre (ticket.getCreadoPor ().getNombre ());
        dto.setUsuarioApellido (ticket.getCreadoPor ().getApellido ());

        return dto;
    }
}
