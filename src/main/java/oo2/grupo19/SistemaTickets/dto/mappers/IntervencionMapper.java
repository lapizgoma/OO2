package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Intervencion;

public final class IntervencionMapper {
    public static IntervencionDTO mapToIntervencionDto(Intervencion intervencion) {
        if (intervencion == null) {
            return null;
        }
        IntervencionDTO dto = new IntervencionDTO();
        dto.setId(intervencion.getId());
        dto.setDescripcion(intervencion.getDescripcion());
        dto.setEmpleadoEmail(intervencion.getRealizadoPor().getContacto().getEmail());
        dto.setFecha(intervencion.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        dto.setEstado(intervencion.getEstado().getEstado());
        dto.setTicketId(intervencion.getTicket().getId());
        return dto;
    }

    public static Intervencion mapToIntervencionEntity(IntervencionDTO dto, Intervencion intervencion) {
        if (dto == null) return null;
        intervencion.setId(dto.getId());
        intervencion.setDescripcion(dto.getDescripcion());
        intervencion.setFecha(LocalDateTime.parse(dto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        return intervencion;
    }   
    
    public static Set<IntervencionDTO> mapToIntervencionDtoSet(List<Intervencion> intervenciones) {
            return intervenciones == null ? Set.of() : intervenciones.stream().map(IntervencionMapper::mapToIntervencionDto).collect(Collectors.toSet());
    }
}
