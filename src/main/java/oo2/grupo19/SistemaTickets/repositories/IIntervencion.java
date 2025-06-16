package oo2.grupo19.SistemaTickets.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IIntervencion extends JpaRepository<Intervencion,Long> {

    Optional<Intervencion> findByFecha(LocalDateTime fecha);
	
    @Query("Select m from Intervencion m where m.realizadoPor.id = :idCliente AND m.fecha BETWEEN :fechaInicio AND :fechaFinal")
	Set<Intervencion> traerFecha(@Param("fechaInicio") LocalDateTime fechaInicio,
                            @Param("fechaFinal") LocalDateTime fechaFinal,
                            @Param("idCliente") Long  idCliente);
    
    @Query("Select m from Intervencion m where m.fecha = :fecha AND m.realizadoPor = :usuario")
	Set<Intervencion> traer(@Param("fecha") LocalDateTime fecha,
                        @Param("usuario") Usuario usuario);

    @Query("select m from Intervencion m where m.realizadoPor.id = :idCliente")
	Set<Intervencion> traerIntervencionPorCliente(@Param("idCliente") Long idCliente);
    
    @Query("select m.realizadoPor from Intervencion m where m.realizadoPor.id = :idCliente ")
	Cliente traerClienteDesdeIntervencion(@Param("idCliente") Long idCliente);
    
    @Query("select m from Intervencion m where m.ticket.id = :idTicket order by m.fecha asc")
	Set<Intervencion> traerIntervencionPorTicket(@Param("idTicket") Long idTicket);
}
