package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;

@Log4j2
public final class ClienteMapper {
    public static ClienteDTO mapToClienteDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setDni(cliente.getDni());
        dto.setContacto(ContactoMapper.mapToContactoDto(cliente.getContacto()));
        dto.setOrganizacion(PersonaJuridicaMapper.mapToPersonaJuridicaDto(cliente.getOrganizacion()));
        return dto;
    }

    public static Cliente mapToClienteEntity(ClienteDTO dto, Cliente cliente) {
        if (dto == null) return null;
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDni(dto.getDni());
        cliente.setPassword(dto.getPassword());
        return cliente;
    }

    public static Set<ClienteDTO> mapToClienteDtoSet(List<Cliente> clientes) {
        return clientes == null ? Set.of() : 
            clientes.stream()
                .map(ClienteMapper::mapToClienteDto)
                .collect(Collectors.toSet());
    }
}
