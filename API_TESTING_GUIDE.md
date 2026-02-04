# Guía Completa de Pruebas de API - Sistema de Estacionamiento

## 📋 Índice
- [mc-clientes (Puerto 8081)](#mc-clientes)
  - [Personas](#personas)
  - [Vehículos](#vehículos)
- [zone_core (Puerto 8080)](#zone_core)
  - [Zonas](#zonas)
  - [Espacios](#espacios)
- [ms-tickets (Puerto 4000)](#ms-tickets)
  - [GraphQL](#graphql)

---

# mc-clientes

**Base URL:** `http://localhost:8081/api`

## Personas

### 1. Listar Todas las Personas
```bash
curl http://localhost:8081/api/personas/
```

### 2. Listar Solo Personas Naturales
```bash
curl http://localhost:8081/api/personas/naturales
```

### 3. Crear Persona Natural
```bash
curl -X POST http://localhost:8081/api/personas/natural \
  -H "Content-Type: application/json" \
  -d '{
    "identificacion": "1234567890",
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "telefono": "0991234567",
    "direccion": "Av. Amazonas 123",
    "genero": "MASCULINO",
    "fechaNacimiento": "1990-05-15"
  }'
```

### 4. Crear Persona Jurídica
```bash
curl -X POST http://localhost:8081/api/personas/juridica \
  -H "Content-Type: application/json" \
  -d '{
    "identificacion": "1790123456001",
    "nombre": "TechCorp S.A.",
    "email": "contacto@techcorp.com",
    "telefono": "022345678",
    "direccion": "Av. República 100",
    "razonSocial": "TechCorp Sociedad Anónima",
    "representanteLegal": "Roberto Sánchez",
    "actividadEconomica": "Desarrollo de Software"
  }'
```

### 5. Actualizar Persona Natural
```bash
curl -X PUT http://localhost:8081/api/personas/natural/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "identificacion": "1234567890",
    "nombre": "Juan Carlos",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "telefono": "0991234567",
    "direccion": "Av. Amazonas 456",
    "genero": "MASCULINO",
    "fechaNacimiento": "1990-05-15"
  }'
```

### 6. Actualizar Persona Jurídica
```bash
curl -X PUT http://localhost:8081/api/personas/juridica/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "identificacion": "1790123456001",
    "nombre": "TechCorp S.A.",
    "email": "info@techcorp.com",
    "telefono": "022345678",
    "direccion": "Av. República 200",
    "razonSocial": "TechCorp Sociedad Anónima",
    "representanteLegal": "Roberto Sánchez",
    "actividadEconomica": "Desarrollo de Software"
  }'
```

### 7. Obtener Persona por Identificación
```bash
curl http://localhost:8081/api/personas/identificacion/1234567890
```

### 8. Eliminar Persona
```bash
curl -X DELETE http://localhost:8081/api/personas/{UUID}
```

---

## Vehículos

### 1. Listar Todos los Vehículos
```bash
curl http://localhost:8081/api/vehiculos/
```

### 2. Crear Moto
```bash
curl -X POST http://localhost:8081/api/vehiculos/moto \
  -H "Content-Type: application/json" \
  -d '{
    "placa": "MTO-111",
    "marca": "Yamaha",
    "modelo": "FZ-16",
    "color": "Azul",
    "anioFabricacion": 2020,
    "propietarioId": "{UUID-PROPIETARIO}",
    "cilindraje": 150,
    "tipo": "DEPORTIVA",
    "tieneCasco": true
  }'
```

### 3. Crear Automóvil
```bash
curl -X POST http://localhost:8081/api/vehiculos/automovil \
  -H "Content-Type: application/json" \
  -d '{
    "placa": "ABC-1234",
    "marca": "Toyota",
    "modelo": "Corolla",
    "color": "Blanco",
    "anioFabricacion": 2020,
    "propietarioId": "{UUID-PROPIETARIO}",
    "tipo": "SEDAN",
    "combustible": "GASOLINA",
    "numeroPuertas": 4,
    "numeroPasajeros": 5,
    "cilindrada": 1800,
    "transmision": "Automática",
    "aireAcondicionado": true,
    "abs": true,
    "airbags": true
  }'
```

### 4. Actualizar Moto
```bash
curl -X PUT http://localhost:8081/api/vehiculos/moto/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "placa": "MTO-111",
    "marca": "Yamaha",
    "modelo": "FZ-16",
    "color": "Rojo",
    "anioFabricacion": 2020,
    "propietarioId": "{UUID-PROPIETARIO}",
    "cilindraje": 150,
    "tipo": "DEPORTIVA",
    "tieneCasco": true
  }'
```

### 5. Actualizar Automóvil
```bash
curl -X PUT http://localhost:8081/api/vehiculos/automovil/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "placa": "ABC-1234",
    "marca": "Toyota",
    "modelo": "Corolla",
    "color": "Negro",
    "anioFabricacion": 2020,
    "propietarioId": "{UUID-PROPIETARIO}",
    "tipo": "SEDAN",
    "combustible": "GASOLINA",
    "numeroPuertas": 4,
    "numeroPasajeros": 5,
    "cilindrada": 1800,
    "transmision": "Automática",
    "aireAcondicionado": true,
    "abs": true,
    "airbags": true
  }'
```

### 6. Obtener Vehículo por Placa
```bash
curl http://localhost:8081/api/vehiculos/placa/ABC-1234
```

### 7. Obtener Vehículos por Propietario
```bash
curl http://localhost:8081/api/vehiculos/propietario/{UUID-PROPIETARIO}
```

### 8. Eliminar Vehículo
```bash
curl -X DELETE http://localhost:8081/api/vehiculos/{UUID}
```

---

# zone_core

**Base URL:** `http://localhost:8080/api`

## Zonas

### 1. Listar Todas las Zonas
```bash
curl http://localhost:8080/api/zones/
```

### 2. Crear Zona
```bash
curl -X POST http://localhost:8080/api/zones/ \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Zona VIP",
    "description": "Zona de estacionamiento VIP",
    "capacity": 10,
    "type": "VIP",
    "isActive": true
  }'
```

### 3. Actualizar Zona
```bash
curl -X PUT http://localhost:8080/api/zones/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Zona VIP Premium",
    "description": "Zona de estacionamiento VIP con servicios premium",
    "capacity": 15,
    "type": "VIP",
    "isActive": true
  }'
```

### 4. Obtener Zona por ID
```bash
curl http://localhost:8080/api/zones/{UUID}
```

### 5. Eliminar Zona
```bash
curl -X DELETE http://localhost:8080/api/zones/{UUID}
```

---

## Espacios

### 1. Listar Todos los Espacios
```bash
curl http://localhost:8080/api/spaces/
```

### 2. Crear Espacio
```bash
curl -X POST http://localhost:8080/api/spaces/ \
  -H "Content-Type: application/json" \
  -d '{
    "code": "VIP-A01",
    "name": "Espacio VIP A01",
    "status": "AVALIABLE",
    "priority": 1,
    "isReserved": false,
    "zoneId": "{UUID-ZONA}"
  }'
```

### 3. Actualizar Espacio
```bash
curl -X PUT http://localhost:8080/api/spaces/{UUID} \
  -H "Content-Type: application/json" \
  -d '{
    "code": "VIP-A01",
    "name": "Espacio VIP A01 Premium",
    "status": "AVALIABLE",
    "priority": 1,
    "isReserved": false,
    "zoneId": "{UUID-ZONA}"
  }'
```

### 4. Obtener Espacios Disponibles
```bash
curl http://localhost:8080/api/spaces/availables
```

### 5. Obtener Espacios Disponibles por Zona
```bash
curl http://localhost:8080/api/spaces/availablesbyzone/{UUID-ZONA}
```

### 6. Obtener Espacio por ID
```bash
curl http://localhost:8080/api/spaces/{UUID}
```

### 7. Actualizar Estado de Espacio
```bash
curl -X PATCH "http://localhost:8080/api/spaces/{UUID}/status?status=OCCUPIED"
```

**Estados válidos:** `AVALIABLE`, `OCCUPIED`, `MAINTENANCE`, `RESERVED`

### 8. Eliminar Espacio
```bash
curl -X DELETE http://localhost:8080/api/spaces/{UUID}
```

---

# ms-tickets

**Base URL:** `http://localhost:4000/graphql`

## GraphQL

Abre http://localhost:4000/graphql en tu navegador para acceder al GraphQL Playground.

### Queries

#### 1. Obtener Todos los Tickets
```graphql
query {
  tickets {
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
    fechaIngreso
  }
}
```

#### 2. Obtener Ticket por Código
```graphql
query {
  ticket(codigoTicket: "TICK-123456-ABC") {
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
    fechaIngreso
    fechaSalida
    tiempoEstacionadoMinutos
  }
}
```

#### 3. Obtener Tickets por Persona
```graphql
query {
  ticketsByPersona(identificacion: "1234567890") {
    codigoTicket
    vehiculoPlaca
    zonaNombre
    estado
    fechaIngreso
  }
}
```

#### 4. Obtener Tickets por Vehículo
```graphql
query {
  ticketsByVehiculo(placa: "ABC-1234") {
    codigoTicket
    personaNombre
    zonaNombre
    estado
    fechaIngreso
  }
}
```

#### 5. Obtener Tickets Activos
```graphql
query {
  ticketsActivos {
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    fechaIngreso
  }
}
```

#### 6. Obtener Estadísticas
```graphql
query {
  estadisticasTickets {
    total
    activos
    cerrados
    anulados
    promedioTiempoMinutos
  }
}
```

### Mutations

#### 1. Emitir Ticket
```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1234567890"
    vehiculoPlaca: "ABC-1234"
  ) {
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
    fechaIngreso
  }
}
```

#### 2. Emitir Ticket con Zona Específica
```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1234567890"
    vehiculoPlaca: "ABC-1234"
    zonaId: "{UUID-ZONA}"
  ) {
    id
    codigoTicket
    zonaNombre
    espacioCodigo
  }
}
```

#### 3. Cerrar Ticket
```graphql
mutation {
  cerrarTicket(ticketId: "{UUID-TICKET}") {
    id
    codigoTicket
    fechaIngreso
    fechaSalida
    tiempoEstacionadoMinutos
    estado
  }
}
```

#### 4. Anular Ticket
```graphql
mutation {
  anularTicket(ticketId: "{UUID-TICKET}") {
    id
    codigoTicket
    estado
    fechaSalida
  }
}
```

---

## 🧪 Flujo de Prueba Completo

### 1. Crear Datos Base
```bash
# Crear persona
PERSONA_RESPONSE=$(curl -X POST http://localhost:8081/api/personas/natural \
  -H "Content-Type: application/json" \
  -d '{"identificacion":"1234567890","nombre":"Juan","apellido":"Pérez","email":"juan@test.com","telefono":"0991234567","direccion":"Av. Test 123","genero":"MASCULINO","fechaNacimiento":"1990-01-01"}')

PERSONA_ID=$(echo $PERSONA_RESPONSE | jq -r '.id')

# Crear vehículo
VEHICULO_RESPONSE=$(curl -X POST http://localhost:8081/api/vehiculos/automovil \
  -H "Content-Type: application/json" \
  -d "{\"placa\":\"ABC-1234\",\"marca\":\"Toyota\",\"modelo\":\"Corolla\",\"color\":\"Blanco\",\"anioFabricacion\":2020,\"propietarioId\":\"$PERSONA_ID\",\"tipo\":\"SEDAN\",\"combustible\":\"GASOLINA\",\"numeroPuertas\":4,\"numeroPasajeros\":5,\"cilindrada\":1800,\"transmision\":\"Automática\",\"aireAcondicionado\":true,\"abs\":true,\"airbags\":true}")

# Crear zona
ZONA_RESPONSE=$(curl -X POST http://localhost:8080/api/zones/ \
  -H "Content-Type: application/json" \
  -d '{"name":"Zona Test","description":"Zona de prueba","capacity":5,"type":"GENERAL","isActive":true}')

ZONA_ID=$(echo $ZONA_RESPONSE | jq -r '.id')

# Crear espacio
curl -X POST http://localhost:8080/api/spaces/ \
  -H "Content-Type: application/json" \
  -d "{\"code\":\"TEST-01\",\"name\":\"Espacio Test 01\",\"status\":\"AVALIABLE\",\"priority\":1,\"isReserved\":false,\"zoneId\":\"$ZONA_ID\"}"
```

### 2. Emitir Ticket (GraphQL)
```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1234567890"
    vehiculoPlaca: "ABC-1234"
  ) {
    id
    codigoTicket
    espacioCodigo
  }
}
```

### 3. Verificar Ticket Activo
```graphql
query {
  ticketsActivos {
    codigoTicket
    personaNombre
    vehiculoPlaca
    espacioCodigo
  }
}
```

### 4. Cerrar Ticket
```graphql
mutation {
  cerrarTicket(ticketId: "{UUID-DEL-TICKET}") {
    codigoTicket
    tiempoEstacionadoMinutos
    estado
  }
}
```

---

## 📊 Resumen de Endpoints

| Microservicio | Recurso | Endpoints |
|---------------|---------|-----------|
| mc-clientes | Personas | 8 |
| mc-clientes | Vehículos | 9 |
| zone_core | Zonas | 5 |
| zone_core | Espacios | 9 |
| ms-tickets | GraphQL | 10 queries/mutations |
| **TOTAL** | | **41 endpoints** |

---

## 🔍 Notas Importantes

1. **UUIDs:** Reemplaza `{UUID}`, `{UUID-PROPIETARIO}`, `{UUID-ZONA}`, etc. con los UUIDs reales obtenidos de las respuestas.

2. **Estados de Espacios:** Usa `AVALIABLE` (con error ortográfico) para compatibilidad con el sistema actual.

3. **GraphQL:** Para cerrar/anular tickets, necesitas el UUID del ticket, no el código. Usa la query `ticket(codigoTicket:...)` para obtener el UUID.

4. **Formato de Fechas:** Usa formato ISO 8601: `YYYY-MM-DD`

5. **Validaciones:** Todos los endpoints validan los datos de entrada. Revisa los mensajes de error para corregir datos inválidos.
