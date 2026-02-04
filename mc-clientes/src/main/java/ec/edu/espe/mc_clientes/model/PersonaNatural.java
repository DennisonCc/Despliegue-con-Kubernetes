package ec.edu.espe.mc_clientes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "persona_natural")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@DiscriminatorValue("NATURAL")
@PrimaryKeyJoinColumn(name = "persona_id")
public class PersonaNatural extends Persona {

	@Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    @Enumerated
    private TipoGenero genero; //M - F - O


    @Column(nullable = false)
    private LocalDate fechaNacimiento; // formato YYYY-MM-DD

	@Override
	public boolean validarIdentificacion() {
		if (this.getIdentificacion() == null || this.getIdentificacion().length() != 10) {
			return false;
		}
		
		try {
			String cedula = this.getIdentificacion();
			
			// Los dos primeros dígitos deben estar entre 01 y 24 (provincias del Ecuador)
			int provincia = Integer.parseInt(cedula.substring(0, 2));
			if (provincia < 1 || provincia > 24) {
				return false;
			}
			
			// El tercer dígito debe ser menor a 6 (para personas naturales)
			int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
			if (tercerDigito >= 6) {
				return false;
			}
			
			// Algoritmo módulo 10 para validar el dígito verificador
			int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
			int suma = 0;
			
			for (int i = 0; i < 9; i++) {
				int digito = Integer.parseInt(cedula.substring(i, i + 1));
				int producto = digito * coeficientes[i];
				
				// Si el producto es mayor a 9, se resta 9
				if (producto >= 10) {
					producto -= 9;
				}
				
				suma += producto;
			}
			
			// Calcular el dígito verificador
			int residuo = suma % 10;
			int digitoVerificador = residuo == 0 ? 0 : 10 - residuo;
			
			// Comparar con el último dígito de la cédula
			int ultimoDigito = Integer.parseInt(cedula.substring(9, 10));
			return digitoVerificador == ultimoDigito;
			
		} catch (NumberFormatException e) {
			return false;
		}
	}


}
