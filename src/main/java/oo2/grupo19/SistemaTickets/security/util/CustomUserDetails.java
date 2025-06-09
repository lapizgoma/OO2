package oo2.grupo19.SistemaTickets.security.util;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public class CustomUserDetails implements UserDetails {
   
    private final Usuario usuario;
    
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }

    // Getters estándar de UserDetails
    @Override
    public String getUsername() {
        return usuario.getContacto().getEmail(); // Para login
    }

    // Nuevo getter para el nombre
    public String getNombre() {
        return usuario.getNombre();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario instanceof Empleado empleado) {
            return List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRole().getEstado()));
        } else if (usuario instanceof Cliente) {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        return List.of();
    }


    @Override
    public String getPassword() {
        return usuario.getPassword();
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}