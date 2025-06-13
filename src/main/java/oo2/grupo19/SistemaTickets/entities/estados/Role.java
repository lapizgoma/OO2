package oo2.grupo19.SistemaTickets.entities.estados;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Role {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 80, unique = true)
    private RoleType type;

    @Column(name = "create_at_role")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_role")
    @UpdateTimestamp
    private Timestamp updateAt;

    public Role(Long id, @NotNull RoleType type) {
        this.id = id;
        this.type = type;
    }

}
