package ec.edu.espe.mc_clientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonaJuridicaRequestDto extends PersonaRequestDto {
    
    @NotBlank(message = "La razón social no puede ser vacía")
    @Size(min = 3, max = 100, message = "La razón social debe tener entre 3 y 100 caracteres")
    private String razonSocial;

    @NotBlank(message = "El representante legal no puede ser vacío")
    @Size(min = 3, max = 100, message = "El representante legal debe tener entre 3 y 100 caracteres")
    private String representanteLegal;

    @NotBlank(message = "La actividad económica no puede ser vacía")
    @Size(min = 3, max = 200, message = "La actividad económica debe tener entre 3 y 200 caracteres")
    private String actividadEconomica;
}
