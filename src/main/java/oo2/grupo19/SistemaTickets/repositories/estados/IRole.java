package oo2.grupo19.SistemaTickets.repositories.estados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oo2.grupo19.SistemaTickets.entities.estados.Role;

@Repository
public interface IRole extends JpaRepository<Role,Long> {

}
