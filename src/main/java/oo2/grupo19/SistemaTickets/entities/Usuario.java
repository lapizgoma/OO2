package oo2.grupo19.SistemaTickets.entities;

import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false)
    protected String email;
    @Column(length = 10)
    protected String nombre;
    @Column(length = 10)
    protected String apellido;
    @Size(min = 4,max = 12)
    @Column(nullable = true)
    protected String password;
    protected boolean deleted;

	public UsuarioDTO usuarioToDto() {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(this.id);
		usuarioDto.setApellido(this.apellido);
		usuarioDto.setNombre(this.nombre);
		usuarioDto.setEmail(this.email);
		return usuarioDto;
	}
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', email='" + email + "'}";
    }

}
