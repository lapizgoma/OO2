package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Intervencion;

public final class IntervencionMapper {
    private IntervencionMapper() {}

    public static IntervencionDTO mapToIntervencionDto(Intervencion intervencion) {
        if (intervencion == null) {
            return null;
        }
        IntervencionDTO dto = new IntervencionDTO();
        dto.setId(intervencion.getId());
        dto.setContenido(intervencion.getDescripcion());
        dto.setDescripcion(intervencion.getDescripcion());
        dto.setRealizadoPor(EmpleadoMapper.mapToEmpleadoIntervencionDto(intervencion.getRealizadoPor()));
        if (intervencion.getFecha() != null) {
            dto.setFecha(intervencion.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        if (intervencion.getEstado() != null) {
            dto.setEstado(intervencion.getEstado().getEstado());
        }
        return dto;
    }

    public static Intervencion mapToIntervencionEntity(IntervencionDTO dto) {
        if (dto == null) return null;
        Intervencion intervencion = new Intervencion();
        intervencion.setId(dto.getId());
        intervencion.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion() : dto.getContenido());
        // Fecha: parse if present
        if (dto.getFecha() != null) {
            try {
                intervencion.setFecha(LocalDateTime.parse(dto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            } catch (Exception ignored) {}
        }
        // Estado y realizadoPor deben ser seteados por el servicio según lógica de negocio
        return intervencion;
    }    public static Set<IntervencionDTO> mapToIntervencionDtoSet(Set<Intervencion> intervenciones) {
        return intervenciones == null ? new HashSet<>() :
            intervenciones.stream().map(IntervencionMapper::mapToIntervencionDto).collect(Collectors.toSet());
    }

    public static Set<Intervencion> mapToIntervencionEntitySet(Set<IntervencionDTO> dtos) {
        return dtos == null ? new HashSet<>() :
            dtos.stream().map(IntervencionMapper::mapToIntervencionEntity).collect(Collectors.toSet());
    }
}
