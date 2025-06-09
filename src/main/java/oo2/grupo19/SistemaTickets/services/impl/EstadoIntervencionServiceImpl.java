package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.services.IEstadoIntervencionService;

@Service
public class EstadoIntervencionServiceImpl implements IEstadoIntervencionService {

    private final IEstadoIntervencion estadoRepository;

    public EstadoIntervencionServiceImpl(IEstadoIntervencion estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoIntervencionDeleteException("Error: no se ha podido eliminar el estado de intervención", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoIntervencion> findAll() {
        try {
            return estadoRepository.findAll();
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoIntervencionListException("Error: no se ha podido mostrar la lista de estados de intervención", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoIntervencion> findById(Long id) {
        try {
            return estadoRepository.findById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoIntervencionNotFoundException("Error: no se ha podido mostrar el estado de intervención", e);
        }
    }

    @Override
    @Transactional
    public void save(EstadoIntervencion object) {
        try {
            estadoRepository.save(object);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoIntervencionSaveException("Error: no se ha podido guardar el estado de intervención", e);
        }
    }
}