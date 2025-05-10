package oo2.grupo19.SistemaTickets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import oo2.grupo19.SistemaTickets.entities.Empleado;

public interface IEmpleado extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByContactoEmail(String email);
    
    @Query("SELECT e FROM Empleado e")
    List<Empleado> findAllEmpleados();

    Optional<Empleado> findByNombre(String nombre);
}