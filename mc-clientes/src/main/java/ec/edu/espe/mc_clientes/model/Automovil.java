package ec.edu.espe.mc_clientes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "automovil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Automovil extends Vehiculo {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAutomovil tipo; // SUV, CROSSOVER, SEDAN, HATCHBACK, COUPE, CONVERTIBLE

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCombustible combustible; // GASOLINA, DIESEL, ELECTRICO, HIBRIDO

    @Column(nullable = false)
    private Integer numeroPuertas; // 2, 3, 4, 5 puertas

    @Column(nullable = false)
    private Integer numeroPasajeros; // capacidad de pasajeros

    @Column(nullable = false)
    private Integer cilindrada; // cilindrada del motor en cc

    @Column(nullable = false)
    private String transmision; // MANUAL, AUTOMATICA, CVT, etc.

    @Column
    private Boolean aireAcondicionado; // si tiene aire acondicionado

    @Column
    private Boolean abs; // si tiene frenos ABS

    @Column
    private Boolean airbags; // si tiene airbags

}

