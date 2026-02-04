package ec.edu.espe.mc_clientes.dto.mapper;

import org.springframework.stereotype.Component;

import ec.edu.espe.mc_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.mc_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.mc_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.mc_clientes.model.PersonaJuridica;
import ec.edu.espe.mc_clientes.model.PersonaNatural;
import ec.edu.espe.mc_clientes.model.Persona;
import ec.edu.espe.mc_clientes.model.TipoGenero;



@Component
public class PersonaMapper {

    
    public PersonaNatural toEntity(PersonaNaturalRequestDto dto) {
        if (dto == null) {
            return null;
        }


        //completar mapeo
        return PersonaNatural.builder()
                .identificacion(dto.getIdentificacion()) 
                .nombre(dto.getNombre()) 
                .email(dto.getEmail()) 
                .telefono(dto.getTelefono()) 
                .direccion(dto.getDireccion()) 
                .apellido(dto.getApellido()) 
                .genero(TipoGenero.valueOf(dto.getGenero())) 
                .fechaNacimiento(dto.getFechaNacimiento()) 
                .build(); 
    }   
    
    public PersonaJuridica toEntity(PersonaJuridicaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        //completar mapeo
        return PersonaJuridica.builder()
                .identificacion(dto.getIdentificacion()) 
                .nombre(dto.getNombre()) 
                .email(dto.getEmail()) 
                .telefono(dto.getTelefono()) 
                .direccion(dto.getDireccion()) 
                .razonSocial(dto.getRazonSocial()) 
                .representanteLegal(dto.getRepresentanteLegal()) 
                .actividadEconomica(dto.getActividadEconomica()) 
                .build(); 
    }
    
    public PersonaResponseDto toDto(Persona persona) {
        if (persona == null) {
            return null;
        }

        PersonaResponseDto.PersonaResponseDtoBuilder builder = PersonaResponseDto.builder()
                .id(persona.getId())
                .identificacion(persona.getIdentificacion())
                .nombre(persona.getNombre())
                .email(persona.getEmail())
                .telefono(persona.getTelefono())
                .direccion(persona.getDireccion())
                .activo(persona.getActivo())
                .fechaCreacion(persona.getFechaCreacion());

        if (persona instanceof PersonaNatural personaNatural) {
            builder.tipo("NATURAL")
                    .apellido(personaNatural.getApellido())
                    .genero(personaNatural.getGenero())
                    .fechaNacimiento(personaNatural.getFechaNacimiento());
        } else if (persona instanceof PersonaJuridica personaJuridica) {
            builder.tipo("JURIDICA")
                    .razonSocial(personaJuridica.getRazonSocial())
                    .representanteLegal(personaJuridica.getRepresentanteLegal())
                    .actividadEconomica(personaJuridica.getActividadEconomica());
        } else {
            builder.tipo("DESCONOCIDO");
        }

        return builder.build();
    }
    
}
