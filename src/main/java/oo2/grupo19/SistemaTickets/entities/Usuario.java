package oo2.grupo19.SistemaTickets.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contacto_id")
    @Valid
    protected Contacto contacto;

    @Column(length = 10)
    @NotBlank(message = "El nombre no debe estar vacio")
    protected String nombre;
    
    @Column(length = 10)
    @NotBlank(message = "El apellido no debe estar vacio")
    protected String apellido;
    
    @Column(nullable = false)
    @NotBlank(message = "La password no debe estar vacia")
    protected String password;

    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 caracteres")
    @Column(nullable = false)
    private String dni;

    protected boolean deleted;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(
        name = "users_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns= @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

	public UsuarioDTO usuarioToDto() {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(this.id);
		usuarioDto.setApellido(this.apellido);
		usuarioDto.setNombre(this.nombre);
		usuarioDto.setEmail(this.contacto.getEmail());
		return usuarioDto;
	}
    
    public Cliente toCliente(String razonSocial, String cuit){
        Cliente cliente = new Cliente();
        cliente.setApellido(this.apellido);
        cliente.setContacto(this.contacto);
        cliente.setDeleted(this.deleted);
        cliente.setDni(this.dni);
        cliente.setId(this.id);
        cliente.setPassword(this.password);
        cliente.setNombre(this.nombre);
        PersonaJuridica personaJuridica = new PersonaJuridica();
        personaJuridica.setCuit(cuit);
        personaJuridica.setRazonSocial(razonSocial);
        personaJuridica.getCliente().add(cliente);
        cliente.setOrganizacion(personaJuridica);
        return cliente;
    }

    public Cliente toCliente(){
        Cliente cliente = new Cliente();
        cliente.setApellido(this.apellido);
        cliente.setContacto(this.contacto);
        cliente.setDeleted(this.deleted);
        cliente.setDni(this.dni);
        cliente.setId(this.id);
        cliente.setPassword(this.password);
        cliente.setNombre(this.nombre);
        return cliente;
    }

    public void asignarContactoUsuario(){
        this.contacto.setUsuario(this);
    }

    public void agregarRoles(Role role){
        if(this.roles == null){
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getType().getPrefixedName())).collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.getContacto().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.deleted;
    }


    
    
}
