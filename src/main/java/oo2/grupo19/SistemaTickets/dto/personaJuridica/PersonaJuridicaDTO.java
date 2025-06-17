package oo2.grupo19.SistemaTickets.dto.personaJuridica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PersonaJuridicaDTO
{
    public static final int CODIGO_ACCESO_LENGTH = 12;

    @NotBlank(message = "La razon social no debe estar vacia")
    private String razonSocial;

    @NotBlank(message = "El CUIT no debe estar vacio")
    private String cuit;
    
    @NotBlank(message = "El codigo de acceso no tiene que estar vacio")
    @Size(min = CODIGO_ACCESO_LENGTH, max = CODIGO_ACCESO_LENGTH, message = "El código de acceso debe tener " + CODIGO_ACCESO_LENGTH + " caracteres")
    private String codigoAcceso;
}
