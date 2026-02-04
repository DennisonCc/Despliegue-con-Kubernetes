# API Gateway con Kong - Microservicios Parkin

Este proyecto integra dos microservicios usando Kong API Gateway:
- **mc-clientes**: Microservicio de gestión de clientes (Puerto 8081)
- **zone-core**: Microservicio de gestión de zonas (Puerto 8080)

## Arquitectura

```
Cliente → Kong (Puerto 8000) → Microservicios
                ↓
          Admin API (Puerto 8001)
```

## Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Docker Compose
- PowerShell (Windows)

## Iniciar el Sistema

### 1. Levantar todos los servicios

```powershell
docker-compose up -d
```

Este comando levantará:
- Kong Database (PostgreSQL - Puerto 5433)
- Kong API Gateway (Puerto 8000 para proxy, 8001 para admin)
- PostgreSQL para mc-clientes (Puerto 5434)
- PostgreSQL para zone-core (Puerto 5432)
- Microservicio mc-clientes (Puerto 8081)
- Microservicio zone-core (Puerto 8080)

### 2. Configurar rutas en Kong

Espera aproximadamente 30 segundos hasta que todos los servicios estén listos, luego ejecuta:

```powershell
.\kong-config.ps1
```

## Endpoints Disponibles

### A través del API Gateway (Puerto 8000)

- **Clientes**: `http://localhost:8000/clientes/*`
  - Redirige a mc-clientes en `http://mc-clientes:8081/*`
  
- **Zonas**: `http://localhost:8000/zones/*`
  - Redirige a zone-core en `http://zone-core:8080/*`

### Acceso Directo (Bypass del Gateway)

- **mc-clientes**: `http://localhost:8081`
- **zone-core**: `http://localhost:8080`

### Kong Admin API

- **Admin API**: `http://localhost:8001`

## Ejemplos de Uso

### Listar servicios configurados en Kong

```powershell
Invoke-RestMethod -Uri "http://localhost:8001/services" -Method GET
```

### Listar rutas configuradas

```powershell
Invoke-RestMethod -Uri "http://localhost:8001/routes" -Method GET
```

### Probar el API Gateway

```powershell
# Acceder a clientes
Invoke-WebRequest -Uri "http://localhost:8000/clientes" -Method GET

# Acceder a zones
Invoke-WebRequest -Uri "http://localhost:8000/zones" -Method GET
```

## Plugins Configurados

### 1. CORS
- Permite peticiones desde cualquier origen
- Métodos permitidos: GET, POST, PUT, DELETE, PATCH, OPTIONS

### 2. Rate Limiting
- Límite: 100 peticiones por minuto
- Límite: 1000 peticiones por hora

## Comandos Útiles

### Ver logs de Kong

```powershell
docker logs kong-gateway -f
```

### Ver logs de microservicios

```powershell
docker logs mc-clientes -f
docker logs zone-core -f
```

### Reiniciar servicios

```powershell
docker-compose restart
```

### Detener todos los servicios

```powershell
docker-compose down
```

### Detener y eliminar volúmenes

```powershell
docker-compose down -v
```

## Gestión de Kong

### Agregar un nuevo plugin

```powershell
$body = @{
    name = "key-auth"
}
Invoke-RestMethod -Uri "http://localhost:8001/plugins" -Method POST -Body $body
```

### Eliminar una ruta

```powershell
Invoke-RestMethod -Uri "http://localhost:8001/routes/{route-id}" -Method DELETE
```

### Ver estado de Kong

```powershell
Invoke-RestMethod -Uri "http://localhost:8001/status" -Method GET
```

## Troubleshooting

### Kong no inicia

1. Verificar que la base de datos esté lista:
```powershell
docker logs kong-database
```

2. Verificar que las migraciones se ejecutaron:
```powershell
docker logs kong-bootstrap
```

### Microservicio no responde

1. Verificar logs del microservicio:
```powershell
docker logs mc-clientes
docker logs zone-core
```

2. Verificar que las bases de datos estén listas:
```powershell
docker logs parkin_users_db
docker logs parkin_zone_db
```

### Reconfigurar Kong desde cero

```powershell
docker-compose down -v
docker-compose up -d
# Esperar 30 segundos
.\kong-config.ps1
```

## Puertos Utilizados

| Servicio | Puerto |
|----------|--------|
| Kong Proxy | 8000 |
| Kong Admin API | 8001 |
| mc-clientes | 8081 |
| zone-core | 8080 |
| PostgreSQL (zone-core) | 5432 |
| PostgreSQL (Kong) | 5433 |
| PostgreSQL (mc-clientes) | 5434 |

## Arquitectura de Red

Todos los servicios están conectados a la red `parkin-network` que permite la comunicación entre contenedores.
