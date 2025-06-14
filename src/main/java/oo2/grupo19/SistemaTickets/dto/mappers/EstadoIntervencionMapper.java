package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.EstadoIntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;

public class EstadoIntervencionMapper {

    public static EstadoIntervencionDTO mapEstadoIntervencionToDto(EstadoIntervencion estado) {
        EstadoIntervencionDTO dto = new EstadoIntervencionDTO();
        dto.setEstado(estado.getEstado());
        return dto;
    }

    public static EstadoIntervencion mapDtoToEstadoIntervencion(EstadoIntervencionDTO dto) {
        if (dto == null) {
            return null;
        }
        EstadoIntervencion estado = new EstadoIntervencion();
        estado.setEstado(dto.getEstado());
        return estado;
    }

    public static Set<EstadoIntervencionDTO> mapToEstadoIntervencionDtoList(List<EstadoIntervencion> entities) {
        return entities == null ? Set.of() : entities.stream()
                .map(EstadoIntervencionMapper::mapEstadoIntervencionToDto)
                .collect(Collectors.toSet());
    }
    public static Set<EstadoIntervencion> mapToEstadoIntervencionEntityList(List<EstadoIntervencionDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream()
                .map(EstadoIntervencionMapper::mapDtoToEstadoIntervencion)
                .collect(Collectors.toSet());
    }
}
