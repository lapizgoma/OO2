package oo2.grupo19.SistemaTickets.services;


import oo2.grupo19.SistemaTickets.controllers.apirest.dto.PersonaJuridicaCreateRequestRecord;
import oo2.grupo19.SistemaTickets.controllers.apirest.dto.PersonaJuridicaResponseRecord;
import oo2.grupo19.SistemaTickets.dto.personaJuridica.PersonaJuridicaDTO;

public interface IPersonaJuridicaService extends IService<PersonaJuridicaDTO> 
{
    PersonaJuridicaDTO crearPersonaJuridica (PersonaJuridicaDTO personaJuridicaDTO);
    PersonaJuridicaResponseRecord crearPersonaJuridica (PersonaJuridicaCreateRequestRecord record);
    PersonaJuridicaDTO findByCode (String code);
}
