package ec.edu.espe.mc_clientes.servicios.impl;

import ec.edu.espe.mc_clientes.dto.request.AutomovilRequest;
import ec.edu.espe.mc_clientes.dto.request.MotoRequest;
import ec.edu.espe.mc_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.mc_clientes.dto.mapper.VehiculoMapper;
import ec.edu.espe.mc_clientes.model.Automovil;
import ec.edu.espe.mc_clientes.model.Moto;
import ec.edu.espe.mc_clientes.model.Persona;
import ec.edu.espe.mc_clientes.model.Vehiculo;
import ec.edu.espe.mc_clientes.repositorio.PersonaRepositorio;
import ec.edu.espe.mc_clientes.repositorio.VehiculoRepositorio;
import ec.edu.espe.mc_clientes.servicios.VehiculoServicio;
import ec.edu.espe.mc_clientes.messaging.NotificationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class VehiculoServicioImpl implements VehiculoServicio {

    private final VehiculoRepositorio vehiculoRepositorio;
    private final PersonaRepositorio personaRepositorio;
    private final VehiculoMapper vehiculoMapper;
    private final NotificationProducer notificationProducer;

    @Override
    public VehiculoResponseDto crearMoto(MotoRequest dto) {
        Persona propietario = personaRepositorio.findById(dto.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + dto.getPropietarioId()));

        Moto moto = vehiculoMapper.toEntity(dto, propietario);
        moto.setFechaCreacion(LocalDateTime.now());
        moto.setActivo(true);

        Vehiculo saved = vehiculoRepositorio.save(moto);
        log.info("Moto creada con ID: " + saved.getId());
        
        // Enviar notificación
        notificationProducer.sendVehiculoCreated(saved.getId(), saved.getPlaca(), propietario.getId());
        
        return vehiculoMapper.toDto(saved);
    }

    @Override
    public VehiculoResponseDto crearAutomovil(AutomovilRequest dto) {
        Persona propietario = personaRepositorio.findById(dto.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + dto.getPropietarioId()));

        Automovil automovil = vehiculoMapper.toEntity(dto, propietario);
        automovil.setFechaCreacion(LocalDateTime.now());
        automovil.setActivo(true);

        Vehiculo saved = vehiculoRepositorio.save(automovil);
        log.info("Automovil creado con ID: " + saved.getId());
        
        // Enviar notificación
        notificationProducer.sendVehiculoCreated(saved.getId(), saved.getPlaca(), propietario.getId());
        
        return vehiculoMapper.toDto(saved);
    }

    @Override
    public VehiculoResponseDto actualizarMoto(UUID id, MotoRequest dto) {
        Moto moto = (Moto) vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada con ID: " + id));

        Persona propietario = personaRepositorio.findById(dto.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + dto.getPropietarioId()));

        moto.setPlaca(dto.getPlaca());
        moto.setMarca(dto.getMarca());
        moto.setModelo(dto.getModelo());
        moto.setColor(dto.getColor());
        moto.setAnioFabricacion(dto.getAnioFabricacion());
        moto.setPropietario(propietario);
        moto.setCilindraje(dto.getCilindraje());
        moto.setTipo(dto.getTipo());
        moto.setTieneCasco(dto.getTieneCasco());

        Vehiculo updated = vehiculoRepositorio.save(moto);
        log.info("Moto actualizada con ID: " + id);
        
        // Enviar notificación
        notificationProducer.sendVehiculoUpdated(id, updated.getPlaca(), propietario.getId());
        
        return vehiculoMapper.toDto(updated);
    }

    @Override
    public VehiculoResponseDto actualizarAutomovil(UUID id, AutomovilRequest dto) {
        Automovil automovil = (Automovil) vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Automovil no encontrado con ID: " + id));

        Persona propietario = personaRepositorio.findById(dto.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + dto.getPropietarioId()));

        automovil.setPlaca(dto.getPlaca());
        automovil.setMarca(dto.getMarca());
        automovil.setModelo(dto.getModelo());
        automovil.setColor(dto.getColor());
        automovil.setAnioFabricacion(dto.getAnioFabricacion());
        automovil.setPropietario(propietario);
        automovil.setTipo(dto.getTipo());
        automovil.setCombustible(dto.getCombustible());
        automovil.setNumeroPuertas(dto.getNumeroPuertas());
        automovil.setNumeroPasajeros(dto.getNumeroPasajeros());
        automovil.setCilindrada(dto.getCilindrada());
        automovil.setTransmision(dto.getTransmision());
        automovil.setAireAcondicionado(dto.getAireAcondicionado());
        automovil.setAbs(dto.getAbs());
        automovil.setAirbags(dto.getAirbags());

        Vehiculo updated = vehiculoRepositorio.save(automovil);
        log.info("Automovil actualizado con ID: " + id);
        
        // Enviar notificación
        notificationProducer.sendVehiculoUpdated(id, updated.getPlaca(), propietario.getId());
        
        return vehiculoMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> findAllVehiculo() {
        return vehiculoRepositorio.findAll().stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarVehiculo(UUID id) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con ID: " + id));
        
        String placa = vehiculo.getPlaca();
        UUID propietarioId = vehiculo.getPropietario().getId();
        
        vehiculo.setActivo(false);
        vehiculoRepositorio.save(vehiculo);
        log.info("Vehiculo eliminado (desactivado) con ID: " + id);
        
        // Enviar notificación
        notificationProducer.sendVehiculoDeleted(id, placa, propietarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponseDto findByPlaca(String placa) {
        log.info("Buscando vehículo por placa: {}", placa);
        Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con placa: " + placa));
        
        return vehiculoMapper.toDto(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> findByPropietarioId(UUID propietarioId) {
        log.info("Buscando vehículos por propietario ID: {}", propietarioId);
        
        // Verificar que el propietario existe
        personaRepositorio.findById(propietarioId)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + propietarioId));
        
        return vehiculoRepositorio.findByPropietarioId(propietarioId).stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }
}
