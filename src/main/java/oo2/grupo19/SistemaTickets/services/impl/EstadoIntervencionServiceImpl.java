package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
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
        } catch (Error e) {
            throw new RuntimeErrorException(e, "Error: no se ha podido eliminar el estado de intervención");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoIntervencion> findAll() {
        try {
            return estadoRepository.findAll();
        } catch (Error e) {
            throw new RuntimeErrorException(e, "Error: no se ha podido mostrar la lista de estados de intervención");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoIntervencion> findById(Long id) {
        try {
            return estadoRepository.findById(id);
        } catch (Error e) {
            throw new RuntimeErrorException(e, "Error: no se ha podido mostrar el estado de intervención");
        }
    }

    @Override
    @Transactional
    public void save(EstadoIntervencion object) {
        try {
            estadoRepository.save(object);
        } catch (Error e) {
            throw new RuntimeErrorException(e, "Error: no se ha podido guardar el estado de intervención");
        }
    }
}