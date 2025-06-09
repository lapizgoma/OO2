package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.security.util.CustomUserDetails;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuario usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByContactoEmail(email);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            return new CustomUserDetails(usuario);
        }
        throw new UsernameNotFoundException("Usuario no encontrado: ");
    }

    
}
