package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.PersonaJuridicaMapper;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.PersonaJuridicaAlreadyExists;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
public class PersonaJuridicaService implements IService<PersonaJuridica> {

    private final IPersonaJuridica repository;

    public PersonaJuridicaService(IPersonaJuridica repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaJuridica> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonaJuridica> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(PersonaJuridica object) {
        repository.save(object);
    }

    public PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO)
    {
        if (!repository.findByCuit(personaJuridicaDTO.getCuit()).isEmpty()) 
        {
            throw new PersonaJuridicaAlreadyExists ("Persona Jurídica bajo ese CUIT ya se encuentra registrada.");
        }

        String codigoAcceso = UUID.randomUUID ().toString ().replace ("-", "").substring (0, 12);

        personaJuridicaDTO.setCodigoAcceso (codigoAcceso);

        PersonaJuridica personaJuridicaEntity = PersonaJuridicaMapper.mapToPersonaJuridica (personaJuridicaDTO);
        repository.save (personaJuridicaEntity);

        return personaJuridicaDTO;
    }
    
}
