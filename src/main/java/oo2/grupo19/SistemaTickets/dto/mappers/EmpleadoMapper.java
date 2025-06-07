package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
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

    public static EmpleadoDTO mapToEmpleadoDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoDTO dto = new EmpleadoDTO();

        dto.setId  (empleado.getId());
        dto.setNombre  (empleado.getNombre());
        dto.setApellido  (empleado.getApellido());

        return dto;
    }

}
