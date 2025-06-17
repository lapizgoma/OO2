package oo2.grupo19.SistemaTickets.services;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface EmailService {
    void enviarCorreoPlano(String destinatario, String asunto, String mensaje);
    void enviarCorreoHtml(String destinatario, String asunto, String nombrePlantilla, Map<String, Object> datos);
}
