package oo2.grupo19.SistemaTickets.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UsuarioDTO {
    @JsonIgnore
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String role;
}
