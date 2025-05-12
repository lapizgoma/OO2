package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.repositories.IContacto;
import oo2.grupo19.SistemaTickets.services.IService;

public class ContactoServiceImpl implements IService<Contacto> {

    private final IContacto contactoRepository;

    public ContactoServiceImpl(IContacto contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Override
    public void delete(Long id) {
        contactoRepository.deleteById(id);        
    }

    @Override
    public List<Contacto> findAll() {
        return contactoRepository.findAll();
    }

    @Override
    public Optional<Contacto> findById(Long id) {
        return contactoRepository.findById(id);
    }

    @Override
    public void save(Contacto object) {
        contactoRepository.save(object);
    }  
    
    
}
