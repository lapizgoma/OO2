package oo2.grupo19.SistemaTickets.services;

import java.time.LocalDateTime;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;

public interface IIntervencionService extends IService<IntervencionDTO> {
    Set<IntervencionDTO> traerIntervencionPorCliente(Long idCliente);
    Set<IntervencionDTO> traerIntervencionPorTicket(Long idTicket);
    Set<IntervencionDTO> traer(LocalDateTime fecha, Empleado empleado);
    Set<IntervencionDTO> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente);
    IntervencionDTO traerFecha(LocalDateTime fecha);
    Usuario traerUsuarioDesdeIntervencion(Long idCliente);
    void actualizarEstadoIntervencion(Long empleadoId, Long ticketId, Long intervencionId, EstadoIntervencion nuevoEstado);
}