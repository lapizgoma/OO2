package oo2.grupo19.SistemaTickets.services;

import java.time.LocalDateTime;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public interface IIntervencionService extends IService<IntervencionDTO> {
    Set<IntervencionDTO> traerIntervencionPorEmpleado(Long idEmpleado);
    Set<IntervencionDTO> traerIntervencionPorTicket(Long idTicket);
    Set<IntervencionDTO> traer(LocalDateTime fecha, Empleado empleado);
    Set<IntervencionDTO> traerPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente);
    IntervencionDTO traerPorFecha(LocalDateTime fecha);
    Empleado traerEmpleadoDesdeIntervencion(Long idEmpleado);
}