package oo2.grupo19.SistemaTickets.services;


import oo2.grupo19.SistemaTickets.dto.PersonaJuridicaDTO;

public interface IPersonaJuridicaService extends IService<PersonaJuridicaDTO> {
    PersonaJuridicaDTO findByCode(String code);
}
