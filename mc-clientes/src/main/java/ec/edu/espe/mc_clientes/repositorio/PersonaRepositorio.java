package ec.edu.espe.mc_clientes.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.espe.mc_clientes.model.Persona;
import java.util.UUID;
import java.util.Optional;
import ec.edu.espe.mc_clientes.model.PersonaNatural;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;


@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, UUID>{
    Optional<Persona> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByEmail(String email);
    
    boolean existsByTelefono(String telefono);

    @Query("SELECT pn FROM PersonaNatural pn WHERE pn.activo = true") //Consulta jpql
    List<PersonaNatural> findPersonasNaturalesActivas();

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Persona> findByNombreContainingIgnoreCase(@Param("nombre") String nombre); //consulta nativa

}
