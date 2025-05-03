package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.entities.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empleado")
@Getter @Setter @ToString @NoArgsConstructor
public class Empleado extends Usuario {

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    @Enumerated
    private Role role;

    @ManyToMany
    @JoinTable(name = "empleado_ticket",
                joinColumns = @JoinColumn(name = "empleado_id", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> tickets;

    public EmpleadoDTO empleadoToDto() {
		EmpleadoDTO empleadoDto = new EmpleadoDTO();
		empleadoDto.setId(this.id);
		empleadoDto.setNombre(this.nombre);
		empleadoDto.setRole(this.role.toString());
		return empleadoDto;
	}
}
