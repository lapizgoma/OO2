package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.PersonaJuridicaMapper;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
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
    public void delete(String id) {
        Long personaId = Long.parseLong(id);
        try {
            repository.deleteById(personaId);
        } catch (Exception e) {
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
    public void save(PersonaJuridicaDTO personaDto) {
        if (personaDto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        Optional<PersonaJuridica> personaOpt = repository.findByCuit(personaDto.getCuit());
        if(personaOpt.isPresent()) {
            PersonaJuridica personaDB = repository.findById(personaOpt.get().getId()).get();
            PersonaJuridica personaNew = PersonaJuridicaMapper.mapToPersonaJuridicaEntity(personaDto, personaDB);
            personaNew.setId(personaDB.getId());
            repository.save(personaNew);
        } else {
        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, 12);
        personaDto.setCodigoAcceso (codigoAcceso);
        PersonaJuridica personaJuridica = PersonaJuridicaMapper.mapToPersonaJuridicaEntity(personaDto, new PersonaJuridica());
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
