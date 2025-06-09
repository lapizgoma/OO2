package oo2.grupo19.SistemaTickets.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.TicketCustomExceptions;
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
            throw new TicketCustomExceptions.EstadoTicketListException("Error: no se ha podido mostrar la lista de estados de ticket", e);
        }
    }

    @Override
    public Optional<EstadoTicket> findById(Long id) {
        try {
            return estadoTicketRepository.findById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoTicketNotFoundException("Error: no se ha podido mostrar el estado de ticket", e);
        }
    }

    @Override
    public void save(EstadoTicket estadoTicket) {
        try {
            estadoTicketRepository.save(estadoTicket);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoTicketSaveException("Error: no se ha podido guardar el estado de ticket", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            estadoTicketRepository.deleteById(id);
        } catch (Exception e) {
            throw new TicketCustomExceptions.EstadoTicketDeleteException("Error: no se ha podido eliminar el estado de ticket", e);
        }
    }
}