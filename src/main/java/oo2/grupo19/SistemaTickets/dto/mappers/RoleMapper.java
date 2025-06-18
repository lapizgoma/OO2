package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.RoleDTO;
import oo2.grupo19.SistemaTickets.entities.estados.Role;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;

public final class RoleMapper {
    public static RoleDTO mapRoleToDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setType(role.getType().toString());
        return dto;
    }

    public static Role mapDtoToRole(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        Role role = new Role();
        switch (dto.getType()) {
            case "ADMIN":
                role.setType(RoleType.ADMIN);
                break;
            case "EMPLEADO":
                role.setType(RoleType.EMPLOYEE);
                break;
            case "CLIENTE":
                role.setType(RoleType.CUSTOMER);
                break;
            default:
                throw new IllegalArgumentException("Tipo de rol desconocido: " + dto.getType());
        }
        return role;
    }

    public static Set<RoleDTO> mapToRoleDtoSet(Set<Role> entities) {
        return entities == null ? Set.of() : entities.stream()
                .map(RoleMapper::mapRoleToDto)
                .collect(Collectors.toSet());
    }

    public static Set<Role> mapToRoleEntitySet(Set<RoleDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream()
                .map(RoleMapper::mapDtoToRole)
                .collect(Collectors.toSet());
    }
}
