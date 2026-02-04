package ec.edu.espe.zone_core.zone_core.services;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDto;

import java.util.List;
import java.util.UUID;

public interface ZoneService {

    ZoneResponseDto createZone(ZoneRequestDto dto);
    ZoneResponseDto updateZone(UUID id, ZoneRequestDto dto);
    ZoneResponseDto getZone(UUID id);
    void deleteZone(UUID id);
    List<ZoneResponseDto> getAllZones();
}
