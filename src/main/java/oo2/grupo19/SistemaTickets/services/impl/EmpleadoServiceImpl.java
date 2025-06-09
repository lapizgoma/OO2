package oo2.grupo19.SistemaTickets.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
public class EmpleadoServiceImpl implements IService<Empleado> {
    
    private final IEmpleado empleadoRepository;
    private final ITicket ticketRepository;

    public EmpleadoServiceImpl(IEmpleado empleadoRepository, ITicket ticketRepository) {
        this.empleadoRepository = empleadoRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
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
        return empleadoRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Empleado object) {
        if(object.getId() != null && object.getId() > 0){
            object.setTickets(new HashSet<>(ticketRepository.findAll()));
        }
        empleadoRepository.save(object);
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> traerEmpleado(String email){
        try{
            return empleadoRepository.findByContactoEmail(email);
        }catch(Error e){
            throw new RuntimeException("No se ha encontrado el empleado con ese email");
        }
    }

    @Transactional(readOnly = true)
	public List<Empleado> traerEmpleados(){
        try{
            return empleadoRepository.findAllEmpleados();
        }catch(Error e){
            throw new RuntimeException("No se hay podido mostrar los empleados");
        }
    }
    
    @Transactional
	void agregarEmpleado(Empleado empleado){
        try{
            save(empleado);
        }catch(Error e){
            throw new RuntimeException("No se ha podido persistir el empleado");
        }
    }

}
