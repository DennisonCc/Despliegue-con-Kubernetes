package ec.edu.espe.zone_core.zone_core.repositories;

import ec.edu.espe.zone_core.zone_core.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, UUID> {
}
