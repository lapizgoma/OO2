package oo2.grupo19.SistemaTickets.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUsuario usuarioRepository;

    public UserDetailsServiceImpl(IUsuario usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Intentando cargar usuario con email: {}", email);

        Usuario usuario = usuarioRepository.findByContactoEmail(email)
            .orElseThrow(() -> {
                log.warn("Usuario no encontrado con email: {}", email);
                return new UsernameNotFoundException("Usuario no encontrado con email: " + email);
            });

        String roles = usuario.getRoles().stream()
            .map(role -> role.getType().name())
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
        log.info("Usuario encontrado: {} con roles: {}", usuario.getNombre(), roles);

        // Usuario ya implementa UserDetails, solo lo devolvemos
        return usuario;
    }
}