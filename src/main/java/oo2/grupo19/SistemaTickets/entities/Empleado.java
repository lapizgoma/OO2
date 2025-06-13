package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.annotations.CreationTimestamp;

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
}
