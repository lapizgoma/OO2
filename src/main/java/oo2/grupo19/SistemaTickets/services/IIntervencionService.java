package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.entities.Intervencion;

import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;

import java.time.LocalDateTime;
import java.util.List;



public interface IIntervencionService extends IService<Intervencion>{

    List<Intervencion> traerIntervencionPorCliente(Long idCliente);
    List<Intervencion> traerMensajePorTicket(Long idTicket);
    List<Intervencion> traer(LocalDateTime fecha, Usuario cliente);
    List<Intervencion> traerFecha(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Long idCliente);
    Intervencion traerFecha(LocalDateTime fecha);
    Usuario traerUsuarioDesdeIntervencion(Long idCliente);
    public void actualizarEstadoIntervencion(Long empleadoId, Long ticketId, Long intervencionId, EstadoIntervencion nuevoEstado);
}