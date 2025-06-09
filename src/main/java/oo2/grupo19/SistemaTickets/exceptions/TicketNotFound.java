package oo2.grupo19.SistemaTickets.exceptions;

public class TicketNotFound extends RuntimeException {
    public TicketNotFound(String menssage){
        super(menssage);
    }
}
