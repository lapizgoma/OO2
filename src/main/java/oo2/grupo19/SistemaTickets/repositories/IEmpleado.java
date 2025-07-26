package oo2.grupo19.SistemaTickets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.Empleado;

public interface IEmpleado extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByContactoEmail(String email);

    List<Empleado> findAllByDeletedFalse();

    Optional<Empleado> findByNombre(String nombre);

    Boolean existsByContactoEmail(String email);

    Boolean existsByDni(String dni);
    
}