package oo2.grupo19.SistemaTickets.entities.estados;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estado_ticket")
@Getter @Setter @NoArgsConstructor
public class EstadoTicket {

    @Id
    private Long id;

    @Column(nullable = false)
    private String estado;

    public EstadoTicket(Long id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    // ID 1 -> PENDIENTE
    // ID 2 -> ATENDIDO
    // ID 3 -> CERRADO


}
