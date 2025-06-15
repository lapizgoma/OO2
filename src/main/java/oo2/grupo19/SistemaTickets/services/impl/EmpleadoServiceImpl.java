package oo2.grupo19.SistemaTickets.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import oo2.grupo19.SistemaTickets.services.IEmpleadoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.EmpleadoDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.EmpleadoMapper;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.NotFoundException;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;


@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
    
    private final IEmpleado empleadoRepository;
    private final ITicket ticketRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoServiceImpl(IEmpleado empleadoRepository, ITicket ticketRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.ticketRepository = ticketRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new NotFoundException("No se ha encontrado el empleado con ese id"));
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
        Optional<Empleado> empleadoOpt = empleadoRepository.findByContactoEmail(empleadodto.getEmail());
        Empleado empleado;
        if(empleadoOpt.isPresent()) {
            empleadodto.setId(empleadoOpt.get().getId());
            empleado = EmpleadoMapper.mapToEmpleadoEntity(empleadodto);
        } else {
            empleado = EmpleadoMapper.mapToEmpleadoEntity(empleadodto);
            String passwordHash = passwordEncoder.encode(empleado.getPassword());
            empleado.setPassword(passwordHash);
            long ultimoLegajo;
            if(empleadoRepository.findAll().isEmpty()) {
                ultimoLegajo = 10000;
            } else {
                ultimoLegajo = Long.parseLong(empleadoRepository.findAll().getLast().getNroLegajo()) + 1;
            }
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
}
