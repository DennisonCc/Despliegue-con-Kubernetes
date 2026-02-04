# Guía de Uso - Sistema de Tickets de Estacionamiento

Esta guía te ayudará a iniciar el sistema completo y crear datos de prueba para verificar el funcionamiento de ms-tickets (GraphQL).

## 📋 Requisitos Previos

- Docker y Docker Compose instalados
- PowerShell (Windows) o Bash (Linux/Mac)
- Navegador web para acceder a GraphQL Playground

## 🚀 Inicio Rápido

> [!IMPORTANT]
> **Después de las correcciones implementadas, es NECESARIO reconstruir las imágenes Docker** para que los cambios en el código se reflejen en los contenedores.

### 1. Reconstruir e Iniciar los Servicios

```bash
# Desde el directorio raíz del proyecto
# Detener contenedores existentes (si los hay)
docker-compose down

# Reconstruir las imágenes con los cambios más recientes
docker-compose build

# Iniciar los servicios
docker-compose up -d
```

**Alternativa (todo en un comando):**
```bash
docker-compose up -d --build
```

Esto iniciará los siguientes servicios:
- **mc-clientes** (Puerto 8081): Microservicio de personas y vehículos
- **zone_core** (Puerto 8080): Microservicio de zonas y espacios
- **ms-tickets** (Puerto 4000): Microservicio GraphQL de tickets
- **notification-service** (Puerto 3000): Microservicio de notificaciones
- **PostgreSQL** (Puerto 5432): Base de datos para mc-clientes y zone_core
- **MongoDB** (Puerto 27017): Base de datos para ms-tickets
- **RabbitMQ** (Puerto 5672, 15672): Sistema de mensajería

### 2. Verificar que los Servicios Están Corriendo

```bash
docker-compose ps
```

Todos los servicios deben estar en estado "Up".

### 3. Crear Datos de Prueba

#### En Windows (PowerShell):
```powershell
.\create-test-data.ps1
```

#### En Linux/Mac (Bash):
```bash
chmod +x create-test-data.sh
./create-test-data.sh
```

Este script creará:
- ✅ 5 Personas (3 naturales, 2 jurídicas)
- ✅ 5 Vehículos (3 automóviles, 2 motos)
- ✅ 2 Zonas de estacionamiento (VIP y General)
- ✅ 10 Espacios de estacionamiento (5 por zona)

### 4. Verificar los Datos Creados

#### mc-clientes:
```bash
# Ver todas las personas
curl http://localhost:8081/api/personas/

# Ver todos los vehículos
curl http://localhost:8081/api/vehiculos/
```

#### zone_core:
```bash
# Ver todas las zonas
curl http://localhost:8080/api/zones/

# Ver todos los espacios
curl http://localhost:8080/api/spaces/

# Ver espacios disponibles
curl http://localhost:8080/api/spaces/availables
```

### 5. Probar ms-tickets con GraphQL

Abre tu navegador y ve a:
```
http://localhost:4000/graphql
```

Esto abrirá el GraphQL Playground donde podrás ejecutar queries y mutations.

## 📝 Datos de Prueba Creados

### Personas y Vehículos

| Identificación | Nombre | Vehículo | Placa |
|----------------|--------|----------|-------|
| 1234567890 | Juan Pérez | Toyota Corolla | ABC-1234 |
| 0987654321 | María González | Chevrolet Spark | XYZ-5678 |
| 1122334455 | Carlos Ramírez | Mazda CX-5 | DEF-9012 |
| 1790123456001 | TechCorp S.A. | Yamaha FZ-16 | MTO-111 |
| 1790987654001 | Comercial XYZ Ltda. | Honda CBR 250 | MTO-222 |

### Zonas y Espacios

**Zona VIP:**
- VIP-A01, VIP-A02, VIP-A03, VIP-A04, VIP-A05

**Zona General:**
- GEN-B01, GEN-B02, GEN-B03, GEN-B04, GEN-B05

## 🧪 Ejemplos de Pruebas GraphQL

### Emitir un Ticket

```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1234567890"
    vehiculoPlaca: "ABC-1234"
  ) {
    id
    codigoTicket
    personaNombres
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    fechaEntrada
    estado
  }
}
```

### Ver Todos los Tickets

```graphql
query {
  getAllTickets {
    codigoTicket
    personaNombres
    vehiculoPlaca
    zonaNombre
    espacioCodigo
    estado
  }
}
```

### Ver Tickets Activos

```graphql
query {
  getActiveTickets {
    codigoTicket
    personaNombres
    vehiculoPlaca
    espacioCodigo
    fechaEntrada
  }
}
```

### Cerrar un Ticket

```graphql
mutation {
  cerrarTicket(ticketId: "TICKET-ID-AQUI") {
    codigoTicket
    fechaEntrada
    fechaSalida
    tiempoEstacionado
    estado
  }
}
```

## 📚 Documentación Adicional

Para más ejemplos y casos de prueba detallados, consulta:
- **[GRAPHQL_TESTING_GUIDE.md](./GRAPHQL_TESTING_GUIDE.md)**: Guía completa de pruebas GraphQL
- **[walkthrough.md](./walkthrough.md)**: Reporte de verificación y correcciones implementadas

## 🔧 Comandos Útiles

### Ver logs de un servicio específico
```bash
docker-compose logs -f ms-tickets
docker-compose logs -f mc-clientes
docker-compose logs -f zone_core
```

### Reiniciar un servicio
```bash
docker-compose restart ms-tickets
```

### Detener todos los servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes (limpieza completa)
```bash
docker-compose down -v
```

## 🐛 Troubleshooting

### Los servicios no inician
```bash
# Ver logs de todos los servicios
docker-compose logs

# Reconstruir las imágenes
docker-compose up --build
```

### Error al crear datos de prueba
```bash
# Verificar que los servicios estén corriendo
docker-compose ps

# Esperar unos segundos más para que los servicios terminen de iniciar
sleep 10

# Volver a ejecutar el script
.\create-test-data.ps1
```

### GraphQL Playground no carga
```bash
# Verificar logs de ms-tickets
docker-compose logs ms-tickets

# Reiniciar el servicio
docker-compose restart ms-tickets
```

### No hay espacios disponibles
```bash
# Ver espacios ocupados
curl http://localhost:8080/api/spaces/

# Cerrar tickets activos desde GraphQL para liberar espacios
```

## 📊 Flujo de Prueba Recomendado

1. ✅ Iniciar servicios con `docker-compose up -d`
2. ✅ Esperar 30 segundos para que todos los servicios inicien
3. ✅ Ejecutar script de datos de prueba
4. ✅ Verificar datos en mc-clientes y zone_core
5. ✅ Abrir GraphQL Playground
6. ✅ Emitir 3-4 tickets con diferentes personas/vehículos
7. ✅ Consultar tickets activos
8. ✅ Cerrar 1-2 tickets
9. ✅ Verificar que los espacios se liberaron
10. ✅ Probar consultas por persona y por vehículo

## 🎯 Endpoints Importantes

| Servicio | Endpoint | Descripción |
|----------|----------|-------------|
| mc-clientes | http://localhost:8081/api/personas/ | CRUD de personas |
| mc-clientes | http://localhost:8081/api/vehiculos/ | CRUD de vehículos |
| zone_core | http://localhost:8080/api/zones/ | CRUD de zonas |
| zone_core | http://localhost:8080/api/spaces/ | CRUD de espacios |
| ms-tickets | http://localhost:4000/graphql | GraphQL API |
| RabbitMQ | http://localhost:15672 | Panel de administración (guest/guest) |

## ✅ Verificación de Correcciones Implementadas

Este proyecto incluye las siguientes correcciones de integración:

1. ✅ Endpoint `/api/vehiculos/propietario/{personaId}` en mc-clientes
2. ✅ Endpoint `PATCH /api/spaces/{id}/status` en zone_core
3. ✅ Campo `isReserved` incluido en respuestas de espacios
4. ✅ URLs corregidas en ms-tickets (sin duplicación de `/api`)
5. ✅ Mapeo de campos sincronizado entre TypeScript y Java

Todos los microservicios compilan exitosamente y están listos para uso.

## 📞 Soporte

Si encuentras algún problema, revisa:
1. Los logs de los servicios con `docker-compose logs`
2. La guía de troubleshooting en este README
3. El archivo GRAPHQL_TESTING_GUIDE.md para ejemplos detallados
