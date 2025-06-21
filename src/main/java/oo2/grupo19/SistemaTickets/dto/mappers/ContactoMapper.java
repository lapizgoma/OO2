package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.ContactoDTO;
import oo2.grupo19.SistemaTickets.entities.Contacto;

public final class ContactoMapper {
    public static ContactoDTO mapToContactoDto(Contacto contacto) {
        if (contacto == null) {
            return null;
        }
        ContactoDTO dto = new ContactoDTO();
        dto.setEmail(contacto.getEmail());
        dto.setTelefono(contacto.getTelefono());
        dto.setCalle(contacto.getCalle());
        dto.setNroPuerta(contacto.getNroPuerta());
        dto.setLocalidad(contacto.getLocalidad());
        return dto;
    }

    public static Contacto mapToContactoEntity(ContactoDTO dto, Contacto contacto) {
        if (dto == null) return null;
        contacto.setEmail(dto.getEmail());
        contacto.setTelefono(dto.getTelefono());
        contacto.setCalle(dto.getCalle());
        contacto.setNroPuerta(dto.getNroPuerta());
        contacto.setLocalidad(dto.getLocalidad());
        return contacto;
    }

    public static Set<ContactoDTO> mapToContactoDtoSet(List<Contacto> contactos) {
        return contactos == null ? Set.of() :
            contactos.stream().map(ContactoMapper::mapToContactoDto).collect(Collectors.toSet());
    }
}
