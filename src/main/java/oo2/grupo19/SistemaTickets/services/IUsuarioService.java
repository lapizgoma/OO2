package oo2.grupo19.SistemaTickets.services;

<<<<<<< HEAD
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

=======
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IUsuarioService extends IService<Usuario>{
    public Optional<Usuario> findByEmail(String email);
    public boolean validarCredenciales(String email, String password);
    public void registrarUsuario(Usuario usuario);
>>>>>>> feature/wip/spring-co-mati
}
