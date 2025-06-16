package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.mappers.PersonaJuridicaMapper;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.AlreadyExistsException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.InvalidInputException;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;

@Service
@Log4j2
public class PersonaJuridicaServiceImpl implements IPersonaJuridicaService
{
    private final IPersonaJuridica repository;

    public PersonaJuridicaServiceImpl(IPersonaJuridica repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(String id)
    {
        Long personaJuridicaID = Long.parseLong(id);
        try
        {
            repository.deleteById(personaJuridicaID);
        }
        catch (Exception e)
        {
            throw new NotFoundException("No se pudo eliminar la Persona Jurídica"+ e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PersonaJuridicaDTO> findAll()
    {
        try
        {
            return PersonaJuridicaMapper.mapToPersonaJuridicaDtoSet (repository.findAll());
        }
        catch (Exception e)
        {
            throw new NotFoundException("No se pudo listar las Personas Jurídicas: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findById(Long id)
    {
        try
        {
            return PersonaJuridicaMapper.mapToPersonaJuridicaDto(repository.findById(id).orElseThrow());
        }
        catch (Exception e)
        {
            throw new NotFoundException("No se pudo encontrar la Persona Jurídica: " + e.getMessage());
        }
    }

    /**
     * @deprecated Este método no conviene usarlo
     *             Usá {@link #crearPersonaJuridica(PersonaJuridicaDTO)} en su lugar.
     */
    @Override
    @Deprecated
    public void save(PersonaJuridicaDTO object)
    {
        try
        {
            repository.save(PersonaJuridicaMapper.mapToPersonaJuridicaEntity(object, new PersonaJuridica ()));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO)
    {
        if (!repository.findByCuit(personaJuridicaDTO.getCuit()).isEmpty()) 
        {
            throw new AlreadyExistsException("Persona Jurídica bajo ese CUIT ya se encuentra registrada.");
        }

        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, PersonaJuridica.CODIGO_ACCESO_LENGTH);

        personaJuridicaDTO.setCodigoAcceso (codigoAcceso);

        PersonaJuridica personaJuridicaEntity = PersonaJuridicaMapper.mapToPersonaJuridicaEntity (personaJuridicaDTO, new PersonaJuridica ());
        repository.save (personaJuridicaEntity);

        return personaJuridicaDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findByCode (String code) 
    {
        // log.info("Buscando Persona Jurídica con código {}", code);
        if (code == null || code.length () != PersonaJuridica.CODIGO_ACCESO_LENGTH) 
        {
            throw new InvalidInputException("El código de acceso debe tener " + PersonaJuridica.CODIGO_ACCESO_LENGTH + " caracteres");
        }

        PersonaJuridica personaJuridicaEntity = repository.findByCodigoAcceso(code).orElseThrow(() -> new NotFoundException ("No hay una Persona Jurídica bajo con ese código :/"));
        
        return PersonaJuridicaMapper.mapToPersonaJuridicaDto(personaJuridicaEntity);
    }
    
}
