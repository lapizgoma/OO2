package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    void delete(Long id);
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    void save(Usuario object);
    Optional<Usuario> findByEmail(String email);
    boolean validarCredenciales(String email, String password);
    void registrarUsuario(Usuario usuario);

}
