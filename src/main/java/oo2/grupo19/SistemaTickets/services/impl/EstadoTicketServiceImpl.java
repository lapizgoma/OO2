package oo2.grupo19.SistemaTickets.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.EstadoTicketMapper;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;

@Service
public class EstadoTicketServiceImpl implements IEstadoTicketService {

    private final IEstadoTicket estadoTicketRepository;

    public EstadoTicketServiceImpl(IEstadoTicket estadoTicketRepository) {
        this.estadoTicketRepository = estadoTicketRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<EstadoTicketDTO> findAll() {
        return EstadoTicketMapper.mapToEstadoTicketDtoSet(estadoTicketRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoTicketDTO findById(Long id) {
        return EstadoTicketMapper.mapEstadoTicketToDto(estadoTicketRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("EstadoTicket not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoTicketDTO findByEstado(String estado) {
        return EstadoTicketMapper.mapEstadoTicketToDto(estadoTicketRepository.findByEstado(estado)
            .orElseThrow(() -> new NotFoundException("No se ha encontrado el estado del ticket")));
    }

    @Override
    @Transactional
    public void save(EstadoTicketDTO estadodto) {
        if (estadodto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        EstadoTicket estado = EstadoTicketMapper.mapDtoToEstadoTicket(estadodto);
        estadoTicketRepository.save(estado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            estadoTicketRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}