package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Objects;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empleado")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Empleado extends Usuario {

  @Column(name = "numero_legajo")
  private String nroLegajo;

  @CreationTimestamp
  private LocalDateTime alta;

  private LocalDateTime baja;

  @ManyToMany(mappedBy = "listEmpleado")
  private Set<Ticket> tickets = new HashSet<>();
  /* 
  @ManyToMany
  @JoinTable(name = "empleado_ticket", joinColumns = @JoinColumn(name = "empleado_id", nullable = true), inverseJoinColumns = @JoinColumn(name = "ticket_id", nullable = true))
  private List<Ticket> tickets;*/

  public EmpleadoDTO empleadoToDto() {
    EmpleadoDTO empleadoDto = new EmpleadoDTO();
    empleadoDto.setId(this.id);
    empleadoDto.setNombre(this.nombre);
    return empleadoDto;
  }

  public void darDeBaja() {
    this.setDeleted(true);
    this.baja = LocalDateTime.now();
  }

  public void agregarTicket(Ticket ticket) {
    if (!tickets.contains(ticket)) {
        tickets.add(ticket);
    }
}
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Empleado)) return false;
    Empleado other = (Empleado) o;
    return id != null && id.equals(other.getId());
}

@Override
public int hashCode() {
    return Objects.hash(id);
}

}
