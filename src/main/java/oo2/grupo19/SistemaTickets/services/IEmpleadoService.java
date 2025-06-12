package oo2.grupo19.SistemaTickets.services;

import java.util.List;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Empleado;


public interface IEmpleadoService extends IService<Empleado> {
    Optional<Empleado> traerEmpleado(String email);
	List<Empleado> traerEmpleados();
	List<Empleado> traerEmpleadosActivos();
    void agregarEmpleado(Empleado empleado);
    void darBajaEmpleado(Long id);
    List<Empleado> listarTodos();
    List<Empleado> findAllEmpleados();
    List<Empleado> findAllByDeletedFalse();
    Optional<Empleado> findByNombre(String nombre);
}

