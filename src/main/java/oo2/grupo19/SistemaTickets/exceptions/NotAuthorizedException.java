package oo2.grupo19.SistemaTickets.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String menssage){
        super(menssage);
    }
}
