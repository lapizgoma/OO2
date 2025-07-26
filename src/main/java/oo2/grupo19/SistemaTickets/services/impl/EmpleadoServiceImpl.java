package oo2.grupo19.SistemaTickets.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.EmpleadoMapper;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.estados.enums.RoleType;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IContacto;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;


@Service
@Log4j2
public class EmpleadoServiceImpl implements IEmpleadoService {
    
    private final IEmpleado empleadoRepository;
    private final ITicket ticketRepository;
    private final PasswordEncoder passwordEncoder;
    private final IContacto contactoRepository;
    private final IRole roleRepository;

    public EmpleadoServiceImpl(IEmpleado empleadoRepository, ITicket ticketRepository, PasswordEncoder passwordEncoder, IContacto contactoRepository, IRole roleRepository) {
        this.empleadoRepository = empleadoRepository;
        this.ticketRepository = ticketRepository;
        this.passwordEncoder = passwordEncoder;
        this.contactoRepository = contactoRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void delete(String email) {
        Empleado empleado = empleadoRepository.findByContactoEmail(email).orElseThrow(() -> new NotFoundException("No se ha encontrado el empleado con ese id"));
        empleado.darDeBaja();
        empleadoRepository.save(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<EmpleadoDTO> findAll() {
        return EmpleadoMapper.mapToEmpleadoDtoSet(empleadoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoDTO findById(Long id) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new NotFoundException("No se ha encontrado el empleado con ese id"));
        return EmpleadoMapper.mapToEmpleadoDto(empleado);
    }

    @Override
    @Transactional
    public void save(EmpleadoDTO empleadodto) {
        if(empleadodto == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }
        Optional<Empleado> empleadoOpt = empleadoRepository.findByContactoEmail(empleadodto.getContacto().getEmail());
        Empleado empleado;
        if(empleadoOpt.isPresent()) {
            empleado = EmpleadoMapper.mapToEmpleadoEntity(empleadodto, empleadoOpt.get());
            empleado.setId(empleadoOpt.get().getId());
            Contacto contactoDB = contactoRepository.findById(empleadoOpt.get().getContacto().getId()).get();
            empleado.setContacto(contactoDB);
        } else {
            empleado = EmpleadoMapper.mapToEmpleadoEntity(empleadodto, new Empleado());
            String passwordHash = passwordEncoder.encode(empleado.getPassword());
            empleado.setPassword(passwordHash);
            long ultimoLegajo;
            if(empleadoRepository.findAll().isEmpty()) {
                ultimoLegajo = 10000;
            } else {
                ultimoLegajo = Long.parseLong(empleadoRepository.findAll().getLast().getNroLegajo()) + 1;
            }
            RoleType roleType = RoleType.valueOf(empleadodto.getRole());
            log.info("RoleType: " + roleType.toString());
            empleado.agregarRoles(roleRepository.findByType(roleType).orElseThrow());
            empleado.setNroLegajo(Long.toString(ultimoLegajo));
            empleado.setTickets(new HashSet<>(ticketRepository.findAll()));
        }
        empleadoRepository.save(empleado);
    }

    @Transactional(readOnly = true)
    public EmpleadoDTO findByEmail(String email){
        Empleado empleado = empleadoRepository.findByContactoEmail(email).orElseThrow(() -> new NotFoundException("No se ha encontrado el empleado con ese email"));
        return EmpleadoMapper.mapToEmpleadoDto(empleado);
    }

    @Transactional(readOnly = true)
	public Set<EmpleadoDTO> findAllActive(){
        try{
            return EmpleadoMapper.mapToEmpleadoDtoSet(empleadoRepository.findAllByDeletedFalse());
        }catch(Exception e){
            throw new NotFoundException("No se ha podido encontrar los empleados activos");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean existsByContactoEmail(String email) {
        return empleadoRepository.existsByContactoEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean existsByDni(String dni) {
        return empleadoRepository.existsByDni(dni);
    }

    
}
