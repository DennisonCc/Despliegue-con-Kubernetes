package ec.edu.espe.mc_clientes.controller;

import ec.edu.espe.mc_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.mc_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.mc_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.mc_clientes.servicios.PersonaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    @Autowired
    private final PersonaServicio personaServicio;

    @GetMapping("/")
    public ResponseEntity<List<PersonaResponseDto>> listarTodasPersonas() {
        return ResponseEntity.ok(personaServicio.findAllPersona());
    }

    @GetMapping("/naturales")
    public ResponseEntity<List<PersonaResponseDto>> listarPersonasNaturales() {
        return ResponseEntity.ok(personaServicio.listarPersonasNaturales());
    }

    @PostMapping("/natural")
    public ResponseEntity<PersonaResponseDto> crearPersonaNatural(
            @Valid @RequestBody PersonaNaturalRequestDto dto) {
        PersonaResponseDto response = personaServicio.crearPersonaNatural(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/juridica")
    public ResponseEntity<PersonaResponseDto> crearPersonaJuridica(
            @Valid @RequestBody PersonaJuridicaRequestDto dto) {
        PersonaResponseDto response = personaServicio.crearPersonaJuridica(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/natural/{id}")
    public ResponseEntity<PersonaResponseDto> actualizarPersonaNatural(
            @PathVariable UUID id,
            @Valid @RequestBody PersonaNaturalRequestDto dto) {
        PersonaResponseDto response = personaServicio.actualizarPersonaNatural(id, dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/juridica/{id}")
    public ResponseEntity<PersonaResponseDto> actualizarPersonaJuridica(
            @PathVariable UUID id,
            @Valid @RequestBody PersonaJuridicaRequestDto dto) {
        PersonaResponseDto response = personaServicio.actualizarPersonaJuridica(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<PersonaResponseDto> obtenerPersonaPorIdentificacion(@PathVariable String identificacion) {
        PersonaResponseDto response = personaServicio.findByIdentificacion(identificacion);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable UUID id) {
        personaServicio.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}
