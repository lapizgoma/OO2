package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Map;
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

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void enviarCorreoPlano(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);
        email.setFrom("sistematicketv1@gmail.com");
        mailSender.send(email);
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
            helper.setFrom("sistematicketv1@gmail.com");

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
