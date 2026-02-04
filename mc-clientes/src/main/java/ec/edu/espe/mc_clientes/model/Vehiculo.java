package ec.edu.espe.mc_clientes.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculo")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer anioFabricacion;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false) // en vez de column es join column
    private Persona propietario;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.activo = true;
    }

    
}
