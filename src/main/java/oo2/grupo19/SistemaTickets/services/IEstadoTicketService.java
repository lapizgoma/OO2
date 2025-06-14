package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;

public interface IEstadoTicketService extends IService<EstadoTicketDTO> {
    public EstadoTicketDTO findByEstado(String estado);
}
