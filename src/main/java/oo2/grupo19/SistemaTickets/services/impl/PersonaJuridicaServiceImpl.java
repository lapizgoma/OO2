package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;
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
    public void delete(Long id)
    {
        try
        {
            repository.deleteById(id);
        }
        catch (Exception e)
        {
            throw new NotFoundException("No se pudo eliminar la Persona Jurídica"+ e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PersonaJuridicaDTO> findAll() {
        return PersonaJuridicaMapper.mapToPersonaJuridicaDtoSet(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findById(Long id) {
        return PersonaJuridicaMapper.mapToPersonaJuridicaDto(
            repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado la Persona Jurídica con el id: " + id))
        );
    }

    @Override
    @Transactional
    public void save(PersonaJuridicaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        Optional<PersonaJuridica> estadoOpt = repository.findByCuit(dto.getCuit());
        if(estadoOpt.isPresent()) {
            dto.setId(estadoOpt.get().getId());
            PersonaJuridica personaJuridica = PersonaJuridicaMapper.mapToPersonaJuridica(dto);
            repository.save(personaJuridica);
        } else {
        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, 12);
        dto.setCodigoAcceso (codigoAcceso);
        PersonaJuridica personaJuridica = PersonaJuridicaMapper.mapToPersonaJuridica(dto);
        repository.save(personaJuridica);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaJuridicaDTO findByCode(String code) 
    {
        PersonaJuridica personaJuridicaEntity = repository.findByCodigoAcceso(code)
            .orElseThrow(() -> new NotFoundException ("No hay una Persona Jurídica bajo con ese código :/"));
        
        return PersonaJuridicaMapper.mapToPersonaJuridicaDto(personaJuridicaEntity);
    }
    
}
