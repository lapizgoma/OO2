package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;
import java.util.Set;

import oo2.grupo19.SistemaTickets.services.IUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.UsuarioMapper;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

@Service
@Log4j2
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuario usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(IUsuario usuarioRepository, PasswordEncoder password) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = password;
    }

    @Override
    @Transactional
    public void delete(String email) {
        try {
            usuarioRepository.findByContactoEmail(email);
        } catch (Exception e) {
            throw new NotFoundException("No se ha podido eliminar el Usuario");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<UsuarioDTO> findAll() {
        return UsuarioMapper.mapToUsuarioDtoSet(usuarioRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        return UsuarioMapper.mapToUsuarioDto(
            usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el usuario con el id: " + id))
        );
    }

    @Override
    @Transactional
    public void save(UsuarioDTO usuariodto) {
        if(usuariodto == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }
        Optional<Usuario> clienteOpt = usuarioRepository.findByContactoEmail(usuariodto.getEmail());
        if(clienteOpt.isPresent()) {
            usuariodto.setId(clienteOpt.get().getId());
            Usuario usuario = UsuarioMapper.mapToUsuarioEntity(usuariodto);
            usuarioRepository.save(usuario);
        } else {
        Usuario usuario = UsuarioMapper.mapToUsuarioEntity(usuariodto);
        String passwordHash = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordHash);
        usuarioRepository.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDTO findByEmail(String email) {
        return UsuarioMapper.mapToUsuarioDto(
            usuarioRepository.findByContactoEmail(email)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el usuario con el email: " + email))
        );
    }

    @Override
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usOptional = usuarioRepository.findByContactoEmail(email);
        return usOptional.isPresent() && passwordEncoder.matches(password, usOptional.get().getPassword());
    }
}
