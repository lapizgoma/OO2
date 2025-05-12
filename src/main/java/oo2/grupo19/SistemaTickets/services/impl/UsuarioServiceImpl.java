package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Qualifier("usuarioService")
@Log4j2
public class UsuarioServiceImpl implements IService<Usuario> {

    private final IUsuario usuarioRepository;
    private final IEmpleado empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICliente clienteRepository;

    public UsuarioServiceImpl(IUsuario usuarioRepository, IEmpleado empleadoRepository, PasswordEncoder password, ICliente clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = password;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void delete(Long id) {
       try{
            usuarioRepository.deleteById(id);
       }catch(Error e){
        throw new RuntimeErrorException(e,"No se ha podido eliminar el Usuario");
       }
    }

    @Override
    public List<Usuario> findAll() {
        try{
            return usuarioRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e,"No se ha podido mostrar la lista de usuario");
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        try{
            return usuarioRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e,"No se ha podido mostrar el usuario");
        }
    }

    @Override
    public void save(Usuario object) {
        try{
            Optional<Usuario> user = usuarioRepository.findByContactoEmail(object.getContacto().getEmail());
            if(user.isEmpty()){
                object.asignarContactoUsuario();
                usuarioRepository.save(object);
            }else{
                log.info("El usuario ya existe en la bd!");
            }
        }catch(Error e){
            throw new RuntimeErrorException(e,"No se ha podido actualizar/insertar el usuario");
        }
    }

    public Optional<Usuario> findByEmail(String email){
        try{
            return usuarioRepository.findByContactoEmail(email);
        }catch(Error e){
            throw new RuntimeException("No se ha encontrado el usuario con ese email");
        }
    }

    public boolean validarCredenciales(String email, String password){
        Optional<Usuario> usOptional = usuarioRepository.findByContactoEmail(email);
        return usOptional.isPresent() && passwordEncoder.matches(password, usOptional.get().getPassword());
    }

    public void registrarUsuario(Usuario usuario) {
        String passwordHash = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordHash);
        if(usuario instanceof Cliente c){
            clienteRepository.save(c);
        }else if(usuario instanceof Empleado e){
            empleadoRepository.save(e);
        }
    }

}
