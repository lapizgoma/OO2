package test;

import negocio.UsuarioABM;
import negocio.TicketABM;

import java.time.LocalDateTime;

import enums.EstadosTicket;
import model.Mensaje;
import model.Ticket;
import model.Usuario;
import negocio.MensajeABM;

public class TestCU004 {
    public static void main(String[] args) {
        // Objetivo: Usuario manda mensaje y es asignado al ticket correspondiente.

        // INICIALIZACIÓN
        UsuarioABM usuarioABM = new UsuarioABM ();
        TicketABM ticketAbm = new TicketABM ();
        MensajeABM mensajeABM = new MensajeABM ();

        Usuario usuario_correcto = new Usuario (
            "CU-004@correcto.com", "apellido", "nombre", null, false
            );
        Usuario usuario_incorrecto = new Usuario (
            "CU-004@incorrecto.com", "apellido", "nombre", null, false
        );
        usuarioABM.agregar(usuario_correcto);
        usuarioABM.agregar(usuario_incorrecto);


        Ticket ticket_correcto = new Ticket (usuario_correcto, "Este Ticket es el correcto", EstadosTicket.PENDIENTE);
        // Ticket ticket_incorrecto = new Ticket (usuario_incorrecto, "Este Ticket NO es el correcto", EstadosTicket.PENDIENTE);

        ticketAbm.agregar(ticket_correcto);
        // ticketAbm.agregar(ticket_incorrecto);


        Mensaje mensaje1 = new Mensaje ("Este mensaje debería ser aceptado", LocalDateTime.now (), usuario_correcto);
        Mensaje mensaje2 = new Mensaje ("Este mensaje NO debería ser aceptado", LocalDateTime.now (), usuario_incorrecto);

        // ==== TEST ====
        try 
        {
            // agregar debe recibir Mensaje y Ticket
            mensajeABM.agregar(mensaje1);
            System.out.println ("Mensaje se debía añadir y se añadió.");
        }
        catch (Exception ex) 
        {
            System.out.println ("Mensaje se debía añadir y NO se añadió.");
        }

        try 
        {
            mensajeABM.agregar(mensaje2);
            System.out.println ("Mensaje NO se debía añadir y se añadió.");
        }
        catch (Exception ex) 
        {
            System.out.println ("Mensaje NO se debía añadir y NO se añadió.");
        }

        // OBSERVACIONES:
        /*
         * ¿cómo se determina a qué ticket corresponde el mensaje si el Usuario está asignado a varios Tickets?
         * 
         */
    }
}
