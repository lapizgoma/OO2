package oo2.grupo19.SistemaTickets.controllers.apirest.dto;

import jakarta.validation.constraints.NotBlank;

public record TicketDTO(
    @NotBlank(message = "El detalle no debe estar vacío")
    String detalle,
    @NotBlank(message = "El asunto no debe estar vacío")
    String asunto,
    @NotBlank(message = "El cliente no debe estar vacío")
    String clienteEmail
) {}
