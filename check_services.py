"""
Script para verificar el estado de todos los microservicios
"""
import requests
import sys

def check_service(name, url, expected_status=200):
    """Verifica si un servicio está funcionando"""
    try:
        response = requests.get(url, timeout=3)
        if response.status_code in [200, 404, expected_status]:
            print(f"✅ {name:20s} - OK (Puerto activo)")
            return True
        else:
            print(f"⚠️  {name:20s} - Respuesta inesperada: {response.status_code}")
            return False
    except requests.exceptions.ConnectionError:
        print(f"❌ {name:20s} - NO DISPONIBLE (Servicio no iniciado)")
        return False
    except requests.exceptions.Timeout:
        print(f"⏱️  {name:20s} - TIMEOUT (Servicio lento)")
        return False
    except Exception as e:
        print(f"❌ {name:20s} - ERROR: {str(e)}")
        return False

def main():
    print("\n" + "="*60)
    print("  VERIFICACIÓN DE MICROSERVICIOS")
    print("="*60 + "\n")
    
    services = [
        ("mc-clientes", "http://localhost:8081/api/personas/"),
        ("zone_core", "http://localhost:8080/api/zones/"),
        ("ms-tickets (GraphQL)", "http://localhost:4000/"),
    ]
    
    results = []
    for name, url in services:
        results.append(check_service(name, url))
    
    print("\n" + "="*60)
    all_ok = all(results)
    
    if all_ok:
        print("✅ TODOS LOS SERVICIOS ESTÁN FUNCIONANDO")
        print("\n📝 Siguiente paso: Ejecuta 'python populate_test_data.py'")
    else:
        print("⚠️  ALGUNOS SERVICIOS NO ESTÁN DISPONIBLES")
        print("\n💡 Inicia los servicios faltantes antes de continuar")
        print("\nComandos para iniciar:")
        print("  mc-clientes: cd mc-clientes && mvn spring-boot:run")
        print("  zone_core:   cd zone_core && mvn spring-boot:run")
        print("  ms-tickets:  cd ms-tickets && npm run dev")
    
    print("="*60 + "\n")
    
    sys.exit(0 if all_ok else 1)

if __name__ == "__main__":
    main()
