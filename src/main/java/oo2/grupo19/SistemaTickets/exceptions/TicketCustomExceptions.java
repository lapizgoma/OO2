package oo2.grupo19.SistemaTickets.exceptions;

public class TicketCustomExceptions extends RuntimeException{
    public static class TicketNotFoundException extends RuntimeException {
        public TicketNotFoundException(String message) {
            super(message);
        }
        public TicketNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoIntervencionDeleteException extends RuntimeException {
        public EstadoIntervencionDeleteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoIntervencionListException extends RuntimeException {
        public EstadoIntervencionListException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoIntervencionNotFoundException extends RuntimeException {
        public EstadoIntervencionNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoIntervencionSaveException extends RuntimeException {
        public EstadoIntervencionSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoTicketDeleteException extends RuntimeException {
        public EstadoTicketDeleteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoTicketListException extends RuntimeException {
        public EstadoTicketListException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoTicketNotFoundException extends RuntimeException {
        public EstadoTicketNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class EstadoTicketSaveException extends RuntimeException {
        public EstadoTicketSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class IntervencionDeleteException extends RuntimeException {
        public IntervencionDeleteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class IntervencionListException extends RuntimeException {
        public IntervencionListException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class IntervencionNotFoundException extends RuntimeException {
        public IntervencionNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class IntervencionSaveException extends RuntimeException {
        public IntervencionSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class PrioridadDeleteException extends RuntimeException {
        public PrioridadDeleteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class PrioridadListException extends RuntimeException {
        public PrioridadListException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class PrioridadNotFoundException extends RuntimeException {
        public PrioridadNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class PrioridadSaveException extends RuntimeException {
        public PrioridadSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class TicketDeleteException extends RuntimeException {
        public TicketDeleteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class TicketListException extends RuntimeException {
        public TicketListException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class TicketSaveException extends RuntimeException {
        public TicketSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
