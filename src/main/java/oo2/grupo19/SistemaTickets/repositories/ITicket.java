package oo2.grupo19.SistemaTickets.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public interface ITicket extends JpaRepository<Ticket,Long> {
    
    Ticket findByCreadoPor_Id(Long id);


}
