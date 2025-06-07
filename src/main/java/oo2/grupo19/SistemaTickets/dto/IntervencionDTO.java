package oo2.grupo19.SistemaTickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class IntervencionDTO {
    private Long id;
    private String contenido;
    private EmpleadoDeIntervencionDTO realizadoPor;
    private String fecha;
    private String descripcion;
    private String estado;
}

