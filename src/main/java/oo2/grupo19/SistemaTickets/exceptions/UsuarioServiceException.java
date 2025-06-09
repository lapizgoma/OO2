package oo2.grupo19.SistemaTickets.exceptions;

public class UsuarioServiceException extends RuntimeException {
    public UsuarioServiceException(String message) {
        super(message);
    }
    public UsuarioServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
