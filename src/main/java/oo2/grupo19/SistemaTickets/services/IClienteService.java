package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.dto.ClienteDTO;

public interface IClienteService extends IService<ClienteDTO>{
    ClienteDTO findByEmail(String email);
}
