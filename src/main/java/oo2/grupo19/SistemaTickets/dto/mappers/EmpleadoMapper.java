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
        dto.setEmail(empleado.getContacto().getEmail());
        dto.setTelefono(empleado.getContacto().getTelefono());
        dto.setNroLegajo(empleado.getNroLegajo());
        dto.setDni(empleado.getDni());
        dto.setDireccionCompleta(empleado.getContacto().getCalle() + ", " + empleado.getContacto().getNroPuerta() + ", " + empleado.getContacto().getLocalidad());
        return dto;
    }

    public static Empleado mapToEmpleadoEntity(EmpleadoDTO dto) {
        if (dto == null) return null;
        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.getContacto().setEmail(dto.getEmail());
        empleado.getContacto().setTelefono(dto.getTelefono());
        empleado.setNroLegajo(dto.getNroLegajo());
        empleado.setDni(dto.getDni());
        empleado.getContacto().setCalle(dto.getDireccionCompleta().split(",")[0].trim());
        empleado.getContacto().setNroPuerta(dto.getDireccionCompleta().split(",")[1].trim());
        empleado.getContacto().setLocalidad(dto.getDireccionCompleta().split(",")[2].trim());
        return empleado;
    }

    public static List<EmpleadoDTO> mapToEmpleadoDtoList(List<Empleado> empleados) {
        return empleados == null ? List.of() : empleados.stream().map(EmpleadoMapper::mapToEmpleadoDto).collect(Collectors.toList());
    }

    public static List<Empleado> mapToEmpleadoEntityList(List<EmpleadoDTO> dtos) {
        return dtos == null ? List.of() : dtos.stream().map(EmpleadoMapper::mapToEmpleadoEntity).collect(Collectors.toList());
    }
}
