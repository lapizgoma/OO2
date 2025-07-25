package oo2.grupo19.SistemaTickets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public interface IPersonaJuridica extends JpaRepository<PersonaJuridica,Long> {

    Optional<PersonaJuridica> findByCuit(String cuit);

    @Query("SELECT pj FROM PersonaJuridica pj WHERE pj.deleted = false")
    @Override
    List<PersonaJuridica> findAll();

    
    @Query("SELECT pj FROM PersonaJuridica pj WHERE pj.codigoAcceso = :codigoAcceso AND pj.deleted = false")
    Optional<PersonaJuridica> findByCodigoAcceso (String codigoAcceso);
}
