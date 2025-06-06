package oo2.grupo19.SistemaTickets.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contacto")
@Getter @Setter @NoArgsConstructor @ToString
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "La calle es obligatoria")
    @Column(nullable = false)
    private String calle;

    @NotBlank(message = "El numero de puerta es obligatorio")
    @Column(name = "nro_puerta", nullable = false)
    private String nroPuerta;

    @NotBlank(message = "La localidad es obligatoria")
    @Column(nullable = false)
    private String localidad;

    @OneToOne(mappedBy = "contacto")
    @ToString.Exclude
    @JsonIgnore
    private Usuario usuario;

}
