package oo2.grupo19.SistemaTickets.controllers.apirest.dto.personaJuridica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;

public record PersonaJuridicaRequestDTO(
    @NotBlank(message = "El 'codigo' es necesario.")
    @Size(min = PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH, max = PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH, message = "El código debe tener " + PersonaJuridicaDTO.CODIGO_ACCESO_LENGTH + " caracteres.")
    String codigo
) { }
