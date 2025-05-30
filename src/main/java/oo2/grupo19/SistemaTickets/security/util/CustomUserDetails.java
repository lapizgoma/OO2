package oo2.grupo19.SistemaTickets.security.util;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final String nombre; // Nuevo campo
    private final String rol;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public CustomUserDetails(String email, String password, String nombre, String rol) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    // Getters estándar de UserDetails
    @Override
    public String getUsername() {
        return email; // Para login
    }

    // Nuevo getter para el nombre
    public String getNombre() {
        return nombre;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getPassword() {
        return password;
    }

}