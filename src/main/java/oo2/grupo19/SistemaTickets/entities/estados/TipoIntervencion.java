package oo2.grupo19.SistemaTickets.entities.estados;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_intervencion")
@Getter @Setter @NoArgsConstructor
public class TipoIntervencion {

    @Id
    private Long id;

    @Column(nullable = false)
    private String estado;

    public TipoIntervencion(Long id, String estado) {
        this.id = id;
        this.estado = estado;
    }

}
