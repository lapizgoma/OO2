package oo2.grupo19.SistemaTickets.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
    public List<EstadoTicket> findAll() {
        try {
            return estadoTicketRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("Error: no se ha podido encontrar la lista de estados de ticket");
        }
    }

    @Override
    public Optional<EstadoTicket> findById(Long id) {
        try {
            return estadoTicketRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Error: no se ha podido encontrar el estado de ticket");
        }
    }

    @Override
    public void save(EstadoTicket estadoTicket) {
        try {
            estadoTicketRepository.save(estadoTicket);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            estadoTicketRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}