package oo2.grupo19.SistemaTickets.entities.estados;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "prioridad")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Prioridad {
    @Id
    private Long id;

    @NotBlank
    private String prioridad;
}
