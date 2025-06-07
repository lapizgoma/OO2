package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.format.DateTimeFormatter;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Intervencion;

public class IntervencioMapper {
    public static IntervencionDTO mapToIntervencionDto(Intervencion intervencion) {
        if (intervencion == null) {
            return null;
        }

        IntervencionDTO dto = new IntervencionDTO();

        dto.setId (intervencion.getId ());
        dto.setContenido (intervencion.getDescripcion ()); // Assuming contenido is equivalent to descripcion
        dto.setRealizadoPor (EmpleadoMapper.mapToEmpleadoIntervencionDto (intervencion.getRealizadoPor ()));
        dto.setFecha (intervencion.getFecha ().format (DateTimeFormatter.ofPattern ("dd/MM/yyyy HH:mm")));
        dto.setDescripcion (intervencion.getDescripcion ());
        dto.setEstado (intervencion.getEstado ().toString ());

        return dto;
    }
}
