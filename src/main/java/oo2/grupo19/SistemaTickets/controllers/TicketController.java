package oo2.grupo19.SistemaTickets.controllers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.helpers.ViewRouteHelper;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoIntervencion;
import oo2.grupo19.SistemaTickets.repositories.estados.IEstadoTicket;
import oo2.grupo19.SistemaTickets.services.impl.TicketServiceImpl;
import oo2.grupo19.SistemaTickets.services.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/ticket")
@Log4j2
public class TicketController {

    private final TicketServiceImpl ticketService;
    private final UsuarioServiceImpl usuarioService;
    private final IEmpleado empleadoRepository;
    private final ICliente clienteRepository;
    private final IEstadoTicket estadoTicketRepository;
    private final IEstadoIntervencion estadoIntervencion;

    // Crear un cliente - Crear un ticket - Login cliente

    // Agregue Qualifier para identificar a cada uno.
    public TicketController(TicketServiceImpl ticketService, @Qualifier("usuarioService") UsuarioServiceImpl usuarioService, IEstadoTicket estadoTicketRepository, IEstadoIntervencion estadoIntervencion, ICliente clienteRepository,IEmpleado empleadoRepository) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
        this.estadoTicketRepository = estadoTicketRepository;
        this.estadoIntervencion = estadoIntervencion;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // ... (otros campos y constructor permanecen igual)

    @GetMapping("/create")
    public String createTicket(Model model, Authentication authentication) {
        Ticket ticket = new Ticket();
        
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();
            
            // Buscar el cliente por email (ajusta según tu implementación)
            Cliente cliente = clienteRepository.findByContactoEmail(email).orElseThrow();
                
            if (cliente != null) {
                model.addAttribute("ticket", ticket);
                return "ticket/formTicket";
            }
        }
        
        model.addAttribute("title", "No tienes permiso para entrar aquí");
        return ViewRouteHelper.ERROR_404;
    }

    @PostMapping("/create")
    public String postCreate(
        @ModelAttribute Ticket ticket,
        Authentication authentication,
        Model model,
        @RequestParam("mensaje") String contenido) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("title", "Debes iniciar sesión");
            return ViewRouteHelper.ERROR_404;
        }

        // Obtener el usuario autenticado
        String email = authentication.getName();
        Usuario clienteDb = usuarioService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!(clienteDb instanceof Cliente)) {
            model.addAttribute("title", "Solo clientes pueden crear tickets");
            return ViewRouteHelper.ERROR_404;
        }

        // Detalle y realizadoPor No lo utilzamos por el momento!
        // Intervencion mensaje = new Intervencion();
        // mensaje.setDescripcion(contenido);
        // mensaje.setEstado(estadoIntervencion.findById(1L).get())
        
        
        ticket.setEstado(estadoTicketRepository.findById(1L).get());
        ticket.setCreadoPor(clienteDb);
        // Ser cuidadoso con esto. No esta probado, asi que puede fallar.
        ticket.setListEmpleado(empleadoRepository.findAll());
        ticketService.save(ticket);
        
        // return "redirect:/intervencion/form-processing-ticket?ticketId=" + ticket.getId();
        return ViewRouteHelper.TICKET_SUCCESS;
    }

    

    // Esto actualizaria el Ticket los datos de prioridad y estado. NO TERMINADO
    @GetMapping("ticket/update-ticket")
    public String updateTicket(@RequestParam Long ticketId){
        Ticket ticket = ticketService.findById(ticketId).orElseThrow();

        return "ticket/formTicketUpdate";
    }
}
