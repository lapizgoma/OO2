package oo2.grupo19.SistemaTickets.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cliente extends Usuario {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id", nullable = true)
    private PersonaJuridica organizacion;

    public boolean tieneOrganizacion() {
        return this.organizacion != null;
    }
}
