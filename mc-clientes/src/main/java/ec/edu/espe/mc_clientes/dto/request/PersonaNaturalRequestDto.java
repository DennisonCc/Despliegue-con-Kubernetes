package ec.edu.espe.mc_clientes.dto.request;
import lombok.EqualsAndHashCode;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonaNaturalRequestDto extends PersonaRequestDto {
    @NotBlank(message = "El nombre no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
    private String apellido;
    
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @Pattern(regexp = "[MFO]", message = "El género debe ser 'M' (Masculino), 'F' (Femenino) u 'O' (Otro)")
    private String genero; // M, F, O

}
