"""
Script para poblar los microservicios con datos de prueba.
Crea 5 registros de cada tipo en los microservicios mc-clientes y zone_core.
"""
import requests
import json
from datetime import datetime, timedelta
import random
import time

# Configuración de URLs de los microservicios
MC_CLIENTES_URL = "http://localhost:8081/api"
ZONE_CORE_URL = "http://localhost:8080/api"

# Colores para la terminal
class Colors:
    GREEN = '\033[92m'
    YELLOW = '\033[93m'
    RED = '\033[91m'
    BLUE = '\033[94m'
    END = '\033[0m'

def print_success(message):
    print(f"{Colors.GREEN}✓ {message}{Colors.END}")

def print_info(message):
    print(f"{Colors.BLUE}ℹ {message}{Colors.END}")

def print_warning(message):
    print(f"{Colors.YELLOW}⚠ {message}{Colors.END}")

def print_error(message):
    print(f"{Colors.RED}✗ {message}{Colors.END}")

# ==================== DATOS DE PRUEBA ====================

# Personas Naturales
personas_naturales = [
    {
        "identificacion": "1701234567",  # Cédula válida de Pichincha
        "nombre": "Juan Carlos",
        "email": "juan.perez@email.com",
        "telefono": "0987654321",
        "direccion": "Av. Amazonas N23-45, Quito",
        "apellido": "Pérez García",
        "fechaNacimiento": "1990-05-15",
        "genero": "M"
    },
    {
        "identificacion": "0902345677",  # Cédula válida de Guayas
        "nombre": "María Elena",
        "email": "maria.rodriguez@email.com",
        "telefono": "0998765432",
        "direccion": "Calle 10 de Agosto 234, Guayaquil",
        "apellido": "Rodríguez Silva",
        "fechaNacimiento": "1985-08-22",
        "genero": "F"
    },
    {
        "identificacion": "1803456787",  # Cédula válida de Tungurahua
        "nombre": "Pedro Antonio",
        "email": "pedro.morales@email.com",
        "telefono": "0976543210",
        "direccion": "Av. 12 de Noviembre, Ambato",
        "apellido": "Morales López",
        "fechaNacimiento": "1992-03-10",
        "genero": "M"
    }
]

# Personas Jurídicas
personas_juridicas = [
    {
        "identificacion": "1790123456001",  # RUC válido
        "nombre": "TechCorp S.A.",
        "email": "contacto@techcorp.com",
        "telefono": "022345678",
        "direccion": "Av. República 100, Quito",
        "razonSocial": "TechCorp Sociedad Anónima",
        "representanteLegal": "Roberto Sánchez",
        "actividadEconomica": "Desarrollo de Software"
    },
    {
        "identificacion": "0992736542001",  # RUC válido de Guayas (calculado con algoritmo)
        "nombre": "Comercial Pacífico",
        "email": "info@compac.com",
        "telefono": "042567890",
        "direccion": "Av. 9 de Octubre 456, Guayaquil",
        "razonSocial": "Comercial Pacífico Cía. Ltda.",
        "representanteLegal": "Ana Martínez",
        "actividadEconomica": "Comercio al por mayor"
    }
]

# Zonas de estacionamiento
zonas = [
    {
        "name": "Zona Norte",
        "description": "Zona de estacionamiento sector norte de la ciudad",
        "capacity": 50,
        "type": "EXTERNAL",
        "isActive": True
    },
    {
        "name": "Zona Centro",
        "description": "Zona de estacionamiento en el centro histórico",
        "capacity": 30,
        "type": "EXTERNAL",
        "isActive": True
    },
    {
        "name": "Zona Sur",
        "description": "Zona de estacionamiento sector sur",
        "capacity": 40,
        "type": "EXTERNAL",
        "isActive": True
    },
    {
        "name": "Zona VIP Norte",
        "description": "Zona reservada para clientes VIP sector norte",
        "capacity": 20,
        "type": "VIP",
        "isActive": True
    },
    {
        "name": "Zona Empresarial",
        "description": "Zona exclusiva para empresas asociadas",
        "capacity": 35,
        "type": "INTERNAL",
        "isActive": True
    }
]

# Marcas y modelos de vehículos
marcas_modelos = [
    ("Chevrolet", ["Aveo", "Sail", "Cruze", "Spark", "Captiva"]),
    ("Toyota", ["Corolla", "Yaris", "RAV4", "Hilux", "Prado"]),
    ("Nissan", ["Sentra", "Versa", "X-Trail", "Qashqai", "Frontier"]),
    ("KIA", ["Rio", "Sportage", "Picanto", "Sorento", "Seltos"]),
    ("Mazda", ["3", "CX-5", "2", "CX-3", "6"])
]

colores = ["Blanco", "Negro", "Gris", "Plata", "Azul", "Rojo", "Verde"]

# ==================== FUNCIONES AUXILIARES ====================

def generar_placa():
    """Genera una placa ecuatoriana válida"""
    letras = ''.join(random.choices('ABCDEFGHIJKLMNOPQRSTUVWXYZ', k=3))
    numeros = ''.join(random.choices('0123456789', k=4))
    return f"{letras}-{numeros}"

def esperar_microservicio(url, nombre, max_intentos=30):
    """Espera a que un microservicio esté disponible"""
    print_info(f"Verificando disponibilidad de {nombre}...")
    for i in range(max_intentos):
        try:
            response = requests.get(url, timeout=2)
            if response.status_code in [200, 404]:
                print_success(f"{nombre} está disponible")
                return True
        except requests.exceptions.RequestException:
            if i == max_intentos - 1:
                print_error(f"{nombre} no está disponible después de {max_intentos} intentos")
                return False
            time.sleep(2)
    return False

# ==================== FUNCIONES DE POBLACIÓN ====================

def crear_personas_naturales():
    """Crea personas naturales en mc-clientes"""
    print_info("\n📋 Creando Personas Naturales...")
    personas_creadas = []
    
    for persona in personas_naturales:
        try:
            response = requests.post(
                f"{MC_CLIENTES_URL}/personas/natural",
                json=persona,
                headers={"Content-Type": "application/json"}
            )
            
            if response.status_code in [200, 201]:
                data = response.json()
                personas_creadas.append(data)
                print_success(f"Persona creada: {persona['nombre']} {persona['apellido']} (ID: {data.get('identificacion', 'N/A')})")
            else:
                print_warning(f"Error al crear persona {persona['nombre']}: {response.status_code} - {response.text}")
        except Exception as e:
            print_error(f"Excepción al crear persona {persona['nombre']}: {str(e)}")
    
    return personas_creadas

def crear_personas_juridicas():
    """Crea personas jurídicas en mc-clientes"""
    print_info("\n🏢 Creando Personas Jurídicas...")
    personas_creadas = []
    
    for persona in personas_juridicas:
        try:
            response = requests.post(
                f"{MC_CLIENTES_URL}/personas/juridica",
                json=persona,
                headers={"Content-Type": "application/json"}
            )
            
            if response.status_code in [200, 201]:
                data = response.json()
                personas_creadas.append(data)
                print_success(f"Empresa creada: {persona['nombre']} (RUC: {data.get('identificacion', 'N/A')})")
            else:
                print_warning(f"Error al crear empresa {persona['nombre']}: {response.status_code} - {response.text}")
        except Exception as e:
            print_error(f"Excepción al crear empresa {persona['nombre']}: {str(e)}")
    
    return personas_creadas

def crear_vehiculos(personas_creadas):
    """Crea vehículos (automóviles y motos) para cada persona"""
    print_info("\n🚗 Creando Vehículos...")
    vehiculos_creados = []
    
    for idx, persona in enumerate(personas_creadas):
        marca, modelos = random.choice(marcas_modelos)
        modelo = random.choice(modelos)
        
        # Alternar entre automóvil y moto
        es_moto = idx % 3 == 0  # Cada 3 vehículos, crear una moto
        
        if es_moto:
            # Crear moto
            vehiculo = {
                "placa": generar_placa(),
                "marca": random.choice(["Yamaha", "Honda", "Suzuki", "Kawasaki"]),
                "modelo": random.choice(["FZ-16", "CB190R", "GN125", "Ninja 300"]),
                "color": random.choice(colores),
                "anioFabricacion": random.randint(2015, 2024),
                "propietarioId": persona.get('id'),
                "cilindraje": random.choice([125, 150, 200, 250, 300]),
                "tipo": random.choice(["DEPORTIVA", "TOURING", "CRUISER", "SCOOTER"]),
                "tieneCasco": True
            }
            
            try:
                response = requests.post(
                    f"{MC_CLIENTES_URL}/vehiculos/moto",
                    json=vehiculo,
                    headers={"Content-Type": "application/json"}
                )
                
                if response.status_code in [200, 201]:
                    data = response.json()
                    vehiculos_creados.append(data)
                    print_success(f"Moto creada: {vehiculo['marca']} {vehiculo['modelo']} - Placa: {vehiculo['placa']}")
                else:
                    print_warning(f"Error al crear moto: {response.status_code} - {response.text}")
            except Exception as e:
                print_error(f"Excepción al crear moto: {str(e)}")
        else:
            # Crear automóvil
            vehiculo = {
                "placa": generar_placa(),
                "marca": marca,
                "modelo": modelo,
                "color": random.choice(colores),
                "anioFabricacion": random.randint(2015, 2024),
                "propietarioId": persona.get('id'),
                "tipo": random.choice(["SEDAN", "SUV", "HATCHBACK", "COUPE", "CONVERTIBLE"]),
                "combustible": random.choice(["GASOLINA", "DIESEL", "ELECTRICO", "HIBRIDO"]),
                "numeroPuertas": random.choice([2, 4, 5]),
                "numeroPasajeros": random.randint(2, 7),
                "cilindrada": random.randint(1000, 3000),
                "transmision": random.choice(["Manual", "Automatica", "CVT"]),
                "aireAcondicionado": random.choice([True, False]),
                "abs": random.choice([True, False]),
                "airbags": random.choice([True, False])
            }
            
            try:
                response = requests.post(
                    f"{MC_CLIENTES_URL}/vehiculos/automovil",
                    json=vehiculo,
                    headers={"Content-Type": "application/json"}
                )
                
                if response.status_code in [200, 201]:
                    data = response.json()
                    vehiculos_creados.append(data)
                    print_success(f"Automóvil creado: {vehiculo['marca']} {vehiculo['modelo']} - Placa: {vehiculo['placa']}")
                else:
                    print_warning(f"Error al crear automóvil: {response.status_code} - {response.text}")
            except Exception as e:
                print_error(f"Excepción al crear automóvil: {str(e)}")
    
    return vehiculos_creados

def crear_zonas():
    """Crea zonas de estacionamiento"""
    print_info("\n🏢 Creando Zonas de Estacionamiento...")
    zonas_creadas = []
    
    for zona in zonas:
        try:
            response = requests.post(
                f"{ZONE_CORE_URL}/zones/",
                json=zona,
                headers={"Content-Type": "application/json"}
            )
            
            if response.status_code in [200, 201]:
                data = response.json()
                zonas_creadas.append(data)
                print_success(f"Zona creada: {zona['name']} (Capacidad: {zona['capacity']})")
            else:
                print_warning(f"Error al crear zona {zona['name']}: {response.status_code} - {response.text}")
        except Exception as e:
            print_error(f"Excepción al crear zona {zona['name']}: {str(e)}")
    
    return zonas_creadas

def crear_espacios(zonas_creadas):
    """Crea espacios de estacionamiento para cada zona"""
    print_info("\n🅿️  Creando Espacios de Estacionamiento...")
    espacios_creados = []
    
    if not zonas_creadas:
        print_warning("No hay zonas creadas, no se pueden crear espacios")
        return espacios_creados
    
    print_info(f"Se crearán espacios para {len(zonas_creadas)} zonas")
    time.sleep(1)  # Dar tiempo para que las zonas se guarden completamente
    
    for idx, zona in enumerate(zonas_creadas, 1):
        zona_id = zona.get('id')
        zona_name = zona.get('name')
        capacidad = zona.get('capacity', 10)
        
        print_info(f"\n[{idx}/{len(zonas_creadas)}] Procesando zona: {zona_name} (ID: {zona_id})")
        
        if not zona_id:
            print_warning(f"Zona {zona_name} no tiene ID, saltando...")
            continue
        
        # Crear espacios según la capacidad de la zona (mínimo 5, máximo 10)
        num_espacios = max(5, min(capacidad, 10))  # Al menos 5 espacios por zona
        
        print_info(f"Creando {num_espacios} espacios para {zona_name}...")
        espacios_zona = 0
        
        for i in range(1, num_espacios + 1):
            # Generar código único para el espacio usando las primeras letras del nombre de la zona
            # Eliminar espacios y tomar las primeras 3-4 letras
            palabras = zona_name.split()
            if len(palabras) > 1:
                # Si tiene múltiples palabras, tomar primera letra de cada una
                codigo_zona = ''.join([p[0].upper() for p in palabras[:3]])
            else:
                # Si es una sola palabra, tomar las primeras 3 letras
                codigo_zona = zona_name[:3].upper().replace(" ", "")
            
            # IMPORTANTE: El campo 'name' tiene límite de 20 caracteres en la BD
            # Formato: "ZN-001" (6 chars) o "Espacio ZN-001" (14 chars max)
            espacio_code = f"{codigo_zona}-{i:03d}"
            espacio_name = f"Espacio {espacio_code}"  # Ej: "Espacio ZN-001" (14 chars)
            
            espacio = {
                "name": espacio_name,  # Máximo 14-16 caracteres
                "code": espacio_code,  # Ej: ZN-001, ZC-001, ZS-001, ZVN-001, ZE-001
                "status": "AVALIABLE",
                "isReserved": False,
                "priority": i,
                "zoneId": zona_id
            }
            
            try:
                response = requests.post(
                    f"{ZONE_CORE_URL}/spaces/",
                    json=espacio,
                    headers={"Content-Type": "application/json"},
                    timeout=10
                )
                
                if response.status_code in [200, 201]:
                    data = response.json()
                    espacios_creados.append(data)
                    espacios_zona += 1
                    print_success(f"  ✓ {espacio['code']} creado")
                else:
                    error_msg = response.text[:200] if response.text else "Sin mensaje"
                    print_warning(f"  ✗ Error al crear {espacio['code']}: HTTP {response.status_code}")
                    print_warning(f"     Respuesta: {error_msg}")
            except requests.exceptions.Timeout:
                print_error(f"  ✗ Timeout al crear espacio {espacio['code']}")
            except requests.exceptions.ConnectionError as e:
                print_error(f"  ✗ Error de conexión al crear {espacio['code']}: {str(e)[:100]}")
            except Exception as e:
                print_error(f"  ✗ Excepción al crear {espacio['code']}: {str(e)[:100]}")
            
            # Pequeño delay entre creaciones para evitar sobrecarga
            time.sleep(0.1)
        
        print_info(f"✓ Zona {zona_name}: {espacios_zona}/{num_espacios} espacios creados")
    
    return espacios_creados
    
    return espacios_creados

# ==================== FUNCIÓN PRINCIPAL ====================

def main():
    print("\n" + "="*70)
    print("  SCRIPT DE POBLACIÓN DE DATOS DE PRUEBA")
    print("  Sistema de Gestión de Estacionamientos")
    print("="*70)
    
    # Verificar disponibilidad de microservicios
    if not esperar_microservicio(f"{MC_CLIENTES_URL}/personas/", "mc-clientes"):
        print_error("\n❌ mc-clientes no está disponible. Asegúrate de que esté ejecutándose en el puerto 8081")
        return
    
    if not esperar_microservicio(f"{ZONE_CORE_URL}/zones/", "zone_core"):
        print_error("\n❌ zone_core no está disponible. Asegúrate de que esté ejecutándose en el puerto 8080")
        return
    
    print_success("\n✓ Todos los microservicios están disponibles\n")
    
    # Crear datos de prueba
    personas_naturales_creadas = crear_personas_naturales()
    personas_juridicas_creadas = crear_personas_juridicas()
    
    # Combinar todas las personas para crear vehículos
    todas_personas = personas_naturales_creadas + personas_juridicas_creadas
    vehiculos_creados = crear_vehiculos(todas_personas)
    
    zonas_creadas = crear_zonas()
    espacios_creados = crear_espacios(zonas_creadas)
    
    # Resumen
    print("\n" + "="*70)
    print("  RESUMEN DE DATOS CREADOS")
    print("="*70)
    print_success(f"Personas Naturales: {len(personas_naturales_creadas)}")
    print_success(f"Personas Jurídicas: {len(personas_juridicas_creadas)}")
    print_success(f"Total Personas: {len(todas_personas)}")
    print_success(f"Vehículos: {len(vehiculos_creados)}")
    print_success(f"Zonas: {len(zonas_creadas)}")
    print_success(f"Espacios Totales: {len(espacios_creados)}")
    
    # Mostrar espacios por zona
    if zonas_creadas and espacios_creados:
        print("\n📊 Espacios por Zona:")
        for zona in zonas_creadas:
            zona_id = zona.get('id')
            zona_name = zona.get('name')
            espacios_en_zona = sum(1 for e in espacios_creados if e.get('zoneId') == zona_id)
            print(f"  • {zona_name}: {espacios_en_zona} espacios")
    
    # Información para pruebas de GraphQL
    print("\n" + "="*70)
    print("  DATOS PARA PRUEBAS DE GRAPHQL (ms-tickets)")
    print("="*70)
    
    if todas_personas and vehiculos_creados:
        print_info("\n📝 Puedes usar estos datos para probar GraphQL:")
        print("\nEjemplo de mutación emitirTicket:")
        print("-" * 70)
        persona_ejemplo = todas_personas[0]
        vehiculo_ejemplo = vehiculos_creados[0]
        print(f"""
mutation {{
  emitirTicket(
    personaIdentificacion: "{persona_ejemplo.get('identificacion', 'N/A')}"
    vehiculoPlaca: "{vehiculo_ejemplo.get('placa', 'N/A')}"
  ) {{
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
    fechaIngreso
  }}
}}
""")
        
        print("\nOtros datos disponibles:")
        print("-" * 70)
        for i, (persona, vehiculo) in enumerate(zip(todas_personas[:3], vehiculos_creados[:3]), 1):
            nombre = persona.get('nombre', 'N/A')
            print(f"{i}. {nombre} - ID: {persona.get('identificacion', 'N/A')} - Placa: {vehiculo.get('placa', 'N/A')}")
    
    print("\n" + "="*70)
    print_success("✅ Proceso completado exitosamente")
    print("="*70)
    print_info(f"\nAccede a GraphQL Playground en: http://localhost:4000")
    print_info("Para ver todos los tickets: query { tickets { id codigoTicket personaNombre } }")
    print("\n")

if __name__ == "__main__":
    main()
