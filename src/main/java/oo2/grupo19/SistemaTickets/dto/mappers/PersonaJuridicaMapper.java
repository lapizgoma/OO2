package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public final class PersonaJuridicaMapper
{
    public static PersonaJuridicaDTO mapToPersonaJuridicaDto(PersonaJuridica personaJuridica)
    {
        if (personaJuridica == null)
        {
            return null;
        }

        PersonaJuridicaDTO dto = new PersonaJuridicaDTO();

        dto.setRazonSocial(personaJuridica.getRazonSocial());
        dto.setCuit(personaJuridica.getCuit());
        dto.setCodigoAcceso(personaJuridica.getCodigoAcceso());

        return dto;
    }

    public static PersonaJuridica mapToPersonaJuridica(PersonaJuridicaDTO personaJuridicaDTO)
    {
        if (personaJuridicaDTO == null)
        {
            return null;
        }

        PersonaJuridica personaJuridica = new PersonaJuridica();

        personaJuridica.setRazonSocial(personaJuridicaDTO.getRazonSocial());
        personaJuridica.setCuit(personaJuridicaDTO.getCuit());
        personaJuridica.setCodigoAcceso(personaJuridicaDTO.getCodigoAcceso());

        return personaJuridica;
    }

    public static List<PersonaJuridicaDTO> mapToPersonaJuridicaDtoList(List<PersonaJuridica> entities)
    {
        return entities == null ? List.of() : entities.stream().map(PersonaJuridicaMapper::mapToPersonaJuridicaDto).collect(Collectors.toList());
    }

    public static List<PersonaJuridica> mapToPersonaJuridicaEntityList(List<PersonaJuridicaDTO> dtos)
    {
        return dtos == null ? List.of() : dtos.stream().map(PersonaJuridicaMapper::mapToPersonaJuridica).collect(Collectors.toList());
    }
}
