package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDate;


import oo2.grupo19.SistemaTickets.dto.MensajeDTO;

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
@Table(name = "mensaje")
@Getter @Setter @NoArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false,name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    private Ticket ticket;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(length = 1000, nullable = false)
    private String contenido;

    public MensajeDTO mensajeToDto() {
		MensajeDTO dto = new MensajeDTO();
		dto.setId(this.id);
		dto.setContenido(this.contenido);
		return dto;
	}

  @Override
  public String toString() {
      return "Mensaje{id=" + id + ", contenido='" + contenido + "'}";
  }


}

