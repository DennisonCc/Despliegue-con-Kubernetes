package ec.edu.espe.mc_clientes.servicios;

import java.util.UUID;

import ec.edu.espe.mc_clientes.dto.request.AutomovilRequest;
import ec.edu.espe.mc_clientes.dto.request.MotoRequest;
import ec.edu.espe.mc_clientes.dto.response.VehiculoResponseDto;

import java.util.List;

public interface VehiculoServicio {

    VehiculoResponseDto crearMoto(MotoRequest dto);
    VehiculoResponseDto crearAutomovil(AutomovilRequest dto);

    VehiculoResponseDto actualizarMoto(UUID id, MotoRequest dto);
    VehiculoResponseDto actualizarAutomovil(UUID id, AutomovilRequest dto);

    List<VehiculoResponseDto> findAllVehiculo();

    void eliminarVehiculo(UUID id);

    VehiculoResponseDto findByPlaca(String placa);
    
    List<VehiculoResponseDto> findByPropietarioId(UUID propietarioId);
    
}
