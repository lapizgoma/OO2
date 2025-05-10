package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

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
            
            log.info("Usuario: " + usuario.toString());
            String rol = "USUARIO"; // Valor por defecto

            if (usuario instanceof Cliente) {
                rol = "CLIENTE";
            } else if (usuario instanceof Empleado) {
                rol = ((Empleado) usuario).getRole().toString(); // o algún valor por defecto como "EMPLEADO"
            }

            return User.builder()
                .password(usuario.getPassword())
                .username(usuario.getContacto().getEmail())
                .roles(rol)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
        }


        throw new UsernameNotFoundException("Usuario no encontrado: ");
    }

    
}
