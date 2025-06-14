package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO mapToUsuarioDto(oo2.grupo19.SistemaTickets.entities.Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getContacto().getEmail());
        return dto;
    }

    public static Usuario mapToUsuarioEntity(UsuarioDTO usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        if (usuario.getContacto() != null) {
            usuario.getContacto().setEmail(usuarioDto.getEmail());
        }
        return usuario;
    }

    public static Set<UsuarioDTO> mapToUsuarioDtoSet(List<Usuario> usuarios) {
        return usuarios == null ? Collections.emptySet() : usuarios.stream()
                .map(UsuarioMapper::mapToUsuarioDto)
                .collect(Collectors.toSet());
    }
}
