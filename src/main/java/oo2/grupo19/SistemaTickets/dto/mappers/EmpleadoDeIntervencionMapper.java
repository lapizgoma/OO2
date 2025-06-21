package oo2.grupo19.SistemaTickets.dto.mappers;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDeIntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public class EmpleadoDeIntervencionMapper {
    public static EmpleadoDeIntervencionDTO mapToEmpleadoIntervencionDto(EmpleadoDTO empleado) {
        if (empleado == null) {
            return null;
        }
        EmpleadoDeIntervencionDTO dto = new EmpleadoDeIntervencionDTO();
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        return dto;
    }

    public static Empleado mapToEmpleadoIntervencionEntity(EmpleadoDeIntervencionDTO dto) {
        if (dto == null) {
            return null;
        }
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        return empleado;
    }
}
