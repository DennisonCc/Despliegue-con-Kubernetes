#!/usr/bin/env python3
"""
Script simple para probar la creación de datos uno por uno
"""

import requests
import json

MC_CLIENTES_URL = "http://localhost:8081"
ZONE_CORE_URL = "http://localhost:8080"

def test_persona():
    print("Probando creación de persona...")
    persona = {
        "identificacion": "1701234567",
        "nombre": "Juan Carlos",
        "apellido": "Pérez García",
        "email": "juan.perez@email.com",
        "telefono": "0987654321",
        "direccion": "Av. Amazonas N23-45, Quito",
        "fechaNacimiento": "1990-05-15",
        "genero": "M"
    }

    try:
        response = requests.post(
            f"{MC_CLIENTES_URL}/api/personas/natural",
            json=persona,
            headers={"Content-Type": "application/json"}
        )
        print(f"Status: {response.status_code}")
        print(f"Response: {response.text}")
        return response.status_code in [200, 201]
    except Exception as e:
        print(f"Error: {e}")
        return False

def test_zona():
    print("\nProbando creación de zona...")
    zona = {
        "name": "Zona Norte",
        "description": "Zona de estacionamiento sector norte",
        "capacity": 50,
        "type": "EXTERNAL",
        "isActive": True
    }

    try:
        response = requests.post(
            f"{ZONE_CORE_URL}/api/zones/",
            json=zona,
            headers={"Content-Type": "application/json"}
        )
        print(f"Status: {response.status_code}")
        print(f"Response: {response.text}")
        return response.status_code in [200, 201]
    except Exception as e:
        print(f"Error: {e}")
        return False

if __name__ == "__main__":
    print("=== PRUEBAS INDIVIDUALES ===")
    persona_ok = test_persona()
    zona_ok = test_zona()

    print("\n=== RESULTADOS ===")
    print(f"Persona: {'✓' if persona_ok else '✗'}")
    print(f"Zona: {'✓' if zona_ok else '✗'}")