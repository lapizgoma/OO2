package oo2.grupo19.SistemaTickets.services;

import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IUsuarioService extends IService<Usuario>{
    Optional<Usuario> findByEmail(String email);
    boolean validarCredenciales(String email, String password);
    void registrarUsuario(Usuario usuario);
}
