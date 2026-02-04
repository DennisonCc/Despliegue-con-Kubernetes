# 🚀 GUÍA COMPLETA PARA PROBAR MS-TICKETS (GraphQL)

## 📋 Índice
1. [Prerequisitos](#prerequisitos)
2. [Iniciar los Microservicios](#iniciar-microservicios)
3. [Poblar Datos de Prueba](#poblar-datos)
4. [Acceder a GraphQL Playground](#graphql-playground)
5. [Ejemplos de Queries](#ejemplos-queries)
6. [Ejemplos de Mutations](#ejemplos-mutations)
7. [Casos de Prueba](#casos-prueba)
8. [Solución de Problemas](#troubleshooting)

---

## 📌 Prerequisitos

Antes de empezar, asegúrate de tener:

- ✅ Docker y Docker Compose instalados
- ✅ PostgreSQL corriendo (puertos 5432, 5434, 5435)
- ✅ Node.js instalado (para ms-tickets)
- ✅ Java 17+ y Maven (para mc-clientes y zone_core)
- ✅ Python 3.8+ (para el script de datos)

---

## 🏃 Iniciar los Microservicios

### 1. Iniciar las bases de datos (Docker)

```powershell
# Desde la raíz del proyecto
docker-compose up -d postgres
```

Verifica que las bases de datos estén corriendo:
```powershell
docker ps
```

### 2. Iniciar mc-clientes (Puerto 8081)

```powershell
cd mc-clientes
mvn clean install
mvn spring-boot:run
```

**Verifica que esté funcionando:**
```powershell
curl http://localhost:8081/api/personas/
```

### 3. Iniciar zone_core (Puerto 8080)

```powershell
cd zone_core
mvn clean install
mvn spring-boot:run
```

**Verifica que esté funcionando:**
```powershell
curl http://localhost:8080/api/zones/
```

### 4. Iniciar ms-tickets (Puerto 4000)

```powershell
cd ms-tickets
npm install
npm run dev
```

**Verifica que esté funcionando:**
```powershell
curl http://localhost:4000
```

---

## 📊 Poblar Datos de Prueba

Con todos los microservicios corriendo, ejecuta el script de Python:

```powershell
# Desde la raíz del proyecto
python populate_test_data.py
```

Este script creará:
- ✅ **5 Personas Naturales** con cédulas válidas ecuatorianas
- ✅ **5 Vehículos** (uno por cada persona)
- ✅ **5 Zonas de Estacionamiento**
- ✅ **Espacios de Estacionamiento** en cada zona

**Ejemplo de salida esperada:**
```
✓ mc-clientes está disponible
✓ zone_core está disponible
✓ Persona creada: Juan Carlos Pérez García (ID: 1720345678)
✓ Vehículo creado: Chevrolet Aveo - Placa: ABC-1234
✓ Zona creada: Zona Norte (Capacidad: 50)
✓ Espacio creado: ZON-001 en Zona Norte
```

---

## 🎮 Acceder a GraphQL Playground

### Opción 1: Apollo Sandbox (Recomendado)

1. Abre tu navegador
2. Ve a: **http://localhost:4000**
3. Se abrirá Apollo Studio Explorer

### Opción 2: GraphQL Playground

Si prefieres GraphQL Playground:
```powershell
npm install -g graphql-playground
graphql-playground http://localhost:4000
```

---

## 🔍 Ejemplos de Queries (Consultas)

### 1. Obtener Todos los Tickets

```graphql
query {
  tickets {
    id
    codigoTicket
    personaIdentificacion
    personaNombre
    vehiculoPlaca
    vehiculoMarca
    vehiculoModelo
    zonaNombre
    espacioCodigo
    fechaIngreso
    fechaSalida
    estado
    tiempoEstacionadoMinutos
    createdAt
  }
}
```

### 2. Obtener Tickets Activos

```graphql
query {
  ticketsActivos {
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    fechaIngreso
    estado
  }
}
```

### 3. Obtener Ticket por Código

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
  }
}
```

### 4. Obtener Tickets por Persona

```graphql
query {
  ticketsByPersona(identificacion: "1720345678") {
    codigoTicket
    vehiculoPlaca
    zonaNombre
    fechaIngreso
    fechaSalida
    estado
  }
}
```

### 5. Obtener Tickets por Vehículo

```graphql
query {
  ticketsByVehiculo(placa: "ABC-1234") {
    codigoTicket
    personaNombre
    zonaNombre
    espacioCodigo
    fechaIngreso
    estado
  }
}
```

### 6. Obtener Estadísticas de Tickets

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

---

## ✏️ Ejemplos de Mutations (Operaciones)

### 1. Emitir un Nuevo Ticket

**Sin especificar zona (asignación automática):**

```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1720345678"
    vehiculoPlaca: "ABC-1234"
  ) {
    id
    codigoTicket
    personaNombre
    vehiculoPlaca
    vehiculoMarca
    vehiculoModelo
    zonaNombre
    espacioCodigo
    fechaIngreso
    estado
  }
}
```

**Con zona específica:**

```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1715234567"
    vehiculoPlaca: "DEF-5678"
    zonaId: "uuid-de-la-zona"
  ) {
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
  }
}
```

### 2. Cerrar un Ticket (Registrar Salida)

```graphql
mutation {
  cerrarTicket(ticketId: "uuid-del-ticket") {
    id
    codigoTicket
    fechaIngreso
    fechaSalida
    tiempoEstacionadoMinutos
    estado
  }
}
```

### 3. Anular un Ticket

```graphql
mutation {
  anularTicket(ticketId: "uuid-del-ticket") {
    codigoTicket
    estado
    updatedAt
  }
}
```

---

## 🧪 Casos de Prueba Completos

### Caso 1: Flujo Completo de Estacionamiento

```graphql
# Paso 1: Emitir ticket
mutation EmitirTicket {
  emitirTicket(
    personaIdentificacion: "1720345678"
    vehiculoPlaca: "ABC-1234"
  ) {
    id
    codigoTicket
    zonaNombre
    espacioCodigo
    estado
  }
}

# Paso 2: Verificar tickets activos
query VerificarActivos {
  ticketsActivos {
    codigoTicket
    personaNombre
    vehiculoPlaca
    zonaNombre
  }
}

# Paso 3: Cerrar ticket (usar el ID del paso 1)
mutation CerrarTicket {
  cerrarTicket(ticketId: "id-obtenido-en-paso-1") {
    codigoTicket
    tiempoEstacionadoMinutos
    estado
    fechaSalida
  }
}

# Paso 4: Verificar estadísticas
query Estadisticas {
  estadisticasTickets {
    total
    activos
    cerrados
    promedioTiempoMinutos
  }
}
```

### Caso 2: Gestión por Persona

```graphql
# Emitir múltiples tickets para la misma persona
mutation {
  ticket1: emitirTicket(
    personaIdentificacion: "1715234567"
    vehiculoPlaca: "XYZ-9876"
  ) {
    codigoTicket
  }
}

# Consultar todos los tickets de esa persona
query {
  ticketsByPersona(identificacion: "1715234567") {
    codigoTicket
    vehiculoPlaca
    zonaNombre
    estado
  }
}
```

### Caso 3: Gestión por Vehículo

```graphql
# Consultar historial de un vehículo específico
query HistorialVehiculo {
  ticketsByVehiculo(placa: "ABC-1234") {
    codigoTicket
    personaNombre
    zonaNombre
    fechaIngreso
    fechaSalida
    tiempoEstacionadoMinutos
    estado
  }
}
```

---

## 🔧 Solución de Problemas

### Error: "No hay espacios disponibles"

**Solución:**
```graphql
# Verifica que hay zonas y espacios creados
# Consulta directa a zone_core
```
```powershell
curl http://localhost:8080/api/zones/
curl http://localhost:8080/api/spaces/availables
```

### Error: "La persona no existe"

**Solución:**
Verifica que los datos se crearon correctamente:
```powershell
curl http://localhost:8081/api/personas/
curl http://localhost:8081/api/personas/identificacion/1720345678
```

### Error: "El vehículo no pertenece a la persona"

**Solución:**
Verifica la relación vehículo-persona:
```powershell
curl http://localhost:8081/api/vehiculos/placa/ABC-1234
```

### Error de Conexión a GraphQL

**Solución:**
1. Verifica que ms-tickets esté corriendo:
   ```powershell
   cd ms-tickets
   npm run dev
   ```

2. Verifica las variables de entorno en ms-tickets:
   - `ZONE_SERVICE_URL=http://localhost:8080/api`
   - `PERSONA_SERVICE_URL=http://localhost:8081/api`

3. Revisa los logs de ms-tickets para errores específicos

### Base de datos no conecta

**Solución:**
```powershell
# Verifica que PostgreSQL esté corriendo
docker ps | grep postgres

# Revisa los logs de la base de datos
docker logs db_parkin_tickets
```

---

## 📝 Variables de Entorno para ms-tickets

Crea un archivo `.env` en el directorio `ms-tickets`:

```env
# Puerto del servidor GraphQL
PORT=4000

# URLs de microservicios
ZONE_SERVICE_URL=http://localhost:8080/api
PERSONA_SERVICE_URL=http://localhost:8081/api

# Configuración de base de datos
DB_HOST=localhost
DB_PORT=5435
DB_NAME=db_parkin_tickets
DB_USER=parkin
DB_PASSWORD=qwerty123

# Entorno
NODE_ENV=development
```

---

## 🎯 Endpoints de los Microservicios

| Microservicio | Puerto | Endpoint Base | Swagger/Docs |
|--------------|--------|---------------|--------------|
| mc-clientes  | 8081   | http://localhost:8081/api | N/A |
| zone_core    | 8080   | http://localhost:8080/api | N/A |
| ms-tickets   | 4000   | http://localhost:4000 | GraphQL Playground |

---

## 🎨 Tips Adicionales

### Usar Variables en GraphQL

```graphql
# Definir la mutación con variables
mutation EmitirTicket($cedula: String!, $placa: String!) {
  emitirTicket(
    personaIdentificacion: $cedula
    vehiculoPlaca: $placa
  ) {
    codigoTicket
    estado
  }
}

# Variables (panel de variables en GraphQL Playground)
{
  "cedula": "1720345678",
  "placa": "ABC-1234"
}
```

### Introspección del Schema

Para ver todos los tipos disponibles:
```graphql
{
  __schema {
    types {
      name
      description
    }
  }
}
```

### Fragmentos para Reutilización

```graphql
fragment TicketBasico on Ticket {
  id
  codigoTicket
  personaNombre
  vehiculoPlaca
  estado
}

query {
  ticketsActivos {
    ...TicketBasico
    zonaNombre
  }
  
  ticket(codigoTicket: "TICK-123") {
    ...TicketBasico
    fechaIngreso
    fechaSalida
  }
}
```

---

## 📚 Documentación Adicional

- **GraphQL**: https://graphql.org/learn/
- **Apollo Server**: https://www.apollographql.com/docs/apollo-server/
- **TypeORM**: https://typeorm.io/

---

## ✅ Checklist de Verificación

Antes de empezar a probar:

- [ ] PostgreSQL corriendo (3 bases de datos)
- [ ] mc-clientes corriendo en puerto 8081
- [ ] zone_core corriendo en puerto 8080
- [ ] ms-tickets corriendo en puerto 4000
- [ ] Datos de prueba poblados (script ejecutado)
- [ ] GraphQL Playground accesible

---

**¡Listo para probar!** 🚀

Si tienes problemas, revisa los logs de cada microservicio:
- mc-clientes: Logs en consola de Spring Boot
- zone_core: Logs en consola de Spring Boot
- ms-tickets: Logs en consola de Node.js
