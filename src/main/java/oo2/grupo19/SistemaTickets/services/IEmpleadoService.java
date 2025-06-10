package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface IEmpleado {
    void delete(Long id);
    List<Empleado> findAll();
    Optional<Empleado> findById(Long id);
    void save(Empleado object);
    List<Empleado> findAllEmpleados();
    List<Empleado> findAllByDeletedFalse();
    Optional<Empleado> findByNombre(String nombre);
}
