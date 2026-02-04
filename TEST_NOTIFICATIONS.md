# 🧪 Guía de Pruebas - Sistema de Notificaciones RabbitMQ

## 📋 Pre-requisitos

Verificar que todos los servicios estén corriendo:
```powershell
# Zone Core (Java Spring Boot)
docker ps --filter "name=zone-core"

# RabbitMQ
docker ps --filter "name=rabbitmq"

# Notification Service (Node.js)
netstat -ano | findstr :3001
```

## 🚀 Pasos para Probar

### 1. Crear una Zona

**Endpoint:** `POST http://localhost:8080/api/zones/`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Zona Norte",
  "code": "ZN-001",
  "description": "Zona de parqueo Norte",
  "latitude": -0.2099,
  "longitude": -78.4952,
  "pricePerHour": 2.50,
  "startSchedule": "06:00",
  "endSchedule": "22:00",
  "capacity": 100,
  "disabled": false
}
```

**Respuesta esperada:**
```json
{
  "id": 1,
  "name": "Zona Norte",
  "code": "ZN-001",
  ...
}
```

### 2. Crear un Espacio (Esto envía la notificación)

**Endpoint:** `POST http://localhost:8080/api/spaces/`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "zoneId": 1,
  "name": "Espacio A-101",
  "code": "A-101",
  "status": "DISPONIBLE",
  "description": "Espacio en primer piso",
  "pricePerHour": 2.50,
  "disabled": false
}
```

**Respuesta esperada:**
```json
{
  "id": 1,
  "zone": {
    "id": 1,
    "name": "Zona Norte"
  },
  "name": "Espacio A-101",
  "code": "A-101",
  "status": "DISPONIBLE",
  ...
}
```

## ✅ Verificar que la Notificación fue Procesada

### Opción 1: Ver Logs del Zone Core
```powershell
docker logs zone-core --tail 20
```
Deberías ver algo como:
```
Sending notification to RabbitMQ: NotificationEvent(type=SPACES_CREATED, ...)
```

### Opción 2: Ver Logs del Notification Service
Busca en la terminal de Node.js (puerto 3001):
```
Message received: { type: 'SPACES_CREATED', ... }
Notification saved with ID: 1
```

### Opción 3: Consultar RabbitMQ Management UI
1. Abrir: `http://localhost:15672`
2. Login: `admin` / `admin`
3. Ir a **Queues** → `notification.queue`
4. Verificar que los mensajes están siendo consumidos

### Opción 4: Consultar la Base de Datos de Notificaciones
```powershell
# Conectar a PostgreSQL
docker exec -it postgres_notifications psql -U notificationuser -d notificationdb

# Ver notificaciones
SELECT * FROM notification ORDER BY "createdAt" DESC LIMIT 5;
```

Deberías ver registros como:
```
 id |      type      |  title   |           message            
----+----------------+----------+------------------------------
  1 | SPACES_CREATED | Espacio  | Espacio A-101 creado...
```

### Opción 5: API GET de Notificaciones
**Endpoint:** `GET http://localhost:3001/notifications`

**Respuesta esperada:**
```json
[
  {
    "id": 1,
    "type": "SPACES_CREATED",
    "title": "Espacio Creado",
    "message": "Espacio A-101 creado exitosamente en zona Zona Norte",
    "data": {
      "spaceId": 1,
      "spaceName": "Espacio A-101",
      "zoneId": 1,
      "zoneName": "Zona Norte"
    },
    "read": false,
    "createdAt": "2026-01-12T16:30:00.000Z"
  }
]
```

## 🔍 Debugging

Si no ves las notificaciones:

1. **Verificar conexión a RabbitMQ:**
```powershell
docker logs zone-core | Select-String "RabbitMQ"
```

2. **Verificar que el exchange existe:**
   - UI: http://localhost:15672 → **Exchanges** → buscar `notifications_exchange`

3. **Verificar que la cola existe:**
   - UI: http://localhost:15672 → **Queues** → buscar `notification.queue`

4. **Verificar binding:**
   - La cola `notification.queue` debe estar vinculada al exchange `notifications_exchange` con routing key `notification.routingkey`

## 📊 Flujo Completo

```
Usuario → Postman → Zone Core (8080)
                         ↓
                    Guarda Space en DB
                         ↓
                    Envía mensaje a RabbitMQ
                         ↓
              RabbitMQ (notifications_exchange)
                         ↓
                   (notification.queue)
                         ↓
              Notification Service (3001)
                         ↓
              Guarda en PostgreSQL (5433)
                         ↓
                   Notificación disponible vía API
```

## 🎯 Casos de Prueba Adicionales

### Crear Múltiples Espacios
Crea varios espacios con diferentes códigos:
- A-101, A-102, A-103 (primer piso)
- B-201, B-202, B-203 (segundo piso)

Cada uno generará una notificación separada.

### Verificar Notificaciones No Leídas
```
GET http://localhost:3001/notifications?read=false
```

### Marcar Notificación como Leída
```
PATCH http://localhost:3001/notifications/1/read
```
