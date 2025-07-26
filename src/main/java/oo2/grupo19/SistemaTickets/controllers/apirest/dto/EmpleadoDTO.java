package oo2.grupo19.SistemaTickets.controllers.apirest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import oo2.grupo19.SistemaTickets.dto.ContactoDTO;

public record EmpleadoDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "El apellido es obligatorio")
    String apellido,
    
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener 7 u 8 dígitos")
    String dni,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,
    
    @Valid
    @NotNull(message = "La información de contacto es obligatoria")
    ContactoDTO contacto,

	@NotBlank(message = "El rol es obligatorio")
	String role
) {}
