package ec.edu.espe.mc_clientes.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class PersonaRequestDto {
    @NotBlank(message = "La identificación no puede ser vacía")
    @Size(min=10, max=13, message = "La identificación debe tener entre 10 (CC) o 13 (RUC) caracteres")
    @Pattern(regexp = "\\d+", message = "La identificación debe contener solo números")
    private String identificacion;
    private String nombre;
    //Agregar validación en el que se incluyan tres extensiones juntas por ejemplo .gob.ec.com
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$", message = "El correo electrónico no es válido")
    private String email;
    @Pattern(regexp = "[0-9+\\-]+", message = "El teléfono tiene caracteres inválidos")
    private String telefono;
    private String direccion;   
}
