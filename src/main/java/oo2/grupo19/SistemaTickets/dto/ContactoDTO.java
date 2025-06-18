package oo2.grupo19.SistemaTickets.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class ContactoDTO {
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{11}", message = "El teléfono debe tener 11 dígitos")
    private String telefono;
    
    @NotBlank(message = "La calle es obligatoria")
    private String calle;
    
    @NotBlank(message = "El número de puerta es obligatorio")
    private String nroPuerta;
    
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
}

