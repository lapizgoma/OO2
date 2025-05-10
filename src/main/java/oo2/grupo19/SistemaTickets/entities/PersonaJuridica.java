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

@Entity
@Table(name = "persona_juridica")
@Getter @Setter @NoArgsConstructor
public class PersonaJuridica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    @Column(name = "razon_social")
    private String razonSocial;

    private String cuit;

    @OneToOne(mappedBy = "organizacion")
    private Cliente cliente;

}
