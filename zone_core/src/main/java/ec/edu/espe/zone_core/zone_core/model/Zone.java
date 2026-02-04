package ec.edu.espe.zone_core.zone_core.model;

import jakarta.persistence.*;

import java.util.UUID;
import ec.edu.espe.zone_core.zone_core.model.TypeZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column (length=100, nullable=false, unique=true)
    private String name;

    @Column (length=255)
    private String description;

    @Column (nullable=false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column (nullable=false)
    private TypeZone type;

    @Column (nullable=false, name = "status")
    private Boolean isActive;

}

