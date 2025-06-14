package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;

public interface IUsuarioService extends IService<UsuarioDTO>{
    UsuarioDTO findByEmail(String email);
    boolean validarCredenciales(String email, String password);
}
