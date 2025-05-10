package oo2.grupo19.SistemaTickets.entities.estados;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oo2.grupo19.SistemaTickets.entities.Ticket;

@Entity
@Table(name = "estado_ticket")
@Getter @Setter @NoArgsConstructor
public class EstadoTicket {

    @Id
    private Long id;

    @Column(nullable = false)
    private String estado;

    @OneToOne(mappedBy = "estado")
    private Ticket ticket;

    public EstadoTicket(Long id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    // ID 1 -> PENDIENTE
    // ID 2 -> ATENDIDO
    // ID 3 -> CERRADO


}
