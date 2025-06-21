package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;

public class PrioridadMapper {
    public static PrioridadDTO mapPrioridadToDto(Prioridad prioridad) {
        PrioridadDTO dto = new PrioridadDTO();
        dto.setPrioridad(prioridad.getPrioridad());
        return dto;
    }

    public static Prioridad mapDtoToPrioridad(PrioridadDTO dto) {
        if (dto == null) {
            return null;
        }
        Prioridad prioridad = new Prioridad();
        prioridad.setPrioridad(dto.getPrioridad()); 
        return prioridad;
    }
    
    public static Set<PrioridadDTO> mapToPrioridadDtoSet(List<Prioridad> entities) {
        return entities == null ? Set.of() : entities.stream()
                .map(PrioridadMapper::mapPrioridadToDto)
                .collect(Collectors.toSet());
    }
    public static Set<Prioridad> mapToPrioridadEntitySet(List<PrioridadDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream()
                .map(PrioridadMapper::mapDtoToPrioridad)
                .collect(Collectors.toSet());
    }
}
