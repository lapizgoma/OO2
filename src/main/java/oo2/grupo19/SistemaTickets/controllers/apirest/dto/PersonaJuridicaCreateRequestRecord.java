package oo2.grupo19.SistemaTickets.controllers.apirest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PersonaJuridicaCreateRequestRecord(
    @Schema(name = "razonSocial", 
            description = "Razón social o nombre de la Persona Jurídica.",
            required = true)
    @NotBlank(message="Es necesaria una Razón Social.")
    String razonSocial,
    @Schema(name = "CUIT", 
            description = "CUIT de la Persona Jurídica.",
            required = true)
    @NotBlank(message ="Es necesario un CIUT")
    @Pattern(regexp = "^\\d+$", message = "Solo se aceptan dígitos para el CUIT.")
    String CUIT
)
{

}
