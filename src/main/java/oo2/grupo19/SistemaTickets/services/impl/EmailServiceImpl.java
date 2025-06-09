package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import oo2.grupo19.SistemaTickets.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${mail.from}")
    private String mailFrom;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void enviarCorreoPlano(String destinatario, String asunto, String mensaje) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinatario);
            email.setSubject(asunto);
            email.setText(mensaje);
            email.setFrom(mailFrom);
            mailSender.send(email);
        } catch (Exception e) {
            // Aquí podrías loguear el error o lanzar una excepción personalizada
            throw new RuntimeException("Error al enviar correo plano: " + e.getMessage(), e);
        }
    }

    @Override
    public void enviarCorreoHtml(String destinatario, String asunto, String nombrePlantilla, Map<String, Object> datos) {
        Context context = new Context();
        context.setVariables(datos);
        String cuerpo = templateEngine.process(nombrePlantilla, context);

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, true); // true = HTML
            helper.setFrom(mailFrom);

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            // Aquí podrías loguear el error o lanzar una excepción personalizada
            throw new RuntimeException("Error al enviar correo HTML: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al enviar correo HTML: " + e.getMessage(), e);
        }
    }

}
