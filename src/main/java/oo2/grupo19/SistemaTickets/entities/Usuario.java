package oo2.grupo19.SistemaTickets.entities;

import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contacto_id")
    protected Contacto contacto;

    @Column(length = 10)
    protected String nombre;

    @Column(length = 10)
    protected String apellido;

    @Size(min = 4,max = 12)
    @Column(nullable = false)
    protected String password;
    
    @Size(max = 8)
    @Column(nullable = false)
    protected int dni;

    protected boolean deleted;

	public UsuarioDTO usuarioToDto() {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(this.id);
		usuarioDto.setApellido(this.apellido);
		usuarioDto.setNombre(this.nombre);
		usuarioDto.setEmail(this.contacto.getEmail());
		return usuarioDto;
	}
    
}
