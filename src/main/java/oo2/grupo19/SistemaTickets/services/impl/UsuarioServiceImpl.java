package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Qualifier("usuarioService")
@Log4j2
public class UsuarioServiceImpl implements IService<Usuario> {

    private final IUsuario usuarioRepository;
    private final IEmpleado empleadoRepository;

    public UsuarioServiceImpl(IUsuario usuarioRepository, IEmpleado empleadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoRepository;
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
            Optional<Usuario> user = usuarioRepository.findByEmail(object.getEmail());
            if(user.isEmpty()){
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
            return usuarioRepository.findByEmail(email);
        }catch(Error e){
            throw new RuntimeException("No se ha encontrado el usuario con ese email");
        }
    }
	
	public Optional<Empleado> traerEmpleado(String email){
        try{
            return empleadoRepository.findByEmail(email);
        }catch(Error e){
            throw new RuntimeException("No se ha encontrado el empleado con ese email");
        }
    }
	
	public List<Empleado> traerEmpleados(){
        try{
            return empleadoRepository.findAllEmpleados();
        }catch(Error e){
            throw new RuntimeException("No se hay podido mostrar los empleados");
        }
    }
    
	void agregarEmpleado(Empleado empleado){
        try{
            empleadoRepository.save(empleado);
        }catch(Error e){
            throw new RuntimeException("No se ha podido persistir el empleado");
        }
    }

}
