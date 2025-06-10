package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.repositories.IContacto;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
public class ContactoServiceImpl implements IService<Contacto> {

    private final IContacto contactoRepository;
    public ContactoServiceImpl(IContacto contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new IllegalArgumentException("Contacto con id " + id + " no existe");
        }
        contactoRepository.deleteById(id);        
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> findAll() {
        return contactoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contacto> findById(Long id) {
        Optional<Contacto> contacto = contactoRepository.findById(id);
        if (contacto.isEmpty()) {
            throw new IllegalArgumentException("Contacto con id " + id + " no encontrado");
        }
        return contacto;
    }

    @Override
    @Transactional
    public void save(Contacto object) {
        if (object == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        contactoRepository.save(object);
    }  
    
    
}
