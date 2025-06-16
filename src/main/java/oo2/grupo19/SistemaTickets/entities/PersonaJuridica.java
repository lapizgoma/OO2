package oo2.grupo19.SistemaTickets.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persona_juridica")
@Getter
@Setter
@NoArgsConstructor
public class PersonaJuridica 
{
    public static final int CODIGO_ACCESO_LENGTH = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social")
    @NotBlank(message = "La razon social no debe estar vacia")
    private String razonSocial;

    @Column(name = "cuit")
    @NotBlank(message = "El CUIT no debe estar vacio")
    private String cuit;

    @Column(name = "codigo_acceso")
    @NotBlank(message = "El codigo de acceso no tiene que estar vacio")
    @Size(min = CODIGO_ACCESO_LENGTH, max = CODIGO_ACCESO_LENGTH, message = "El código de acceso debe tener " + CODIGO_ACCESO_LENGTH + " caracteres")
    private String codigoAcceso;
}
