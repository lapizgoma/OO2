package oo2.grupo19.SistemaTickets.services;

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

