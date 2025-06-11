package oo2.grupo19.SistemaTickets.services.impl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.estados.IPrioridad;
import oo2.grupo19.SistemaTickets.services.IPrioridadService;

@Service
public class PrioridadServiceImpl implements IPrioridadService {
    


    private final IPrioridad prioridadRepository;

    public PrioridadServiceImpl(IPrioridad prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    @Override
    public List<Prioridad> findAll() {
        try {
            return prioridadRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("Error: no se ha podido encontrar la lista de prioridades");
        }
    }

    @Override
    public Optional<Prioridad> findById(Long id) {
        try {
            return prioridadRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Error: no se ha podido encontrar la prioridad");
        }
    }

    @Override
    public void save(Prioridad prioridad) {
        try {
            prioridadRepository.save(prioridad);
        } catch (Exception e) {
            throw new RuntimeException("Error: no se ha podido guardar la prioridad", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            prioridadRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Error: no se ha podido eliminar la prioridad");
        }
    }
}

