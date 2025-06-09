package oo2.grupo19.SistemaTickets.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.IEstadoTicketService;

@Service
public class EstadoTicketServiceImpl implements IEstadoTicketService {

    @Autowired
    private IEstadoTicket estadoTicketRepository;

    @Override
    public List<EstadoTicket> findAll() {
        return estadoTicketRepository.findAll();
    }

    @Override
    public Optional<EstadoTicket> findById(Long id) {
        return estadoTicketRepository.findById(id);
    }

    @Override
    public void save(EstadoTicket estadoTicket) {
        estadoTicketRepository.save(estadoTicket);
    }

    @Override
    public void delete(Long id) {
        estadoTicketRepository.deleteById(id);
    }
}