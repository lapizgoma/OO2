package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;

public interface IPrioridadService extends IService<PrioridadDTO> {
    PrioridadDTO findByPrioridad (String prioridad);
}
