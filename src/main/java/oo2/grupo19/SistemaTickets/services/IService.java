package oo2.grupo19.SistemaTickets.services;

import java.util.List;
import java.util.Optional;

public interface IService <T> {

    List<T> findAll();
    Optional<T> findById(Long id);
    void save(T object);
    void delete(Long id);
    
}
