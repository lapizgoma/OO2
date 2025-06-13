package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public interface IPersonaJuridicaService extends IService<PersonaJuridica>
{
    PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO);
    PersonaJuridicaDTO buscarPersonaJuridica (String code);    
}
