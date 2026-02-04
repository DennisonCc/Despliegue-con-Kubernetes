package ec.edu.espe.mc_clientes;

import ec.edu.espe.mc_clientes.model.PersonaJuridica;
import ec.edu.espe.mc_clientes.model.PersonaNatural;
import ec.edu.espe.mc_clientes.model.TipoGenero;

import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
       
        // Crear y mostrar Persona Natural
        crearPersonaNatural();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Crear y mostrar Persona Jurídica
        crearPersonaJuridica();
    }
    
    private static void crearPersonaNatural() {
        System.out.println(" CREANDO PERSONA NATURAL\n");
        
        // Crear instancia con datos completos
        PersonaNatural persona = new PersonaNatural();
        persona.setIdentificacion("1713175071");
        persona.setNombre("Juan Carlos");
        persona.setApellido("Pérez García");
        persona.setEmail("juan.perez@email.com");
        persona.setTelefono("0987654321");
        persona.setDireccion("Av. 6 de Diciembre N34-451 y Bosmediano, Quito");
        persona.setGenero(TipoGenero.M);
        persona.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        
        // Mostrar información
        System.out.println("Información del Cliente:");
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("  Nombre completo : " + persona.getNombre() + " " + persona.getApellido());
        System.out.println("  Cédula          : " + persona.getIdentificacion());
        System.out.println("  Email           : " + persona.getEmail());
        System.out.println("  Teléfono        : " + persona.getTelefono());
        System.out.println("  Género          : " + persona.getGenero());
        System.out.println("  Fecha Nac.      : " + persona.getFechaNacimiento());
        System.out.println("  Dirección       : " + persona.getDireccion());
        
        // Validar cédula
        boolean esValida = persona.validarIdentificacion();
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("  Validación      : " + (esValida ? " CÉDULA VÁLIDA" : " CÉDULA INVÁLIDA"));
        
        if (esValida) {
            System.out.println("\n Persona Natural creada exitosamente");
        } else {
            System.out.println("\n Error: La cédula no es válida");
        }
    }
    
    private static void crearPersonaJuridica() {
        System.out.println(" CREANDO PERSONA JURÍDICA\n");
        
        // Crear instancia con datos completos
        PersonaJuridica empresa = new PersonaJuridica();
        empresa.setIdentificacion("1792146739001");
        empresa.setNombre("TechSolutions");
        empresa.setRazonSocial("TechSolutions Cia. Ltda.");
        empresa.setEmail("info@techsolutions.com.ec");
        empresa.setTelefono("022345678");
        empresa.setDireccion("Av. República del Salvador N34-183 y Suiza, Quito");
        empresa.setRepresentanteLegal("María Elena Rodríguez");
        empresa.setActividadEconomica("Desarrollo de Software y Consultoría IT");
        
        // Mostrar información
        System.out.println("Información de la Empresa:");
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("  Razón Social    : " + empresa.getRazonSocial());
        System.out.println("  Nombre Comercial: " + empresa.getNombre());
        System.out.println("  RUC             : " + empresa.getIdentificacion());
        System.out.println("  Email           : " + empresa.getEmail());
        System.out.println("  Teléfono        : " + empresa.getTelefono());
        System.out.println("  Representante   : " + empresa.getRepresentanteLegal());
        System.out.println("  Actividad       : " + empresa.getActividadEconomica());
        System.out.println("  Dirección       : " + empresa.getDireccion());
        
        // Validar RUC
        boolean esValido = empresa.validarIdentificacion();
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("  Validación      : " + (esValido ? " RUC VÁLIDO" : " RUC INVÁLIDO"));
        
        if (esValido) {
            System.out.println("\n Persona Jurídica creada exitosamente");
        } else {
            System.out.println("\n Error: El RUC no es válido");
        }
    }
}
