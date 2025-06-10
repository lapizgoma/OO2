package oo2.grupo19.SistemaTickets.exceptions;

public class UserCustomExceptions extends RuntimeException {
    public UserCustomExceptions(String message) {
        super(message);
    }

    // Empleado exceptions
    public static class EmpleadoNotFoundException extends UserCustomExceptions {
        public EmpleadoNotFoundException(String message) { super(message); }
    }
    public static class EmpleadoNullException extends UserCustomExceptions {
        public EmpleadoNullException(String message) { super(message); }
    }
    public static class EmpleadoListException extends UserCustomExceptions {
        public EmpleadoListException(String message) { super(message); }
    }
    public static class EmpleadoPersistException extends UserCustomExceptions {
        public EmpleadoPersistException(String message) { super(message); }
    }

    // User/PersonaJuridica/Cliente exceptions
    public static class UserAlreadyExistException extends RuntimeException {
        public UserAlreadyExistException(String message) { super(message); }
    }
    public static class UserAlreadyAuthenticatedException extends RuntimeException {
        public UserAlreadyAuthenticatedException(String message) { super(message); }
    }
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    public static class PersonaJuridicaAlreadyExists extends RuntimeException {
        public PersonaJuridicaAlreadyExists(String message) { super(message); }
    }
    public static class PersonaJuridicaNotFound extends RuntimeException {
        public PersonaJuridicaNotFound(String message) { super(message); }
    }
    public static class ClienteAlreadyExistsException extends RuntimeException {
        public ClienteAlreadyExistsException(String message) { super(message); }
    }
    public static class ClienteServiceException extends RuntimeException {
        public ClienteServiceException(String message, Throwable cause) { super(message, cause); }
    }
}
