package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public final class PersonaJuridicaMapper {
    public static PersonaJuridicaDTO mapToPersonaJuridicaDto(PersonaJuridica personaJuridica) {
        if (personaJuridica == null) {
            return null;
        }
        PersonaJuridicaDTO dto = new PersonaJuridicaDTO();
        dto.setRazonSocial(personaJuridica.getRazonSocial());
        dto.setCuit(personaJuridica.getCuit());
        dto.setCodigoAcceso(personaJuridica.getCodigoAcceso());
        return dto;
    }

    public static PersonaJuridica mapToPersonaJuridica(PersonaJuridicaDTO personaJuridicaDTO) {
        if (personaJuridicaDTO == null) {
            return null;
        }
        PersonaJuridica personaJuridica = new PersonaJuridica();
        personaJuridica.setRazonSocial(personaJuridicaDTO.getRazonSocial());
        personaJuridica.setCuit(personaJuridicaDTO.getCuit());
        personaJuridica.setCodigoAcceso(personaJuridicaDTO.getCodigoAcceso());
        return personaJuridica;
    }

    public static Set<PersonaJuridicaDTO> mapToPersonaJuridicaDtoSet(List<PersonaJuridica> entities) {
        return entities == null ? Set.of() : entities.stream().map(PersonaJuridicaMapper::mapToPersonaJuridicaDto).collect(Collectors.toSet());
    }

    public static Set<PersonaJuridica> mapToPersonaJuridicaEntitySet(List<PersonaJuridicaDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream().map(PersonaJuridicaMapper::mapToPersonaJuridica).collect(Collectors.toSet());
    }
}
