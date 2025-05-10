package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;
import oo2.grupo19.SistemaTickets.entities.estados.TipoIntervencion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "intervencion")
@Getter @Setter @NoArgsConstructor
public class Intervencion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(nullable = false,name = "usuario_id")
  private Usuario realizadoPor;

  @ManyToOne
  private Ticket ticket;

  @Column(name = "fecha", nullable = false)
  private LocalDateTime fecha;

  @Column(length = 1000, nullable = false)
  private String descripcion;

  @OneToOne
  @JoinColumn(name = "estado_id", nullable = false)
  private EstadoIntervencion estado;

  @OneToOne
  @JoinColumn(name = "tipo_intervencion_id", nullable = true)
  private TipoIntervencion tipo;

  public IntervencionDTO mensajeToDto() {
		IntervencionDTO dto = new IntervencionDTO();
		dto.setId(this.id);
		dto.setContenido(this.descripcion);
		return dto;
	}

  @Override
  public String toString() {
      return "Mensaje{id=" + id + ", contenido='" + descripcion + "'}";
  }


}

