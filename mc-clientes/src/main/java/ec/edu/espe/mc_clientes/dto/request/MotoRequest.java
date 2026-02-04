package ec.edu.espe.mc_clientes.dto.request;

import ec.edu.espe.mc_clientes.model.TipoMoto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotoRequest extends VehiculoRequestDto {

	@NotNull(message = "El cilindraje es obligatorio")
	@Min(value = 50, message = "El cilindraje debe ser de al menos 50 cc")
	private Integer cilindraje;

	@NotNull(message = "El tipo de moto es obligatorio")
	private TipoMoto tipo;

	@NotNull(message = "Debe especificar si se entrega casco")
	private Boolean tieneCasco;
}
