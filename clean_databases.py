#!/usr/bin/env python3
"""
Script para limpiar todas las bases de datos de los microservicios
"""

import psycopg2
from psycopg2 import sql
import sys

# Configuración de las bases de datos
DATABASES = {
    'mc-clientes': {
        'host': 'localhost',
        'port': 5434,
        'database': 'db_parkin_users',
        'user': 'parkin',
        'password': 'qwerty123',
        'tables': ['automovil', 'moto', 'vehiculo', 'persona_natural', 'persona_juridica', 'personas']
    },
    'zone_core': {
        'host': 'localhost',
        'port': 5435,
        'database': 'db_parkin_zone',
        'user': 'parkin',
        'password': 'qwerty123',
        'tables': ['spaces', 'zones']  # Orden: primero espacios, luego zonas
    },
    'ms-tickets': {
        'host': 'localhost',
        'port': 5437,
        'database': 'db_parkin_tickets',
        'user': 'parkin',
        'password': 'qwerty123',
        'tables': ['tickets', 'Tickets']
    }
}

def clean_database(db_name, db_config):
    """Limpia todas las tablas de una base de datos"""
    print(f"\n🗑️  Limpiando base de datos: {db_name}")
    print(f"   Host: {db_config['host']}:{db_config['port']}")
    print(f"   Database: {db_config['database']}")
    
    try:
        # Conectar a la base de datos
        conn = psycopg2.connect(
            host=db_config['host'],
            port=db_config['port'],
            database=db_config['database'],
            user=db_config['user'],
            password=db_config['password']
        )
        conn.autocommit = True
        cursor = conn.cursor()
        
        # Deshabilitar restricciones de claves foráneas temporalmente
        cursor.execute("SET session_replication_role = 'replica';")
        
        total_deleted = 0
        for table in db_config['tables']:
            try:
                # Eliminar todos los registros de la tabla
                cursor.execute(sql.SQL("DELETE FROM {}").format(sql.Identifier(table)))
                deleted = cursor.rowcount
                total_deleted += deleted
                print(f"   ✓ Tabla '{table}': {deleted} registros eliminados")
                
                # Reiniciar secuencias si existen
                cursor.execute(f"""
                    SELECT column_name 
                    FROM information_schema.columns 
                    WHERE table_name = '{table}' 
                    AND column_default LIKE 'nextval%'
                """)
                sequences = cursor.fetchall()
                for seq in sequences:
                    cursor.execute(sql.SQL("ALTER SEQUENCE {}_id_seq RESTART WITH 1").format(sql.Identifier(table)))
                
            except Exception as e:
                print(f"   ⚠️  Error al limpiar tabla '{table}': {str(e)}")
        
        # Rehabilitar restricciones de claves foráneas
        cursor.execute("SET session_replication_role = 'origin';")
        
        cursor.close()
        conn.close()
        
        print(f"   ✓ Total: {total_deleted} registros eliminados")
        return True
        
    except psycopg2.Error as e:
        print(f"   ❌ Error de conexión: {str(e)}")
        return False
    except Exception as e:
        print(f"   ❌ Error: {str(e)}")
        return False

def main():
    print("=" * 70)
    print("  LIMPIEZA DE BASES DE DATOS")
    print("  Sistema de Gestión de Estacionamientos")
    print("=" * 70)
    
    success_count = 0
    failed_count = 0
    
    for db_name, db_config in DATABASES.items():
        if clean_database(db_name, db_config):
            success_count += 1
        else:
            failed_count += 1
    
    print("\n" + "=" * 70)
    print("  RESUMEN")
    print("=" * 70)
    print(f"✓ Bases de datos limpiadas exitosamente: {success_count}")
    if failed_count > 0:
        print(f"❌ Bases de datos con errores: {failed_count}")
    print("=" * 70)
    
    if failed_count == 0:
        print("\n✅ Todas las bases de datos han sido limpiadas exitosamente")
        print("📝 Ahora puedes ejecutar: python populate_test_data.py")
    else:
        print("\n⚠️  Algunas bases de datos no pudieron ser limpiadas")
        sys.exit(1)

if __name__ == "__main__":
    main()
