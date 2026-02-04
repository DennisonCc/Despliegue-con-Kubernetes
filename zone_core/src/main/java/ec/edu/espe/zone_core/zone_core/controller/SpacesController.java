package ec.edu.espe.zone_core.zone_core.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.espe.zone_core.zone_core.model.Spaces;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;
import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.UUID;

import java.util.List;


@RestController
@RequestMapping("/api/spaces")
public class SpacesController {
    @Autowired
    private SpaceService spaceService;

    @GetMapping("/")
    public ResponseEntity<List<SpacesResponseDto>> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getSpaces());
    }

    @PostMapping("/")
    public ResponseEntity<SpacesResponseDto> createSpace(@RequestBody SpacesRequestDto request){
        return ResponseEntity.ok(spaceService.createSpace(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpacesResponseDto> updateSpace(
            @PathVariable UUID id,
            @RequestBody SpacesRequestDto request){
        return ResponseEntity.ok(spaceService.updateSpace(id, request));
    }

    @GetMapping("/availables")
    public ResponseEntity<List<SpacesResponseDto>> getAvailableSpaces(){
        return ResponseEntity.ok(spaceService.getAvailableSpaces());
    }

    @GetMapping("/availablesbyzone/{zoneId}")
    public ResponseEntity<List<SpacesResponseDto>> getAvailableSpacesByZone(@PathVariable UUID zoneId){
        return ResponseEntity.ok(spaceService.getAvailableSpacesByZone(zoneId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpacesResponseDto> getSpaceById(@PathVariable UUID id){
        return ResponseEntity.ok(spaceService.getSpace(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SpacesResponseDto> updateSpaceStatus(
            @PathVariable UUID id,
            @RequestParam String status){
        return ResponseEntity.ok(spaceService.updateSpaceStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID id){
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }

}