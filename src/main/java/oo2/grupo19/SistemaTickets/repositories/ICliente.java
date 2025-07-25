package oo2.grupo19.SistemaTickets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICliente extends JpaRepository<Cliente,Long> {
    @Query("SELECT c FROM Cliente c WHERE c.contacto.email = :email")
    Optional<Cliente> findByContactoEmail(@Param("email") String email);

    @Query("SELECT c FROM Cliente c WHERE c.organizacion.codigoAcceso = :codigoAcceso")
    List<Cliente> findByCodigoPersonaJuridica(@Param("codigoAcceso") String codigo);
}
