package oo2.grupo19.SistemaTickets.services.impl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;
import oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions;
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
            throw new TicketCustomExceptions.PrioridadListException("Error: no se ha podido mostrar la lista de prioridades", e);
        }
    }

    @Override
    public Optional<Prioridad> findById(Long id) {
        try {
            return prioridadRepository.findById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.PrioridadNotFoundException("Error: no se ha podido mostrar la prioridad", e);
        }
    }

    @Override
    public void save(Prioridad prioridad) {
        try {
            prioridadRepository.save(prioridad);
        } catch (Exception e) {
            throw new TicketCustomExceptions.PrioridadSaveException("Error: no se ha podido guardar la prioridad", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            prioridadRepository.deleteById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.PrioridadDeleteException("Error: no se ha podido eliminar la prioridad", e);
        }
    }
}

