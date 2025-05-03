package oo2.grupo19.SistemaTickets.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oo2.grupo19.SistemaTickets.entities.Mensaje;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IMensaje extends JpaRepository<Mensaje,Long> {

    Mensaje findByFecha(LocalDateTime fecha);
	
    @Query("Select m from Mensaje m where m.usuario.id = :idCliente AND m.fecha BETWEEN :fechaInicio AND :fechaFinal")
	List<Mensaje> traerFecha(@Param("fechaInicio") LocalDateTime fechaInicio,
                            @Param("fechaFinal") LocalDateTime fechaFinal,
                            @Param("idCliente") Long  idCliente);
    
    @Query("Select m from Mensaje m where m.fecha = :fecha AND m.usuario = :usuario")
	List<Mensaje> traer(@Param("fecha") LocalDateTime fecha,
                        @Param("usuario") Usuario usuario);

    @Query("select m from Mensaje m where m.usuario.id = :idCliente")
	List<Mensaje> traerMensajesPorCliente(@Param("idCliente") Long idCliente);
    
    @Query("select m.usuario from Mensaje m where m.usuario.id = :idCliente ")
	Usuario traerUsuarioDesdeMensaje(@Param("idCliente") Long idCliente);
    
    @Query("select m from Mensaje m where m.ticket.id = :idTicket order by m.fecha asc")
	List<Mensaje> traerMensajePorTicket(@Param("idTicket") Long idTicket);

}
