package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDeIntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public final class EmpleadoMapper {
    public static EmpleadoDeIntervencionDTO mapToEmpleadoIntervencionDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }
        EmpleadoDeIntervencionDTO dto = new EmpleadoDeIntervencionDTO();
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        return dto;
    }

    public static EmpleadoDTO mapToEmpleadoDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setNroLegajo(empleado.getNroLegajo());
        dto.setDni(empleado.getDni());
        dto.setContacto(ContactoMapper.mapToContactoDto(empleado.getContacto()));
        return dto;
    }

    public static Empleado mapToEmpleadoEntity(EmpleadoDTO dto, Empleado empleado) {
        if (dto == null) return null;
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setPassword(dto.getPassword());
        empleado.setNroLegajo(dto.getNroLegajo());
        empleado.setDni(dto.getDni());
        empleado.setContacto(ContactoMapper.mapToContactoEntity(dto.getContacto(), new Contacto()));
        return empleado;
    }

    public static EmpleadoDTO empleadoDtoRecordToEmpleadoDto(oo2.grupo19.SistemaTickets.controllers.apirest.dto.EmpleadoDTO empleadoDtoRecord){
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setApellido(empleadoDtoRecord.apellido());
        empleadoDTO.setNombre(empleadoDtoRecord.nombre());
        empleadoDTO.setContacto(empleadoDtoRecord.contacto());
        empleadoDTO.setDni(empleadoDtoRecord.dni());
        empleadoDTO.setPassword(empleadoDtoRecord.password());
        empleadoDTO.setRole(empleadoDtoRecord.role());
        return empleadoDTO;
    }

    public static Set<EmpleadoDTO> mapToEmpleadoDtoSet(List<Empleado> empleados) {
        return empleados == null ? Set.of() : empleados.stream().map(EmpleadoMapper::mapToEmpleadoDto).collect(Collectors.toSet());
    }
}
