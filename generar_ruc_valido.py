"""
Generador de RUC válido para Ecuador
"""

def calcular_digito_verificador_ruc(ruc_base):
    """
    Calcula el dígito verificador para un RUC ecuatoriano de sociedad privada
    Los primeros 9 dígitos son la cédula, luego 001 para sociedad privada
    """
    # Coeficientes para RUC de sociedad privada (tipo 9)
    coeficientes = [4, 3, 2, 7, 6, 5, 4, 3, 2]
    
    suma = 0
    for i in range(9):
        suma += int(ruc_base[i]) * coeficientes[i]
    
    residuo = suma % 11
    digito_verificador = 11 - residuo
    
    if digito_verificador == 11:
        digito_verificador = 0
    elif digito_verificador == 10:
        digito_verificador = 1
    
    return digito_verificador

# Generar RUC válido para Guayas (provincia 09)
# Formato: 09 + 7 dígitos + dígito verificador + 001
ruc_base = "099273654"  # 09 (Guayas) + 7 dígitos aleatorios
digito = calcular_digito_verificador_ruc(ruc_base)
ruc_completo = f"{ruc_base}{digito}001"

print(f"RUC válido generado: {ruc_completo}")
print(f"Base: {ruc_base}")
print(f"Dígito verificador: {digito}")
