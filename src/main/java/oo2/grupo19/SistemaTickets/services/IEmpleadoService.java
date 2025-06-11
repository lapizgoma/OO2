package oo2.grupo19.SistemaTickets.services;

<<<<<<< HEAD
import oo2.grupo19.SistemaTickets.entities.Empleado;

import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {
    void delete(Long id);
    List<Empleado> findAll();
    Optional<Empleado> findById(Long id);
    void save(Empleado object);
    List<Empleado> findAllEmpleados();
    List<Empleado> findAllByDeletedFalse();
    Optional<Empleado> findByNombre(String nombre);
}
=======
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Empleado;


public interface IEmpleadoService extends IService<Empleado> {
    public Optional<Empleado> traerEmpleado(String email);
	public List<Empleado> traerEmpleados();
	public List<Empleado> traerEmpleadosActivos();
    public void agregarEmpleado(Empleado empleado);
    public void darBajaEmpleado(Long id);
    public List<Empleado> listarTodos();

}

>>>>>>> feature/wip/spring-co-mati
