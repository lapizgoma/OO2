package oo2.grupo19.SistemaTickets.repositories.estados;

import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oo2.grupo19.SistemaTickets.entities.estados.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRole extends JpaRepository<Role,Long> {
    List<Role> findByTypeNot(RoleType type);
    Optional<Role> findByType(RoleType type);
}
