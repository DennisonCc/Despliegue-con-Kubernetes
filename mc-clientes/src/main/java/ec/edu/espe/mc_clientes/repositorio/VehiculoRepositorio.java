package ec.edu.espe.mc_clientes.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.espe.mc_clientes.model.Vehiculo;

@Repository
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, UUID> {

    Optional<Vehiculo> findByPlaca(String placa);
    
    List<Vehiculo> findByPropietarioId(UUID propietarioId);
    
}
