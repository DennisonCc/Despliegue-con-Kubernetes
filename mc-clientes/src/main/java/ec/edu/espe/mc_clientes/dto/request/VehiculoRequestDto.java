package ec.edu.espe.mc_clientes.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehiculoRequestDto {

	@NotBlank(message = "La placa no puede estar vacía")
	@Pattern(regexp = "^[A-Z]{3}-?[0-9]{3,4}$", message = "La placa debe tener el formato ABC-123 o ABC-1234")
	private String placa;

	@NotBlank(message = "La marca es obligatoria")
	@Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
	private String marca;

	@NotBlank(message = "El modelo es obligatorio")
	@Size(min = 1, max = 50, message = "El modelo debe tener entre 1 y 50 caracteres")
	private String modelo;

	@NotBlank(message = "El color es obligatorio")
	@Size(min = 3, max = 30, message = "El color debe tener entre 3 y 30 caracteres")
	private String color;

	@NotNull(message = "El año de fabricación es obligatorio")
	@Min(value = 1900, message = "El año de fabricación debe ser mayor o igual a 1900")
	private Integer anioFabricacion;

	@NotNull(message = "El identificador del propietario es obligatorio")
	private UUID propietarioId;
}
