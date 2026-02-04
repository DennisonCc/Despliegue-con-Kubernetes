package ec.edu.espe.mc_clientes.servicios;
import ec.edu.espe.mc_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.mc_clientes.dto.response.PersonaResponseDto;
   
import ec.edu.espe.mc_clientes.dto.request.PersonaJuridicaRequestDto;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service

public interface PersonaServicio {
    PersonaResponseDto crearPersonaNatural(PersonaNaturalRequestDto dto);
    PersonaResponseDto crearPersonaJuridica(PersonaJuridicaRequestDto dto);
    List<PersonaResponseDto> findAllPersona();

    void eliminarPersona(UUID id);

    PersonaResponseDto actualizarPersonaNatural(UUID id, PersonaNaturalRequestDto dto);
    PersonaResponseDto actualizarPersonaJuridica(UUID id, PersonaJuridicaRequestDto dto);

    List<PersonaResponseDto> listarPersonasNaturales();

    PersonaResponseDto findByIdentificacion(String identificacion);
     
}
