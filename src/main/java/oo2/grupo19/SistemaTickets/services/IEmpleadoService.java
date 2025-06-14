package oo2.grupo19.SistemaTickets.services;

import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;


public interface IEmpleadoService extends IService<EmpleadoDTO> {
    EmpleadoDTO findByEmail(String email);
	Set<EmpleadoDTO> findAllActive();
}

