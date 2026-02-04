package ec.edu.espe.mc_clientes.model;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity
@Table(name = "moto")
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto extends Vehiculo {

    @Column(nullable = false)
    private Integer cilindraje;

    @Column(nullable = false)
    private TipoMoto tipo;

    @Column(nullable = false)
    private Boolean tieneCasco;


    
}
