package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUsuario usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Intentando cargar usuario con email: {}", email);
        
        Optional<Usuario> usuarioOptional = usuarioRepository.findByContactoEmail(email);
        
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            log.info("Usuario encontrado: {} con roles: {}", 
                    usuario.getNombre(), 
                    usuario.getRoles().stream()
                            .map(role -> role.getType().name())
                            .toArray());
            
            // Usuario ya implementa UserDetails, solo lo devolvemos
            return usuario;
        }
        
        log.warn("Usuario no encontrado con email: {}", email);
        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}