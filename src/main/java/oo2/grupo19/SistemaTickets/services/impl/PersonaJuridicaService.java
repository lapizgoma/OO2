package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.services.IService;

public class PersonaJuridicaService implements IService<PersonaJuridica> {

    private final IPersonaJuridica repository;

    public PersonaJuridicaService(IPersonaJuridica repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<PersonaJuridica> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PersonaJuridica> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(PersonaJuridica object) {
        repository.save(object);
    }
    
}
