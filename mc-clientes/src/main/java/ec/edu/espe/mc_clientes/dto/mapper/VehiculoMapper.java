package ec.edu.espe.mc_clientes.dto.mapper;

import org.springframework.stereotype.Component;

import ec.edu.espe.mc_clientes.dto.request.AutomovilRequest;
import ec.edu.espe.mc_clientes.dto.request.MotoRequest;
import ec.edu.espe.mc_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.mc_clientes.model.Automovil;
import ec.edu.espe.mc_clientes.model.Moto;
import ec.edu.espe.mc_clientes.model.Persona;
import ec.edu.espe.mc_clientes.model.Vehiculo;

@Component
public class VehiculoMapper {

    public Automovil toEntity(AutomovilRequest dto, Persona propietario) {
        if (dto == null || propietario == null) {
            return null;
        }

        return Automovil.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .tipo(dto.getTipo())
                .combustible(dto.getCombustible())
                .numeroPuertas(dto.getNumeroPuertas())
                .numeroPasajeros(dto.getNumeroPasajeros())
                .cilindrada(dto.getCilindrada())
                .transmision(dto.getTransmision())
                .aireAcondicionado(dto.getAireAcondicionado())
                .abs(dto.getAbs())
                .airbags(dto.getAirbags())
                .build();
    }

    public Moto toEntity(MotoRequest dto, Persona propietario) {
        
        if (dto == null || propietario == null) {
            return null;
        }

        return Moto.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .cilindraje(dto.getCilindraje())
                .tipo(dto.getTipo())
                .tieneCasco(dto.getTieneCasco())
                .build();
    }

    public VehiculoResponseDto toDto(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        VehiculoResponseDto.VehiculoResponseDtoBuilder builder = VehiculoResponseDto.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .color(vehiculo.getColor())
                .anioFabricacion(vehiculo.getAnioFabricacion())
                .propietarioId(vehiculo.getPropietario() != null ? vehiculo.getPropietario().getId() : null)
                .activo(vehiculo.getActivo())
                .fechaCreacion(vehiculo.getFechaCreacion());

        if (vehiculo instanceof Automovil automovil) {
            builder.tipo("AUTOMOVIL")
                    .tipoAutomovil(automovil.getTipo())
                    .tipoCombustible(automovil.getCombustible())
                    .numeroPuertas(automovil.getNumeroPuertas())
                    .numeroPasajeros(automovil.getNumeroPasajeros())
                    .cilindrada(automovil.getCilindrada())
                    .transmision(automovil.getTransmision())
                    .aireAcondicionado(automovil.getAireAcondicionado())
                    .abs(automovil.getAbs())
                    .airbags(automovil.getAirbags());
        } else if (vehiculo instanceof Moto moto) {
            builder.tipo("MOTO")
                    .cilindrajeMoto(moto.getCilindraje())
                    .tipoMoto(moto.getTipo())
                    .tieneCasco(moto.getTieneCasco());
        } else {
            builder.tipo("DESCONOCIDO");
        }

        return builder.build();
    }
}
