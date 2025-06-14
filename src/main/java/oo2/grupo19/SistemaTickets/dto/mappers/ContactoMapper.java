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
        dto.setId(contacto.getId());
        dto.setEmail(contacto.getEmail());
        dto.setTelefono(contacto.getTelefono());
        dto.setCalle(contacto.getCalle());
        dto.setNroPuerta(contacto.getNroPuerta());
        dto.setLocalidad(contacto.getLocalidad());
        return dto;
    }

    public static ContactoDTO mapToContactoDtoNoId(Contacto contacto) {
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

    public static Contacto mapToContactoEntity(ContactoDTO dto) {
        if (dto == null) return null;
        Contacto contacto = new Contacto();
        contacto.setId(dto.getId());
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

    public static Set<Contacto> mapToContactoEntitySet(List<ContactoDTO> dtos) {
        return dtos == null ? Set.of() :
            dtos.stream().map(ContactoMapper::mapToContactoEntity).collect(Collectors.toSet());
    }
}
