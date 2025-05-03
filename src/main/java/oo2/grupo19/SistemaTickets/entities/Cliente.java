package oo2.grupo19.SistemaTickets.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cliente")
@Entity
@Getter @Setter @NoArgsConstructor
public class Cliente extends Usuario {
    private String telefono;
    private String direccion; 
}
