package oo2.grupo19.SistemaTickets.dto.mappers;

import oo2.grupo19.SistemaTickets.dto.ContactoDTO;
import oo2.grupo19.SistemaTickets.entities.Contacto;

public class ContactoMapper {
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

}
