package oo2.grupo19.SistemaTickets.services;

import java.util.Map;

public interface EmailService {
    void enviarCorreoPlano(String destinatario, String asunto, String mensaje);
    void enviarCorreoHtml(String destinatario, String asunto, String nombrePlantilla, Map<String, Object> datos);
}
