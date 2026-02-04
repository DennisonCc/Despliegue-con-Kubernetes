package ec.edu.espe.mc_clientes.controller;

import ec.edu.espe.mc_clientes.dto.request.AutomovilRequest;
import ec.edu.espe.mc_clientes.dto.request.MotoRequest;
import ec.edu.espe.mc_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.mc_clientes.servicios.VehiculoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoServicio vehiculoServicio;

    @GetMapping("/")
    public ResponseEntity<List<VehiculoResponseDto>> listarTodosVehiculos() {
        return ResponseEntity.ok(vehiculoServicio.findAllVehiculo());
    }

    @PostMapping("/moto")
    public ResponseEntity<VehiculoResponseDto> crearMoto(
            @Valid @RequestBody MotoRequest dto) {
        VehiculoResponseDto response = vehiculoServicio.crearMoto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/automovil")
    public ResponseEntity<VehiculoResponseDto> crearAutomovil(
            @Valid @RequestBody AutomovilRequest dto) {
        VehiculoResponseDto response = vehiculoServicio.crearAutomovil(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/moto/{id}")
    public ResponseEntity<VehiculoResponseDto> actualizarMoto(
            @PathVariable UUID id,
            @Valid @RequestBody MotoRequest dto) {
        VehiculoResponseDto response = vehiculoServicio.actualizarMoto(id, dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/automovil/{id}")
    public ResponseEntity<VehiculoResponseDto> actualizarAutomovil(
            @PathVariable UUID id,
            @Valid @RequestBody AutomovilRequest dto) {
        VehiculoResponseDto response = vehiculoServicio.actualizarAutomovil(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VehiculoResponseDto> obtenerVehiculoPorPlaca(@PathVariable String placa) {
        VehiculoResponseDto response = vehiculoServicio.findByPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/propietario/{personaId}")
    public ResponseEntity<List<VehiculoResponseDto>> obtenerVehiculosPorPropietario(@PathVariable UUID personaId) {
        List<VehiculoResponseDto> response = vehiculoServicio.findByPropietarioId(personaId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable UUID id) {
        vehiculoServicio.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}
