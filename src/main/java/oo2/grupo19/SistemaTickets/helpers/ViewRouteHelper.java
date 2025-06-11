package oo2.grupo19.SistemaTickets.helpers;

public class ViewRouteHelper {

    public static final String INDEX = "home";

    public static final String INDEX_ADMIN = "admin/home";
    
    public static final String LOGIN = "auth/login";
    public static final String REGISTER = "auth/register";
    
    public static final String INDEX_USER = "cliente/home";
    public static final String CONFIRMATION_QUESTION = "cliente/confirmationQuestion";
    public static final String REMOVAL_SUCCESS = "cliente/accountRemovalSuccess";

    public static final String INDEX_EMPLOYEE = "empleado/home";
    public static final String EMPLEADO_REGISTER = "empleado/registerEmpleado";
    public static final String LISTAR_EMPLEADOS = "empleado/listarEmpleados";
    public static final String EMPLEADO_REGISTRADO = "empleado/registerSuccessfull";
    public static final String EMPLEADO_BORRADO = "empleado/deletedSuccessfull";

    public static final String FORM_PERSONA_JURIDICA = "personaJuridica/create";
    public static final String VIEW_PERSONA_JURIDICA = "personaJuridica/personaJuridica";

    public static final String TICKET_SUCCESS = "ticket/ticketSuccess";
    public static final String FORM_TICKET = "ticket/formTicket";
    public static final String FORM_LISTAR_TICKET = "ticket/ver-tickets";
    public static final String VIEW_TICKET = "ticket/ticketView";
    public static final String FORM_CREATE_INTERVENCION = "ticket/intervencion/create";
    public static final String INTERVENCION_SUCCESS = "ticket/intervencion/intervencionSuccess";
    public static final String TICKET_UPDATE_STATUS = "ticket/formTicketUpdateStatus";

    public static final String ERROR_404 = "errors/404";
    public static final String ERROR_INDEX = "errors/error";
 }
