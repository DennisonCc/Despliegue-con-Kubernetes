package ec.edu.espe.zone_core.zone_core.services;

import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import ec.edu.espe.zone_core.zone_core.model.Spaces;

import java.util.List;
import java.util.UUID;

public interface SpaceService {

    SpacesResponseDto createSpace(SpacesRequestDto dto);
    List<SpacesResponseDto> getSpaces();
    SpacesResponseDto updateSpace(UUID id, SpacesRequestDto dto);
    void deleteSpace(UUID id);
    SpacesResponseDto getSpace(UUID id);
    List<SpacesResponseDto> getAvailableSpaces();
    List<SpacesResponseDto> getAvailableSpacesByZone(UUID zoneId);
    SpacesResponseDto updateSpaceStatus(UUID id, String status);
   
}
