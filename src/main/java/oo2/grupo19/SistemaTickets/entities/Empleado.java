package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empleado")
@Getter @Setter @ToString @NoArgsConstructor
public class Empleado extends Usuario {

  @Column(name = "numero_legajo")
  private String nroLegajo;

  @CreationTimestamp
  private LocalDateTime alta;

  private LocalDateTime baja;

  @OneToOne
  @JoinColumn(name = "role_id", nullable = false)
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

  public void darDeBaja(){
    this.setDeleted(true);
    this.baja = LocalDateTime.now();
  }

}
