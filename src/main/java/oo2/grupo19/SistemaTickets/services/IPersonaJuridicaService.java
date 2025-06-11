package oo2.grupo19.SistemaTickets.services;


import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public interface IPersonaJuridicaService extends IService<PersonaJuridica> {
    public PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO);
    public PersonaJuridicaDTO buscarPersonaJuridica (String code);
    
}
