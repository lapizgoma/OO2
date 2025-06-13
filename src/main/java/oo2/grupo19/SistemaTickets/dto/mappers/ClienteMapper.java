package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public final class ClienteMapper {
    private ClienteMapper() {}

    public static ClienteDTO mapToClienteDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        
        if (cliente.getContacto() != null) {
            dto.setEmail(cliente.getContacto().getEmail());
            dto.setTelefono(cliente.getContacto().getTelefono());
            dto.setDireccionCompleta(
                cliente.getContacto().getCalle() + ", " + 
                cliente.getContacto().getNroPuerta() + ", " + 
                cliente.getContacto().getLocalidad()
            );
        }
        
        dto.setDni(cliente.getDni());
        
        if (cliente.getOrganizacion() != null) {
            dto.setOrganizacion(cliente.getOrganizacion().getRazonSocial());
            dto.setCodigoAcceso(cliente.getOrganizacion().getCodigoAcceso());
        }
        
        return dto;
    }

    public static Cliente mapToClienteEntity(ClienteDTO dto) {
        if (dto == null) return null;
        
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDni(dto.getDni());

        // Mapear contacto
        Contacto contacto = new Contacto();
        contacto.setEmail(dto.getEmail());
        contacto.setTelefono(dto.getTelefono());
        
        // Separar la dirección completa
        String[] direccionParts = dto.getDireccionCompleta().split(",");
        if (direccionParts.length >= 3) {
            contacto.setCalle(direccionParts[0].trim());
            contacto.setNroPuerta(direccionParts[1].trim());
            contacto.setLocalidad(direccionParts[2].trim());
        }
        
        cliente.setContacto(contacto);
        contacto.setUsuario(cliente);

        // Mapear organización si existe
        if (dto.getOrganizacion() != null && !dto.getOrganizacion().isEmpty()) {
            PersonaJuridica org = new PersonaJuridica();
            org.setRazonSocial(dto.getOrganizacion());
            org.setCodigoAcceso(dto.getCodigoAcceso());
            cliente.setOrganizacion(org);
        }

        return cliente;
    }

    public static List<ClienteDTO> mapToClienteDtoList(List<Cliente> clientes) {
        return clientes == null ? List.of() : 
            clientes.stream()
                .map(ClienteMapper::mapToClienteDto)
                .collect(Collectors.toList());
    }

    public static List<Cliente> mapToClienteEntityList(List<ClienteDTO> dtos) {
        return dtos == null ? List.of() : 
            dtos.stream()
                .map(ClienteMapper::mapToClienteEntity)
                .collect(Collectors.toList());
    }
}
