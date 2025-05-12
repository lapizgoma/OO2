package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.services.IService;

public class EmpleadoServiceImpl implements IService<Empleado> {
    
    private final IEmpleado empleadoRepository;

    public EmpleadoServiceImpl(IEmpleado empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public void save(Empleado object) {
        empleadoRepository.save(object);
    }

    public Optional<Empleado> traerEmpleado(String email){
        try{
            return empleadoRepository.findByContactoEmail(email);
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
