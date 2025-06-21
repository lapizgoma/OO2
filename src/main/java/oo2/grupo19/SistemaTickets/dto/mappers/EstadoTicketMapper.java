package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;

public class EstadoTicketMapper {

    public static EstadoTicketDTO mapEstadoTicketToDto(EstadoTicket estado) {
        EstadoTicketDTO dto = new EstadoTicketDTO();
        dto.setEstado(estado.getEstado());
        return dto;
    }

    public static EstadoTicket mapDtoToEstadoTicket(EstadoTicketDTO dto) {
        if (dto == null) {
            return null;
        }
        EstadoTicket estado = new EstadoTicket();
        estado.setEstado(dto.getEstado());
        return estado;
    }

    public static Set<EstadoTicketDTO> mapToEstadoTicketDtoSet(List<EstadoTicket> entities) {
        return entities == null ? Set.of() : entities.stream()
                .map(EstadoTicketMapper::mapEstadoTicketToDto)
                .collect(Collectors.toSet());
    }

    public static Set<EstadoTicket> mapToEstadoTicketEntitySet(List<EstadoTicketDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream()
                .map(EstadoTicketMapper::mapDtoToEstadoTicket)
                .collect(Collectors.toSet());
    }
}
