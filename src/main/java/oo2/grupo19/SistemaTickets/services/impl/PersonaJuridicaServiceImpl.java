package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolationException;
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
        try
        {
            Long personaJuridicaID = Long.parseLong (id);
            repository.deleteById (personaJuridicaID);
        }
        catch (Exception e)
        {
            throw new NotFoundException ("No se pudo eliminar la Persona Jurídica"+ e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PersonaJuridicaDTO> findAll()
    {
        try
        {
            return PersonaJuridicaMapper.mapToPersonaJuridicaDtoSet (repository.findAll ());
        }
        catch (Exception e)
        {
            throw new NotFoundException ("No se pudo listar las Personas Jurídicas: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findById(Long id)
    {
        try
        {
            return PersonaJuridicaMapper.mapToPersonaJuridicaDto (repository.findById (id).orElseThrow ());
        }
        catch (Exception e)
        {
            throw new NotFoundException ("No se pudo encontrar la Persona Jurídica: " + e.getMessage ());
        }
    }

    /**
     * Este método solo actualiza Personas Jurídicas existentes en base a su CUIT.
     * Para crear una Persona Jurídica, usá {@link #crearPersonaJuridica(PersonaJuridicaDTO)} en su lugar.
     */
    @Override
    @Transactional
    public void save(PersonaJuridicaDTO dto)
    {
        try
        {
            String code = dto.getCodigoAcceso ();

            if (code == null || code.length () != PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH) 
            {
                throw new InvalidInputException ("El código de acceso debe tener " + PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH + " caracteres.");
            }

            PersonaJuridica personaJuridicaEntity = repository.findByCuit (dto.getCuit ()).orElseThrow (() -> new NotFoundException ("No hay una Persona Jurídica bajo ese CUIT :/"));

            repository.save(PersonaJuridicaMapper.mapToPersonaJuridicaEntity (dto, personaJuridicaEntity));
        }
        catch (ConstraintViolationException e)
        {
            throw new InvalidInputException ("ERROR al guardar Persona Jurídica" + e.getMessage ());
        }
        catch (Exception e)
        {
            throw new RuntimeException (e.getMessage());
        }
    }

    @Override
    @Transactional
    public PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO)
    {
        if (!repository.findByCuit (personaJuridicaDTO.getCuit ()).isEmpty ()) 
        {
            throw new AlreadyExistsException ("Persona Jurídica bajo ese CUIT ya se encuentra registrada.");
        }

        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH);

        personaJuridicaDTO.setCodigoAcceso (codigoAcceso);

        PersonaJuridica personaJuridicaEntity = PersonaJuridicaMapper.mapToPersonaJuridicaEntity (personaJuridicaDTO, new PersonaJuridica ());
        
        try 
        {
            repository.save (personaJuridicaEntity);
        }
        // En vez de checkear las restricciones de la Entity por duplicado,
        // redireccionamos las Excepciones a nuestro Handler global.
        catch (ConstraintViolationException e)
        {
            throw new InvalidInputException ("ERROR al crear Persona Jurídica" + e.getMessage ());
        }

        return personaJuridicaDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findByCode (String code) 
    {
        if (code == null || code.length () != PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH) 
        {
            throw new InvalidInputException("El código de acceso debe tener " + PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH + " caracteres.");
        }

        PersonaJuridica personaJuridicaEntity = repository.findByCodigoAcceso(code).orElseThrow(() -> new NotFoundException ("No hay una Persona Jurídica con ese código :/"));
        
        return PersonaJuridicaMapper.mapToPersonaJuridicaDto(personaJuridicaEntity);
    }

}
