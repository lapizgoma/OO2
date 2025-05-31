package oo2.grupo19.SistemaTickets.exceptions;

public class UserAlreadyAuthenticatedException extends RuntimeException{
        public UserAlreadyAuthenticatedException(String menssage){
        super(menssage);
    }
}
