package oo2.grupo19.SistemaTickets.services.impl;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.PrioridadMapper;
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
    public Set<PrioridadDTO> findAll() {
        return PrioridadMapper.mapToPrioridadDtoSet(prioridadRepository.findAll());
    }

    @Override
    public PrioridadDTO findById(Long id) {
        return PrioridadMapper.mapPrioridadToDto(
            prioridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado la prioridad con el id: " + id))
        );
    }

    @Override
    public PrioridadDTO findByPrioridad(String prioridad) {
        return PrioridadMapper.mapPrioridadToDto(
            prioridadRepository.findByPrioridad(prioridad)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado la prioridad "))
        );
    }

    @Override
    public void save(PrioridadDTO prioridaddto) {
        if (prioridaddto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        Optional<Prioridad> prioridadOpt = prioridadRepository.findByPrioridad(prioridaddto.getPrioridad());
        if(prioridadOpt.isPresent()) {
            prioridaddto.setId(prioridadOpt.get().getId());
            Prioridad prioridad = PrioridadMapper.mapDtoToPrioridad(prioridaddto);
            prioridadRepository.save(prioridad);
        } else {
        Prioridad prioridad = PrioridadMapper.mapDtoToPrioridad(prioridaddto);
        prioridadRepository.save(prioridad);
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

