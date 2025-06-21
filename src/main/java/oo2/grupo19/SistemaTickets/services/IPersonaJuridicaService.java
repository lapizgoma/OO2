package oo2.grupo19.SistemaTickets.services;


import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;

public interface IPersonaJuridicaService extends IService<PersonaJuridicaDTO> 
{
    PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO);
    PersonaJuridicaDTO findByCode (String code);
}
