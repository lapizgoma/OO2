package oo2.grupo19.SistemaTickets.dto.personaJuridica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PersonaJuridicaDTO
{
    // Así si se cambia en un lado, se cambia en todos.
    public static final int CODIGO_ACCESO_LENGTH = PersonaJuridica.CODIGO_ACCESO_LENGTH;

    @NotBlank(message = "La razon social no debe estar vacia")
    private String razonSocial;

    @NotBlank(message = "El CUIT no debe estar vacio")
    private String cuit;
    
    // @NotBlank(message = "El codigo de acceso no tiene que estar vacio")
    @Size(min = CODIGO_ACCESO_LENGTH, max = CODIGO_ACCESO_LENGTH, message = "El código de acceso debe tener " + CODIGO_ACCESO_LENGTH + " caracteres")
    private String codigoAcceso;
}
