package oo2.grupo19.SistemaTickets.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket")
@Getter @Setter @NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private LocalDateTime fechaHora;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Usuario creadoPor;

    @Column(length = 20,nullable = false)
    private String asunto;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = false,mappedBy = "ticket")
    private List<Intervencion> lstIntervencion;

    @ManyToMany(mappedBy = "tickets")
    private List<Empleado> listEmpleado;

	@OneToOne
	@JoinColumn(name = "estado_ticket", nullable = false)
    private EstadoTicket estado;

	@Column(length = 10, nullable = false)
	private String detalle;

    public void agregarEmpleado(Empleado empleado) {
	    if (listEmpleado == null) {
	        listEmpleado = new ArrayList<>();
	    }
	    listEmpleado.add(empleado);
	}
	
	public void agregarMensaje(Intervencion chat) {
	    if (lstIntervencion == null) {
	    	lstIntervencion = new ArrayList<>();
	    }
	    // Seteamos la relacion bidireccional desde esta clase
	    lstIntervencion.add(chat);
	    chat.setTicket(this);
	}
	
	public boolean existeUsuario() {
		if(this.creadoPor == null) {
			return false;
		}
		return true;
	}
	
	public TicketDTO toDto() {
		TicketDTO dto = new TicketDTO();
		dto.setId(this.id);
		dto.setAsunto(this.asunto);
		dto.setEstado(this.estado.toString());
		return dto;
	}

	@Override
	public String toString() {
		return "Ticket{id=" + id + ", asunto='" + asunto + "', usuario=" + (creadoPor != null ? creadoPor.toString() : "null") + "}";
	}

}
