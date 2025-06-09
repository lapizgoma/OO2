package oo2.grupo19.SistemaTickets.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
public class EmpleadoServiceImpl implements IService<Empleado> {
    
   private final IEmpleado empleadoRepository;
    private final ITicket ticketRepository;
    private final UsuarioServiceImpl usuarioService;

    public EmpleadoServiceImpl(IEmpleado empleadoRepository, ITicket ticketRepository, UsuarioServiceImpl usuarioService) {
        this.empleadoRepository = empleadoRepository;
        this.ticketRepository = ticketRepository;
        this.usuarioService = usuarioService;
    }


    @Override
    @Transactional
    public void delete(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new UserCustomExceptions.EmpleadoNotFoundException("No se ha encontrado el empleado con ese id");
        }
        empleadoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> findById(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isEmpty()) {
            throw new UserCustomExceptions.EmpleadoNotFoundException("No se ha encontrado el empleado con ese id");
        }
        return empleado;
    }

    @Override
    @Transactional
    public void save(Empleado object) {
        if(object == null) {
            throw new UserCustomExceptions.EmpleadoNullException("El empleado no puede ser null");
        }
        if(object.getId() != null && object.getId() > 0){
            object.setTickets(new HashSet<>(ticketRepository.findAll()));
        }
        empleadoRepository.save(object);
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> traerEmpleado(String email){
        try{
            return empleadoRepository.findByContactoEmail(email);
        }catch(Exception e){
            throw new UserCustomExceptions.EmpleadoNotFoundException("No se ha encontrado el empleado con ese email");
        }
    }

    @Transactional(readOnly = true)
	public List<Empleado> traerEmpleados(){
        try{
            return empleadoRepository.findAllEmpleados();
        }catch(Exception e){
            throw new UserCustomExceptions.EmpleadoListException("No se ha podido mostrar los empleados");
        }
    }

    @Transactional(readOnly = true)
	public List<Empleado> traerEmpleadosActivos(){
        try{
            return empleadoRepository.findAllByDeletedFalse();
        }catch(Exception e){
            throw new UserCustomExceptions.EmpleadoListException("No se ha podido mostrar los empleados activos");
        }
    }
    
    public void agregarEmpleado(Empleado empleado){
        try{
            long ultimoLegajo;

            if(traerEmpleados().isEmpty()) {
                ultimoLegajo = 10000;
            } else {
                ultimoLegajo = Long.parseLong(traerEmpleados().getLast().getNroLegajo()) + 1;
            }
            empleado.setNroLegajo(Long.toString(ultimoLegajo));
            usuarioService.registrarUsuario(empleado);
        }catch(Exception e){
            throw new UserCustomExceptions.EmpleadoPersistException("No se ha podido persistir el empleado");
        }
    }

    @Transactional
    public void darBajaEmpleado(Long id){
        Optional <Empleado> empleado = findById(id);
        if(empleado.isPresent()){
            empleado.get().darDeBaja();
        }else{
            throw new UserCustomExceptions.EmpleadoNotFoundException("No se ha encontrado el empleado con ese id");
        }
    }

    @Transactional
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }


}
