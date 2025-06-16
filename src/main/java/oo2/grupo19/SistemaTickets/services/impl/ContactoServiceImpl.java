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
    public void delete(String id) {
        Long contactoId = Long.parseLong(id);
        if (!contactoRepository.existsById(contactoId)) {
            throw new NotFoundException("Contacto con id " + id + " no existe");
        }
        contactoRepository.deleteById(contactoId);        
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
            Contacto contactoDB = contactoRepository.findById(contactoOpt.get().getId()).get();
            Contacto contacto = ContactoMapper.mapToContactoEntity(contactodto, contactoDB);
            contacto.setId(contactoDB.getId());
            contactoRepository.save(contacto);
        } else {
        Contacto contacto = ContactoMapper.mapToContactoEntity(contactodto, new Contacto());
        contactoRepository.save(contacto);
        }
    }  
    
    
}
