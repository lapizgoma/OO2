package oo2.grupo19.SistemaTickets.controllers.apirest.dto.personaJuridica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public record PersonaJuridicaRequestDTO(
    @NotBlank(message = "El 'codigo' es necesario.")
    @Size(min = PersonaJuridica.CODIGO_ACCESO_LENGTH, max = PersonaJuridica.CODIGO_ACCESO_LENGTH, message = "El código debe tener " + PersonaJuridica.CODIGO_ACCESO_LENGTH + " caracteres.")
    String codigo
) { }
