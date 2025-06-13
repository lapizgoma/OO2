package oo2.grupo19.SistemaTickets.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persona_juridica")
@Getter
@Setter
@NoArgsConstructor
public class PersonaJuridica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social")
    @NotBlank(message = "La razon social no debe estar vacia")
    private String razonSocial;

    @Column(name = "cuit")
    @NotBlank(message = "El cuit no debe estar vacio")
    private String cuit;

    @Column(name = "codigo_acceso")
    @NotBlank(message = "El codigo de acceso no tiene que estar vacio")
    private String codigoAcceso;

    @OneToMany(mappedBy = "organizacion")
    @JsonIgnore
    private List<Cliente> cliente;

}
