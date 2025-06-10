package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Intervencion;

public final class IntervencioMapper {
    private IntervencioMapper() {}

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
    }

    public static List<IntervencionDTO> mapToIntervencionDtoList(List<Intervencion> intervenciones) {
        return intervenciones == null ? List.of() :
            intervenciones.stream().map(IntervencioMapper::mapToIntervencionDto).collect(Collectors.toList());
    }

    public static List<Intervencion> mapToIntervencionEntityList(List<IntervencionDTO> dtos) {
        return dtos == null ? List.of() :
            dtos.stream().map(IntervencioMapper::mapToIntervencionEntity).collect(Collectors.toList());
    }
}
