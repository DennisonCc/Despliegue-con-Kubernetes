package ec.edu.espe.zone_core.zone_core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDto;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    
    @Autowired
    private ZoneService zoneService;

    @GetMapping("/")
    public ResponseEntity<List<ZoneResponseDto>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @PostMapping("/")
    public ResponseEntity<ZoneResponseDto> createZone(@RequestBody ZoneRequestDto request) {
        return ResponseEntity.ok(zoneService.createZone(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> updateZone(
            @PathVariable UUID id,
            @RequestBody ZoneRequestDto request) {
        return ResponseEntity.ok(zoneService.updateZone(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> getZoneById(@PathVariable UUID id) {
        return ResponseEntity.ok(zoneService.getZone(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable UUID id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
