package oo2.grupo19.SistemaTickets.services;

import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IUsuarioService extends IService<Usuario>{
    public Optional<Usuario> findByEmail(String email);
    public boolean validarCredenciales(String email, String password);
    public void registrarUsuario(Usuario usuario);
}
