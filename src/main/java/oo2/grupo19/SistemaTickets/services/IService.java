package oo2.grupo19.SistemaTickets.services;

import java.util.Set;

public interface IService<T> {
    Set<T> findAll();
    T findById(Long id);
    void save(T object);
    void delete(String id);
}
