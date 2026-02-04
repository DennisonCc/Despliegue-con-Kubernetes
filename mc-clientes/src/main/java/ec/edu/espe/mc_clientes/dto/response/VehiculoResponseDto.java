package ec.edu.espe.mc_clientes.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import ec.edu.espe.mc_clientes.model.TipoAutomovil;
import ec.edu.espe.mc_clientes.model.TipoCombustible;
import ec.edu.espe.mc_clientes.model.TipoMoto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehiculoResponseDto {

    private UUID id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private Integer anioFabricacion;
    private UUID propietarioId;
    private String tipo; // AUTOMOVIL o MOTO
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    // Detalles de automóvil
    private TipoAutomovil tipoAutomovil;
    private TipoCombustible tipoCombustible;
    private Integer numeroPuertas;
    private Integer numeroPasajeros;
    private Integer cilindrada;
    private String transmision;
    private Boolean aireAcondicionado;
    private Boolean abs;
    private Boolean airbags;

    // Detalles de moto
    private Integer cilindrajeMoto;
    private TipoMoto tipoMoto;
    private Boolean tieneCasco;
}
