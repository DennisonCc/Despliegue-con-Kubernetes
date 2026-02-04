package ec.edu.espe.mc_clientes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

    @Entity
    @Table(name = "persona_juridica")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
public class PersonaJuridica extends Persona {

    @Column(nullable = false, unique = true)
    private String razonSocial;

    @Column(nullable = false)
    private String representanteLegal;

    @Column(nullable = false)
    private String actividadEconomica;

    @Override
    public boolean validarIdentificacion() {
        // Validación de RUC ecuatoriano para persona jurídica (13 dígitos)
        if (this.getIdentificacion() == null || this.getIdentificacion().length() != 13) {
            return false;
        }
        
        try {
            String ruc = this.getIdentificacion();
            
            // Los dos primeros dígitos deben estar entre 01 y 24 (provincias del Ecuador)
            int provincia = Integer.parseInt(ruc.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false;
            }
            
            // El tercer dígito debe ser 9 (para empresas privadas y extranjeras)
            int tercerDigito = Integer.parseInt(ruc.substring(2, 3));
            if (tercerDigito != 9) {
                return false;
            }
            
            // Los últimos 3 dígitos deben ser 001 o mayor
            int establecimiento = Integer.parseInt(ruc.substring(10, 13));
            if (establecimiento < 1) {
                return false;
            }
            
            // Algoritmo módulo 11 para validar el dígito verificador
            int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
            int suma = 0;
            
            for (int i = 0; i < 9; i++) {
                int digito = Integer.parseInt(ruc.substring(i, i + 1));
                suma += digito * coeficientes[i];
            }
            
            // Calcular el dígito verificador
            int residuo = suma % 11;
            int digitoVerificador = residuo == 0 ? 0 : 11 - residuo;
            
            // Comparar con el décimo dígito del RUC
            int decimoDigito = Integer.parseInt(ruc.substring(9, 10));
            return digitoVerificador == decimoDigito;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
