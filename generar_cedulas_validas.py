def validar_cedula_ecuatoriana(cedula):
    """Valida una cédula ecuatoriana usando el algoritmo módulo 10"""
    if len(cedula) != 10:
        return False
    
    try:
        # Los dos primeros dígitos deben estar entre 01 y 24
        provincia = int(cedula[:2])
        if provincia < 1 or provincia > 24:
            return False
        
        # El tercer dígito debe ser menor a 6
        if int(cedula[2]) >= 6:
            return False
        
        # Algoritmo módulo 10
        coeficientes = [2, 1, 2, 1, 2, 1, 2, 1, 2]
        suma = 0
        
        for i in range(9):
            producto = int(cedula[i]) * coeficientes[i]
            if producto >= 10:
                producto -= 9
            suma += producto
        
        residuo = suma % 10
        digito_verificador = 0 if residuo == 0 else 10 - residuo
        
        return digito_verificador == int(cedula[9])
    except:
        return False

def generar_cedula_valida(provincia, base):
    """Genera una cédula válida para una provincia dada"""
    provincia_str = str(provincia).zfill(2)
    tercero = "0"  # Debe ser menor a 6
    
    # Generar los siguientes 6 dígitos
    siguientes = str(base).zfill(6)
    
    # Calcular dígito verificador
    cedula_sin_verificador = provincia_str + tercero + siguientes
    coeficientes = [2, 1, 2, 1, 2, 1, 2, 1, 2]
    suma = 0
    
    for i in range(9):
        producto = int(cedula_sin_verificador[i]) * coeficientes[i]
        if producto >= 10:
            producto -= 9
        suma += producto
    
    residuo = suma % 10
    digito_verificador = 0 if residuo == 0 else 10 - residuo
    
    cedula_completa = cedula_sin_verificador + str(digito_verificador)
    return cedula_completa

# Generar 5 cédulas válidas para diferentes provincias
cedulas_generadas = [
    generar_cedula_valida(17, 123456),  # Pichincha
    generar_cedula_valida(9, 234567),   # Guayas
    generar_cedula_valida(18, 345678),  # Tungurahua
    generar_cedula_valida(1, 456789),   # Azuay
    generar_cedula_valida(13, 567890),  # Manabí
]

print("Cédulas válidas generadas:")
print("="*50)
for i, cedula in enumerate(cedulas_generadas, 1):
    es_valida = validar_cedula_ecuatoriana(cedula)
    print(f"{i}. {cedula} - {'✓ VÁLIDA' if es_valida else '✗ INVÁLIDA'}")
print("="*50)
print("\nPara usar en el script:")
for cedula in cedulas_generadas:
    print(f'    "{cedula}",')

