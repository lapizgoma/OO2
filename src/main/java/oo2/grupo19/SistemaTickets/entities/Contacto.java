package oo2.grupo19.SistemaTickets.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contacto")
@Getter @Setter @NoArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;
    
    @Column(nullable = false)
    private String calle;
    
    @Column(name = "nro_puerta", nullable = false)
    private String nroPuerta;
    
    @Column(nullable = false)
    private String localidad;

    @OneToOne(mappedBy = "contacto")
    @ToString.Exclude
    private Usuario usuario;

}
