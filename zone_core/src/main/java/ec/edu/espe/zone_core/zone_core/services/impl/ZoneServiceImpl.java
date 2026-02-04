package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDto;
import ec.edu.espe.zone_core.zone_core.model.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;
import ec.edu.espe.zone_core.zone_core.messaging.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    
    @Autowired
    private NotificationProducer notificationProducer;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public ZoneResponseDto createZone(ZoneRequestDto dto) {
        Zone zone = Zone.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .capacity(dto.getCapacity())
                .type(dto.getType())
                .isActive(dto.getIsActive())
                .build();

        Zone saved = zoneRepository.save(zone);
        notificationProducer.sendZoneCreated(saved.getId(), saved.getName());
        return mapToResponseDto(saved);
    }

    @Override
    public ZoneResponseDto updateZone(UUID id, ZoneRequestDto dto) {
        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setCapacity(dto.getCapacity());
        existing.setType(dto.getType());
        existing.setIsActive(dto.getIsActive());

        Zone updated = zoneRepository.save(existing);
        notificationProducer.sendZoneUpdated(updated.getId(), updated.getName());
        return mapToResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ZoneResponseDto getZone(UUID id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        return mapToResponseDto(zone);
    }

    @Override
    public void deleteZone(UUID id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        String zoneName = zone.getName();
        zoneRepository.delete(zone);
        notificationProducer.sendZoneDeleted(id, zoneName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZoneResponseDto> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private ZoneResponseDto mapToResponseDto(Zone zone) {
        return ZoneResponseDto.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .capacity(zone.getCapacity())
                .type(zone.getType())
                .isActive(zone.getIsActive())
                .build();
    }
}
