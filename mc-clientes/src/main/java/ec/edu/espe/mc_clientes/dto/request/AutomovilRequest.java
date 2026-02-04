package ec.edu.espe.mc_clientes.dto.request;

import ec.edu.espe.mc_clientes.model.TipoAutomovil;
import ec.edu.espe.mc_clientes.model.TipoCombustible;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AutomovilRequest extends VehiculoRequestDto {

	@NotNull(message = "El tipo de automóvil es obligatorio")
	private TipoAutomovil tipo;

	@NotNull(message = "El tipo de combustible es obligatorio")
	private TipoCombustible combustible;

	@NotNull(message = "El número de puertas es obligatorio")
	@Min(value = 2, message = "Un automóvil debe tener al menos 2 puertas")
	private Integer numeroPuertas;

	@NotNull(message = "El número de pasajeros es obligatorio")
	@Min(value = 1, message = "Debe transportar al menos a un pasajero")
	private Integer numeroPasajeros;

	@NotNull(message = "La cilindrada es obligatoria")
	@Min(value = 600, message = "La cilindrada debe ser de al menos 600 cc")
	private Integer cilindrada;

	@NotBlank(message = "La transmisión es obligatoria")
	@Size(max = 20, message = "La transmisión no debe exceder 20 caracteres")
	private String transmision;

	private Boolean aireAcondicionado;

	private Boolean abs;

	private Boolean airbags;
}
