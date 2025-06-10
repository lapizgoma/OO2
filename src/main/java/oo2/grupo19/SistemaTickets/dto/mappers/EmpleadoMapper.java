package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDeIntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public final class EmpleadoMapper {
    private EmpleadoMapper() {}

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
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        return dto;
    }

    public static Empleado mapToEmpleadoEntity(EmpleadoDTO dto) {
        if (dto == null) return null;
        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        return empleado;
    }

    public static List<EmpleadoDTO> mapToEmpleadoDtoList(List<Empleado> empleados) {
        return empleados == null ? List.of() : empleados.stream().map(EmpleadoMapper::mapToEmpleadoDto).collect(Collectors.toList());
    }

    public static List<Empleado> mapToEmpleadoEntityList(List<EmpleadoDTO> dtos) {
        return dtos == null ? List.of() : dtos.stream().map(EmpleadoMapper::mapToEmpleadoEntity).collect(Collectors.toList());
    }
}
