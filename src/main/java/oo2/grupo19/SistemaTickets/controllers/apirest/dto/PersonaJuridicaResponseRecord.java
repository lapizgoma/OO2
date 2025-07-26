package oo2.grupo19.SistemaTickets.controllers.apirest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PersonaJuridicaResponseRecord(
    @Schema(name = "razonSocial", 
            description = "Razón social o nombre de la Persona Jurídica.")
    String razonSocial,
    @Schema(name = "CUIT", 
            description = "CUIT de la Persona Jurídica.")
    String CUIT,
    @Schema(name = "codigo", 
            description = "Código interno de la empresa o grupo.")
    String codigo
)
{
}
