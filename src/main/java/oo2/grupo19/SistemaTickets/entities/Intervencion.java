package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "intervencion")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Intervencion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = true, name = "empleado_id")
  private Empleado realizadoPor;

  @ManyToOne
  @ToString.Exclude
  private Ticket ticket;

  @Column(name = "fecha_hora", nullable = false)
  private LocalDateTime fecha;

  @Column(length = 1000, nullable = true)
  private String descripcion;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "estado_id", nullable = false)
  private EstadoIntervencion estado;

  @PrePersist
  private void prePersist() {
    this.fecha = LocalDateTime.now();
  }
}