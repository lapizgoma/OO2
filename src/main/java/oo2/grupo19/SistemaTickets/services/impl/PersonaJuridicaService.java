package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IService;

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
    
}
