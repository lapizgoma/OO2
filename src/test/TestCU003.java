package test;

import enums.EnumRole;
import enums.EstadosTicket;
import model.Empleado;
import model.Ticket;
import model.Usuario;
import negocio.TicketABM;
import negocio.UsuarioABM;

public class TestCU003 {
    public static void main(String[] args) {
        // Objetivo: asginar Empleados a Tickets

        // INICIALIZACIÓN
        UsuarioABM usuarioABM = new UsuarioABM();
        TicketABM ticketAbm = new TicketABM();

        Usuario usuario_cliente = new Usuario(
                "CU-003@cliente.com", "apellido", "nombre", null, false);

        Empleado emple_1 = new Empleado(
                "CU-003@empleado.com", "empleado 1", "nombre",
                null, false,
                EnumRole.EMPLEADO);
        Empleado emple_2 = new Empleado(
                "CU-003@empleado.com", "empleado 2", "nombre",
                null, false,
                EnumRole.EMPLEADO);

        usuarioABM.agregarEmpleado(emple_1);
        usuarioABM.agregarEmpleado(emple_2);

        // ==== TEST ====
        Ticket ticket = new Ticket(usuario_cliente, "A este Ticket se le asignarán Empleados", EstadosTicket.PENDIENTE);

        // Empleado es asignado localmente y luego guardado en BD
        ticket.agregarEmpleado(emple_1);
        ticketAbm.agregar(ticket);

        // Empleado es asignado localmente y luego la relación entre ambos es actualizada en la BD
        ticket.agregarEmpleado(emple_2);
        ticketAbm.actualizar(ticket);
    }
}
