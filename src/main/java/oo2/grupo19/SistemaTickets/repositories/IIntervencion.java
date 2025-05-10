package oo2.grupo19.SistemaTickets.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IIntervencion extends JpaRepository<Intervencion,Long> {

    Intervencion findByFecha(LocalDateTime fecha);
	
    @Query("Select m from Intervencion m where m.realizadoPor.id = :idCliente AND m.fecha BETWEEN :fechaInicio AND :fechaFinal")
	List<Intervencion> traerFecha(@Param("fechaInicio") LocalDateTime fechaInicio,
                            @Param("fechaFinal") LocalDateTime fechaFinal,
                            @Param("idCliente") Long  idCliente);
    
    @Query("Select m from Intervencion m where m.fecha = :fecha AND m.realizadoPor = :usuario")
	List<Intervencion> traer(@Param("fecha") LocalDateTime fecha,
                        @Param("usuario") Usuario usuario);

    @Query("select m from Intervencion m where m.realizadoPor.id = :idCliente")
	List<Intervencion> traerIntervencionPorCliente(@Param("idCliente") Long idCliente);
    
    @Query("select m.realizadoPor from Intervencion m where m.realizadoPor.id = :idCliente ")
	Usuario traerClienteDesdeIntervencion(@Param("idCliente") Long idCliente);
    
    @Query("select m from Intervencion m where m.ticket.id = :idTicket order by m.fecha asc")
	List<Intervencion> traerIntervencionPorTicket(@Param("idTicket") Long idTicket);

}
