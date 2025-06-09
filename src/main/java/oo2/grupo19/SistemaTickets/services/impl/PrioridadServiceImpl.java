package oo2.grupo19.SistemaTickets.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.IPrioridadService;

@Service
public class PrioridadServiceImpl implements IPrioridadService {
    


    @Autowired
    private IPrioridad prioridadRepository;

    @Override
    public List<Prioridad> findAll() {
        return prioridadRepository.findAll();
    }

    @Override
    public Optional<Prioridad> findById(Long id) {
        return prioridadRepository.findById(id);
    }

    @Override
    public void save(Prioridad prioridad) {
        prioridadRepository.save(prioridad);
    }

    @Override
    public void delete(Long id) {
        prioridadRepository.deleteById(id);
    }
}

