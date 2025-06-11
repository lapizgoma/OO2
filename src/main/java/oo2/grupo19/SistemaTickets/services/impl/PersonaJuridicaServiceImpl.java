package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.PersonaJuridicaMapper;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IPersonaJuridicaService;

@Service
public class PersonaJuridicaServiceImpl implements IPersonaJuridicaService{

    private final IPersonaJuridica repository;

    public PersonaJuridicaServiceImpl(IPersonaJuridica repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new UserCustomExceptions.PersonaJuridicaNotFound("No se pudo eliminar la Persona Jurídica: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaJuridica> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new UserCustomExceptions.PersonaJuridicaNotFound("No se pudo listar las Personas Jurídicas: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonaJuridica> findById(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new UserCustomExceptions.PersonaJuridicaNotFound("No se pudo encontrar la Persona Jurídica: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void save(PersonaJuridica object) {
        try {
            repository.save(object);
        } catch (Exception e) {
            throw new UserCustomExceptions.PersonaJuridicaAlreadyExists("No se pudo guardar la Persona Jurídica: " + e.getMessage());
        }
    }

    @Override
    public PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO)
    {
        if (!repository.findByCuit(personaJuridicaDTO.getCuit()).isEmpty()) 
        {
            throw new UserCustomExceptions.PersonaJuridicaAlreadyExists ("Persona Jurídica bajo ese CUIT ya se encuentra registrada.");
        }

        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, 12);

        personaJuridicaDTO.setCodigoAcceso (codigoAcceso);

        PersonaJuridica personaJuridicaEntity = PersonaJuridicaMapper.mapToPersonaJuridica (personaJuridicaDTO);
        repository.save (personaJuridicaEntity);

        return personaJuridicaDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO buscarPersonaJuridica (String code) 
    {
        PersonaJuridica personaJuridicaEntity = repository.findByCodigoAcceso(code).orElseThrow(() -> new UserCustomExceptions.PersonaJuridicaNotFound ("No hay una Persona Jurídica bajo con ese código :/"));
        
        return PersonaJuridicaMapper.mapToPersonaJuridicaDto(personaJuridicaEntity);
    }
    
}
