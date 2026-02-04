package ec.edu.espe.mc_clientes.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import ec.edu.espe.mc_clientes.model.TipoGenero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonaResponseDto {

    private UUID id;
    private String identificacion;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String tipo; //NATURAL o JURIDICA
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    // Campos específicos de PersonaNatural
    private String apellido;
    private TipoGenero genero;
    private LocalDate fechaNacimiento;

    // Campos específicos de PersonaJuridica
    private String razonSocial;
    private String representanteLegal;
    private String actividadEconomica;
}
