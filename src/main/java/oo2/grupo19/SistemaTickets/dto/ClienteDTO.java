package oo2.grupo19.SistemaTickets.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClienteDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private String password;
    private ContactoDTO contacto;
    private PersonaJuridicaDTO organizacion;
}
