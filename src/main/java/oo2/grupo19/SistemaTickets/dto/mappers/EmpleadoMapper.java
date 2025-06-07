package oo2.grupo19.SistemaTickets.dto.mappers;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDeIntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public class EmpleadoMapper {
    public static EmpleadoDeIntervencionDTO mapToEmpleadoIntervencionDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoDeIntervencionDTO dto = new EmpleadoDeIntervencionDTO();

        dto.nombre = empleado.getNombre();
        dto.apellido = empleado.getApellido();

        return dto;
    }

}
