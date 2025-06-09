package oo2.grupo19.SistemaTickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContactoDTO {
    private String email;
    private String telefono;
    private String calle;
    private String nroPuerta;
    private String localidad;
}

