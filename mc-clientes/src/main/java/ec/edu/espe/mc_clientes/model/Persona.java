package ec.edu.espe.mc_clientes.model;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.InheritanceType;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED) //herencia a nivel de tablas separadas
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String telefono;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion; //fecha registro 

    @Column(nullable = false)
    private Boolean activo;

    @Column
    private String direccion;

    public abstract boolean validarIdentificacion();

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }




    
    


}
