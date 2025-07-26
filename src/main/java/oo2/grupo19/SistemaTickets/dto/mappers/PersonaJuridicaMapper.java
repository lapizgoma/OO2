package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.controllers.apirest.dto.PersonaJuridicaCreateRequestRecord;
import oo2.grupo19.SistemaTickets.controllers.apirest.dto.PersonaJuridicaResponseRecord;
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

        PersonaJuridicaDTO dto = new PersonaJuridicaDTO ();
        dto.setRazonSocial (personaJuridica.getRazonSocial ());
        dto.setCuit (personaJuridica.getCuit ());
        dto.setCodigoAcceso (personaJuridica.getCodigoAcceso ());

        return dto;
    }

    public static PersonaJuridica mapToPersonaJuridicaEntity(PersonaJuridicaDTO personaJuridicaDTO, PersonaJuridica personaJuridica)
    {
        if (personaJuridicaDTO == null)
        {
            return null;
        }
        else if (personaJuridica == null) 
        {
            personaJuridica = new PersonaJuridica ();
        }

        personaJuridica.setRazonSocial (personaJuridicaDTO.getRazonSocial ());
        personaJuridica.setCuit (personaJuridicaDTO.getCuit ());
        personaJuridica.setCodigoAcceso (personaJuridicaDTO.getCodigoAcceso ());

        return personaJuridica;
    }

    public static PersonaJuridica mapToPersonaJuridicaEntity(PersonaJuridicaResponseRecord personaJuridicaRecord, PersonaJuridica personaJuridica)
    {
        if (personaJuridicaRecord == null)
        {
            return null;
        }
        else if (personaJuridica == null) 
        {
            personaJuridica = new PersonaJuridica ();
        }

        personaJuridica.setRazonSocial (personaJuridicaRecord.razonSocial ());
        personaJuridica.setCuit (personaJuridicaRecord.CUIT ());
        personaJuridica.setCodigoAcceso (personaJuridicaRecord.codigo ());

        return personaJuridica;
    }

    public static PersonaJuridicaDTO mapToPersonaJuridicaDto(PersonaJuridicaCreateRequestRecord personaJuridicaRecord)
    {
        if (personaJuridicaRecord == null)
        {
            return null;
        }

        PersonaJuridicaDTO dto = new PersonaJuridicaDTO ();
        dto.setRazonSocial (personaJuridicaRecord.razonSocial ());
        dto.setCuit (personaJuridicaRecord.CUIT ());

        return dto;
    }

    public static PersonaJuridicaResponseRecord mapToPersonaJuridicaResponseRecord(PersonaJuridica entity)
    {
        if (entity == null)
        {
            return null;
        }

        return new PersonaJuridicaResponseRecord (entity.getRazonSocial (), entity.getCuit (), entity.getCodigoAcceso ());
    }

    public static PersonaJuridicaResponseRecord mapToPersonaJuridicaDtoResponseRecord(PersonaJuridicaDTO dto)
    {
        if (dto == null)
        {
            return null;
        }

        return new PersonaJuridicaResponseRecord (dto.getRazonSocial (), dto.getCuit (), dto.getCodigoAcceso ());
    }

    public static Set<PersonaJuridicaDTO> mapToPersonaJuridicaDtoSet(List<PersonaJuridica> entities)
    {
        return entities == null ? Set.of() : entities.stream().map(PersonaJuridicaMapper::mapToPersonaJuridicaDto).collect(Collectors.toSet());
    }
}
