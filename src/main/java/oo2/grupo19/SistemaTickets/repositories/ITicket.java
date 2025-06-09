package oo2.grupo19.SistemaTickets.repositories;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public interface ITicket extends JpaRepository<Ticket,Long> {
    
    Ticket findByCreadoPor_Id(Long id);

    @Query("select t from Ticket t left join fetch t.lstIntervencion where t.creadoPor.contacto.email = :email")
    List<Ticket> traerPorCliente(@Param("email") String email);

    @Query("select distinct t from Ticket t join t.listEmpleado e where e.contacto.email = :email")
    List<Ticket> traerPorEmpleado(@Param("email") String email);

    @Query("select t from Ticket t where t.asunto = :asunto")
    List<Ticket> traerPorAsunto(@Param("asunto") String asunto);

    @Query("select t from Ticket t where t.estado.id = :idEstado")
    List<Ticket> traerPorEstado(@Param("idEstado") Long idEstado);

    @Query("select t from Ticket t where t.prioridad.id = :idPrioridad")
    List<Ticket> traerPorPrioridad(@Param("idPrioridad") Long idPrioridad);

    @Query("select t from Ticket t where t.fechaHora >= :inicio and t.fechaHora < :fin")
    List<Ticket> traerPorRangoFecha(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);


    @Query("select t from Ticket t left join t.estado e where t.creadoPor.contacto.email = :email and e.id = :idEstado")
    List<Ticket> traerPorClienteCerrado(@Param("email") String email, @Param("idEstado") Long idEstado);

    /*@Query("select distinct t from Ticket t left join fetch t.lstIntervencion left join fetch t.listEmpleado e where t.id = :idTicket and e.id = :idEmpleado")
    Ticket traerPorEmpleadoYId(@Param("idEmpleado") Long idEmpleado, @Param("idTicket") Long idTicket);*/
    @Query("select t from Ticket t join t.listEmpleado e where t.id = :idTicket and e.id = :idEmpleado")
    Ticket traerPorEmpleadoYId(@Param("idEmpleado") Long idEmpleado, @Param("idTicket") Long idTicket);
}
