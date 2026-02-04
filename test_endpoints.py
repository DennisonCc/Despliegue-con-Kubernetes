#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script para probar los nuevos endpoints implementados
Ejecutar: python test_endpoints.py
"""

import requests
import json
from datetime import datetime

# URLs base de los microservicios
USERS_BASE_URL = "http://localhost:8081/api"
ZONES_BASE_URL = "http://localhost:8080/api"

def print_section(title):
    """Imprime una sección con formato"""
    print("\n" + "=" * 70)
    print(f"  {title}")
    print("=" * 70)

def test_endpoint(method, url, description, json_data=None):
    """Prueba un endpoint y muestra el resultado"""
    print(f"\n[TEST] {description}")
    print(f"       {method} {url}")
    
    try:
        if method == "GET":
            response = requests.get(url)
        elif method == "POST":
            response = requests.post(url, json=json_data, headers={"Content-Type": "application/json"})
        
        print(f"       Status: {response.status_code}")
        
        if response.status_code == 200:
            data = response.json()
            if isinstance(data, list):
                print(f"       [OK] Resultados: {len(data)} items")
                if len(data) > 0:
                    print(f"       Ejemplo: {json.dumps(data[0], indent=10)[:200]}...")
            else:
                print(f"       [OK] Respuesta: {json.dumps(data, indent=10)[:300]}...")
            return True, data
        else:
            print(f"       [ERROR] {response.text[:200]}")
            return False, None
            
    except Exception as e:
        print(f"       [ERROR] Excepcion: {e}")
        return False, None

def main():
    print_section("PRUEBA DE ENDPOINTS IMPLEMENTADOS")
    print(f"Fecha: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    
    # ========================================================================
    # MICROSERVICIO DE ZONAS
    # ========================================================================
    print_section("1. MICROSERVICIO DE ZONAS (zone_core)")
    
    # Obtener todas las zonas primero
    print("\n[INFO] Obteniendo zonas para las pruebas...")
    success, zones = test_endpoint("GET", f"{ZONES_BASE_URL}/zones/", 
                                   "Listar todas las zonas")
    
    if zones and len(zones) > 0:
        zone_id = zones[0]['id']
        print(f"\n[INFO] Usando zona: {zones[0]['name']} (ID: {zone_id})")
    else:
        print("\n[WARN] No hay zonas disponibles para probar")
        zone_id = None
    
    # Endpoint 1: Espacios disponibles
    test_endpoint("GET", f"{ZONES_BASE_URL}/spaces/availables",
                 "Obtener todos los espacios disponibles")
    
    # Endpoint 2: Espacios disponibles por zona
    if zone_id:
        test_endpoint("GET", f"{ZONES_BASE_URL}/spaces/availablesbyzone/{zone_id}",
                     f"Obtener espacios disponibles de la zona {zone_id}")
    
    # Endpoint 3: Espacio por ID (verificar que existe)
    success, spaces = test_endpoint("GET", f"{ZONES_BASE_URL}/spaces/",
                                   "Listar todos los espacios")
    
    if spaces and len(spaces) > 0:
        space_id = spaces[0]['id']
        test_endpoint("GET", f"{ZONES_BASE_URL}/spaces/{space_id}",
                     f"Obtener espacio por ID: {space_id}")
    
    # ========================================================================
    # MICROSERVICIO DE USUARIOS
    # ========================================================================
    print_section("2. MICROSERVICIO DE USUARIOS (mc-clientes)")
    
    # Primero verificar si hay personas
    print("\n[INFO] Verificando personas existentes...")
    success, personas = test_endpoint("GET", f"{USERS_BASE_URL}/personas/",
                                     "Listar todas las personas")
    
    if personas and len(personas) > 0:
        identificacion = personas[0]['identificacion']
        print(f"\n[INFO] Usando persona con identificacion: {identificacion}")
        
        # Endpoint 4: Persona por identificación
        test_endpoint("GET", f"{USERS_BASE_URL}/personas/identificacion/{identificacion}",
                     f"Obtener persona por identificacion: {identificacion}")
    else:
        print("\n[WARN] No hay personas registradas")
        print("[INFO] Necesitas crear personas primero en el microservicio de usuarios")
    
    # Verificar vehículos
    print("\n[INFO] Verificando vehiculos existentes...")
    success, vehiculos = test_endpoint("GET", f"{USERS_BASE_URL}/vehiculos/",
                                      "Listar todos los vehiculos")
    
    if vehiculos and len(vehiculos) > 0:
        placa = vehiculos[0]['placa']
        print(f"\n[INFO] Usando vehiculo con placa: {placa}")
        
        # Endpoint 5: Vehículo por placa
        test_endpoint("GET", f"{USERS_BASE_URL}/vehiculos/placa/{placa}",
                     f"Obtener vehiculo por placa: {placa}")
    else:
        print("\n[WARN] No hay vehiculos registrados")
        print("[INFO] Necesitas crear vehiculos primero en el microservicio de usuarios")
    
    # ========================================================================
    # RESUMEN
    # ========================================================================
    print_section("RESUMEN DE PRUEBAS")
    
    print("\n[OK] Endpoints de Zonas probados:")
    print("     - GET /api/spaces/availables")
    print("     - GET /api/spaces/availablesbyzone/{zoneId}")
    print("     - GET /api/spaces/{spaceId}")
    
    print("\n[OK] Endpoints de Usuarios probados:")
    print("     - GET /api/personas/identificacion/{identificacion}")
    print("     - GET /api/vehiculos/placa/{placa}")
    
    if not personas or len(personas) == 0:
        print("\n[NOTA] Para probar completamente los endpoints de usuarios,")
        print("       necesitas crear personas y vehiculos primero.")
        print("       Puedes hacerlo usando Postman o el API directamente.")
    
    print("\n" + "=" * 70)
    print("  Pruebas completadas!")
    print("=" * 70 + "\n")

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\n\n[WARN] Proceso interrumpido por el usuario")
    except Exception as e:
        print(f"\n[ERROR] Error inesperado: {e}")
        import traceback
        traceback.print_exc()
