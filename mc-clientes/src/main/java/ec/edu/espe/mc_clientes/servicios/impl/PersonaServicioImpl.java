package ec.edu.espe.mc_clientes.servicios.impl;
import ec.edu.espe.mc_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.mc_clientes.repositorio.PersonaRepositorio;
import ec.edu.espe.mc_clientes.servicios.PersonaServicio;
import ec.edu.espe.mc_clientes.messaging.NotificationProducer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ec.edu.espe.mc_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.mc_clientes.dto.mapper.PersonaMapper;
import ec.edu.espe.mc_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.mc_clientes.model.PersonaNatural;
import ec.edu.espe.mc_clientes.model.PersonaJuridica;
import ec.edu.espe.mc_clientes.model.Persona;
import java.util.UUID;
import java.util.stream.Collectors;

//completar el crear persona juridica

@Transactional
@Service
@Slf4j // log.info - log.error - log.debug
@RequiredArgsConstructor
public class PersonaServicioImpl implements PersonaServicio{

    private final PersonaRepositorio personaRepositorio;
    private final PersonaMapper personaMapper;
    private final NotificationProducer notificationProducer;

	@Override
    public PersonaResponseDto crearPersonaNatural(PersonaNaturalRequestDto dto){
        if(personaRepositorio.existsByIdentificacion(dto.getIdentificacion())){
            log.error("Ya existe una persona con estra identificacion: "+ dto.getIdentificacion());
            throw new RuntimeException("La persona con esta identificación ya existe");
        }

        if(personaRepositorio.existsByEmail(dto.getEmail())){
            throw new RuntimeException("La persona con este email ya existe");
        }

        if(personaRepositorio.existsByTelefono(dto.getTelefono())){
            throw new RuntimeException("La persona con este número de teléfono ya existe");
        }

        PersonaNatural pn = personaMapper.toEntity(dto);

        if(!pn.validarIdentificacion()){
            throw new RuntimeException("La cédula no es válida");
        }
        Persona p = personaRepositorio.save(pn);
        log.info("Persona Natural creada y guardada con ID: "+ p.getId());
        
        // Enviar notificación
        notificationProducer.sendPersonaCreated(p.getId(), p.getIdentificacion(), "NATURAL");
        
        return personaMapper.toDto(p);
    }

    @Override
    public PersonaResponseDto crearPersonaJuridica(PersonaJuridicaRequestDto dto){
        if(personaRepositorio.existsByIdentificacion(dto.getIdentificacion())){
            log.error("Ya existe una persona con esta identificación: "+ dto.getIdentificacion());
            throw new RuntimeException("La persona con esta identificación ya existe");
        }

        if(personaRepositorio.existsByEmail(dto.getEmail())){
            throw new RuntimeException("La persona con este email ya existe");
        }

        if(personaRepositorio.existsByTelefono(dto.getTelefono())){
            throw new RuntimeException("La persona con este número de teléfono ya existe");
        }

        PersonaJuridica pj = personaMapper.toEntity(dto);

        if(!pj.validarIdentificacion()){
            throw new RuntimeException("El RUC no es válido");
        }
        
        Persona p = personaRepositorio.save(pj);
        log.info("Persona Jurídica creada y guardada con ID: "+ p.getId());
        
        // Enviar notificación
        notificationProducer.sendPersonaCreated(p.getId(), p.getIdentificacion(), "JURIDICA");
        
        return personaMapper.toDto(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> findAllPersona(){       
        return personaRepositorio.findAll()
                        .stream()
                        .map(personaMapper::toDto)
                        .collect(Collectors.toList());     //métodos streams
    }

    @Override
    public void eliminarPersona(UUID id){
        Persona persona = personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
        
        String identificacion = persona.getIdentificacion();
        String tipo = persona instanceof PersonaNatural ? "NATURAL" : "JURIDICA";
        
        personaRepositorio.delete(persona);
        log.info("Persona eliminada con ID: {}", id);
        
        // Enviar notificación
        notificationProducer.sendPersonaDeleted(id, identificacion, tipo);
    }

    @Override
    public PersonaResponseDto actualizarPersonaNatural(UUID id, PersonaNaturalRequestDto dto){
        PersonaNatural existing = (PersonaNatural) personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
        
        // Actualizar campos
        existing.setNombre(dto.getNombre());
        existing.setApellido(dto.getApellido());
        existing.setEmail(dto.getEmail());
        existing.setTelefono(dto.getTelefono());
        existing.setDireccion(dto.getDireccion());
        
        Persona updated = personaRepositorio.save(existing);
        log.info("Persona Natural actualizada con ID: {}", id);
        
        // Enviar notificación
        notificationProducer.sendPersonaUpdated(id, updated.getIdentificacion(), "NATURAL");
        
        return personaMapper.toDto(updated);
    }

    @Override
    public PersonaResponseDto actualizarPersonaJuridica(UUID id, PersonaJuridicaRequestDto dto){
        PersonaJuridica existing = (PersonaJuridica) personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
        
        // Actualizar campos
        existing.setRazonSocial(dto.getRazonSocial());
        existing.setRepresentanteLegal(dto.getRepresentanteLegal());
        existing.setActividadEconomica(dto.getActividadEconomica());
        existing.setEmail(dto.getEmail());
        existing.setTelefono(dto.getTelefono());
        existing.setDireccion(dto.getDireccion());
        
        Persona updated = personaRepositorio.save(existing);
        log.info("Persona Jurídica actualizada con ID: {}", id);
        
        // Enviar notificación
        notificationProducer.sendPersonaUpdated(id, updated.getIdentificacion(), "JURIDICA");
        
        return personaMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> listarPersonasNaturales() {
        log.info("Listando personas naturales");
        return personaRepositorio.findPersonasNaturalesActivas().stream()
                .map(p -> personaMapper.toDto(p))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDto findByIdentificacion(String identificacion) {
        log.info("Buscando persona por identificación: {}", identificacion);
        Persona persona = personaRepositorio.findByIdentificacion(identificacion)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con identificación: " + identificacion));
        
        return personaMapper.toDto(persona);
    }
	
}
