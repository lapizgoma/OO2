package oo2.grupo19.SistemaTickets.entities.estados;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor
public class Role {

    @Id
    private Long id;

    @Column(nullable = false)
    private String rol;


    public Role(Long id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    
}
