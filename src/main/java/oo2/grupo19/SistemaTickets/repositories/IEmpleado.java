package oo2.grupo19.SistemaTickets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oo2.grupo19.SistemaTickets.entities.Empleado;

public interface IEmpleado extends JpaRepository<Empleado, Long> {
    @Query("SELECT e FROM Empleado e WHERE e.email = :email")
    Optional<Empleado> findByEmail(@Param("email") String email);
    
    @Query("SELECT e FROM Empleado e")
    List<Empleado> findAllEmpleados();
}