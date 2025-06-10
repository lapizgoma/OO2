package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import oo2.grupo19.SistemaTickets.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.UsuarioServiceException;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

@Service
@Qualifier("usuarioService")
@Log4j2
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuario usuarioRepository;
    private final IEmpleado empleadoRepository;
    private final ICliente clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(IUsuario usuarioRepository, IEmpleado empleadoService, PasswordEncoder password,ICliente clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoService;
        this.passwordEncoder = password;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new UsuarioServiceException("No se ha podido eliminar el Usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new UsuarioServiceException("No se ha podido mostrar la lista de usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        try {
            return usuarioRepository.findById(id);
        } catch (Exception e) {
            throw new UsuarioServiceException("No se ha podido mostrar el usuario", e);
        }
    }

    @Override
    @Transactional
    public void save(Usuario object) {
        try {
            Optional<Usuario> user = usuarioRepository.findByContactoEmail(object.getContacto().getEmail());
            if (user.isEmpty()) {
                object.asignarContactoUsuario();
                usuarioRepository.save(object);
            } else {
                log.info("El usuario ya existe en la bd!");
            }
        } catch (Exception e) {
            throw new UsuarioServiceException("No se ha podido actualizar/insertar el usuario", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByEmail(String email) {
        try {
            return usuarioRepository.findByContactoEmail(email);
        } catch (Exception e) {
            throw new UsuarioServiceException("No se ha encontrado el usuario con ese email", e);
        }
    }

    @Override
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usOptional = usuarioRepository.findByContactoEmail(email);
        return usOptional.isPresent() && passwordEncoder.matches(password, usOptional.get().getPassword());
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        String passwordHash = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordHash);
        String rolUser = usuario.getRoles().stream().findAny().orElseThrow().toString();
        if(rolUser.equals("USER")){
            usuario = usuario.toCliente();
        }
        if (usuario instanceof Cliente c) {
            clienteRepository.save(c);
        } else if (usuario instanceof Empleado e) {
            empleadoRepository.save(e);
        }
    }

}
