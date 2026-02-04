package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import ec.edu.espe.zone_core.zone_core.model.Spaces;
import ec.edu.espe.zone_core.zone_core.model.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.SpaceRepository;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ec.edu.espe.zone_core.zone_core.messaging.NotificationProducer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.Notification;


@Service
@Transactional
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private final SpaceRepository spaceRepository;
    @Autowired
    private final ZoneRepository zoneRepository;
    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository, ZoneRepository zoneRepository) {
        this.spaceRepository = spaceRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public SpacesResponseDto createSpace(SpacesRequestDto dto) {

        if(spaceRepository.existsByCode(dto.getCode())) {
            throw new RuntimeException("El código del espacio ya existe");
        }
        
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada "));

        Spaces space = Spaces.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .isReserved(dto.getIsReserved())
                .zone(zone)
                .build();

        Spaces saved = spaceRepository.save(space);
        notificationProducer.sendSpacesCreated(saved.getId(), saved.getCode(), saved.getZone().getId());
        return convertToDto(saved);
    }

    @Override
    public SpacesResponseDto updateSpace(UUID id, SpacesRequestDto dto) {
        Spaces existing = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space no encontrado"));
        
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone no encontrado"));

        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        existing.setStatus(dto.getStatus());
        existing.setPriority(dto.getPriority());
        existing.setIsReserved(dto.getIsReserved());
        existing.setZone(zone);

        Spaces updated = spaceRepository.save(existing);
        notificationProducer.sendSpaceUpdated(updated.getId(), updated.getCode(), updated.getZone().getId());
        return convertToDto(updated);
    }
    

    @Override
    @Transactional(readOnly = true)
    public SpacesResponseDto getSpace(UUID id) {
        Spaces space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space no encontrado"));
        return convertToDto(space);
    }

    @Override
    public void deleteSpace(UUID spaceId) {
        Spaces space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Space not found with id: " + spaceId));
        String spaceCode = space.getCode();
        UUID zoneId = space.getZone().getId();
        spaceRepository.delete(space);
        notificationProducer.sendSpaceDeleted(spaceId, spaceCode, zoneId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpacesResponseDto> getSpaces() {
        return spaceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpacesResponseDto> getAvailableSpaces() {
        return spaceRepository.findByStatus(ec.edu.espe.zone_core.zone_core.model.SapaceStatus.AVALIABLE).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpacesResponseDto> getAvailableSpacesByZone(UUID zoneId) {
        // Verificar que la zona existe
        zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + zoneId));
        
        return spaceRepository.findByZoneIdAndStatus(zoneId, ec.edu.espe.zone_core.zone_core.model.SapaceStatus.AVALIABLE).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpacesResponseDto updateSpaceStatus(UUID id, String status) {
        Spaces existing = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space no encontrado con ID: " + id));
        
        // Convertir string a enum
        ec.edu.espe.zone_core.zone_core.model.SapaceStatus newStatus;
        try {
            // Mapear los estados que vienen de ms-tickets
            String normalizedStatus = status.toUpperCase();
            if (normalizedStatus.equals("DISPONIBLE") || normalizedStatus.equals("AVAILABLE")) {
                newStatus = ec.edu.espe.zone_core.zone_core.model.SapaceStatus.AVALIABLE;
            } else if (normalizedStatus.equals("OCUPADO") || normalizedStatus.equals("OCCUPIED")) {
                newStatus = ec.edu.espe.zone_core.zone_core.model.SapaceStatus.OCCUPIED;
            } else if (normalizedStatus.equals("MANTENIMIENTO") || normalizedStatus.equals("MAINTENANCE")) {
                newStatus = ec.edu.espe.zone_core.zone_core.model.SapaceStatus.MAINTENANCE;
            } else {
                // Intentar conversión directa
                newStatus = ec.edu.espe.zone_core.zone_core.model.SapaceStatus.valueOf(normalizedStatus);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido: " + status + ". Estados válidos: AVAILABLE, OCCUPIED, MAINTENANCE");
        }
        
        existing.setStatus(newStatus);
        Spaces updated = spaceRepository.save(existing);
        
        notificationProducer.sendSpaceUpdated(updated.getId(), updated.getCode(), updated.getZone().getId());
        
        return convertToDto(updated);
    }

    private SpacesResponseDto convertToDto(Spaces objSpaces) {
        return SpacesResponseDto.builder()
                .id(objSpaces.getId())
                .code(objSpaces.getCode())
                .status(objSpaces.getStatus())
                .priority(objSpaces.getPriority())
                .isReserved(objSpaces.getIsReserved())
                .zoneId(objSpaces.getZone().getId())
                .zoneName(objSpaces.getZone().getName())
                .build();
    }
}
