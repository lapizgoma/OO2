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
public class IntervencionDTO {
    private Long id;
    private String descripcion;
    private String fecha;
    private String estado;
    private String realizadoPor;
    private String empleadoEmail;
    private Long ticketId;
}
