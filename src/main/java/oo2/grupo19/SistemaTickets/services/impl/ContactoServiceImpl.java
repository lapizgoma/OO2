package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import oo2.grupo19.SistemaTickets.dto.ContactoDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.ContactoMapper;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IContacto;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
public class ContactoServiceImpl implements IService<ContactoDTO> {

    private final IContacto contactoRepository;
    public ContactoServiceImpl(IContacto contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new NotFoundException("Contacto con id " + id + " no existe");
        }
        contactoRepository.deleteById(id);        
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ContactoDTO> findAll() {
        return ContactoMapper.mapToContactoDtoSet(contactoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactoDTO findById(Long id) {
        Contacto contacto = contactoRepository.findById(id).orElseThrow(() -> new NotFoundException("Contacto no encontrado"));
        return ContactoMapper.mapToContactoDto(contacto);
    }

    @Override
    @Transactional
    public void save(ContactoDTO contactodto) {
        if (contactodto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        
        Optional<Contacto> contactoOpt = contactoRepository.findByEmail(contactodto.getEmail());
        if(contactoOpt.isPresent()) {
            contactodto.setId(contactoOpt.get().getId());
            Contacto contacto = ContactoMapper.mapToContactoEntity(contactodto);
            contactoRepository.save(contacto);
        } else {
        Contacto contacto = ContactoMapper.mapToContactoEntity(contactodto);
        contactoRepository.save(contacto);
        }
    }  
    
    
}
