package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.EstadoIntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.EstadoIntervencionMapper;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
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
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<EstadoIntervencionDTO> findAll() {
        return EstadoIntervencionMapper.mapToEstadoIntervencionDtoList(estadoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoIntervencionDTO findById(Long id) {
        return EstadoIntervencionMapper.mapEstadoIntervencionToDto(estadoRepository.findById(id).orElseThrow(() -> new NotFoundException("No se ha encontrado el Estado con ese email")));
    }

    @Override
    @Transactional
    public void save(EstadoIntervencionDTO estadodto) {
        if (estadodto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        EstadoIntervencion estado = EstadoIntervencionMapper.mapDtoToEstadoIntervencion(estadodto);
        estadoRepository.save(estado);
    }
}