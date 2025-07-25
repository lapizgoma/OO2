package oo2.grupo19.SistemaTickets.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "persona_juridica")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonaJuridica 
{
    public static final int CODIGO_ACCESO_LENGTH = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social")
    private String razonSocial;

    @Pattern(regexp = "^[0-9]+$", message = "El CUIT debe contener solo dígitos.")
    private String cuit;

    @Column(name = "codigo_acceso")
    @Size(min = CODIGO_ACCESO_LENGTH, max = CODIGO_ACCESO_LENGTH, message = "El código de acceso debe tener " + CODIGO_ACCESO_LENGTH + " caracteres")
    private String codigoAcceso;

    private boolean deleted;

    public void darDeBaja()
    {
        this.setDeleted(true);
    }
}
