package oo2.grupo19.SistemaTickets.dto;

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
public class PersonaJuridicaDTO {
    private String razonSocial;
    private String cuit;
    private String codigoAcceso;
}
