# Script para Liberar Espacios Ocupados

## 🔍 Problema Identificado

**Error:** "No hay espacios disponibles en la zona"

**Causa:** Todos los espacios están con status `OCCUPIED`. No hay espacios con status `AVALIABLE` disponibles.

---

## ✅ Solución 1: Liberar Todos los Espacios

### PowerShell Script
```powershell
# Liberar todos los espacios ocupados
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "UPDATE spaces SET status = 'AVALIABLE', is_reserved = false WHERE status = 'OCCUPIED';"
```

### Verificar que se liberaron
```powershell
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT status, COUNT(*) FROM spaces GROUP BY status;"
```

---

## ✅ Solución 2: Liberar Espacios de una Zona Específica

```powershell
# Reemplaza {ZONE_ID} con el UUID de tu zona
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "UPDATE spaces SET status = 'AVALIABLE', is_reserved = false WHERE zone_id = '{ZONE_ID}' AND status = 'OCCUPIED';"
```

---

## ✅ Solución 3: Crear Nuevos Espacios Disponibles

```bash
# Obtener ID de una zona
curl http://localhost:8080/api/zones/

# Crear espacio disponible
curl -X POST http://localhost:8080/api/spaces/ \
  -H "Content-Type: application/json" \
  -d '{
    "code": "NEW-001",
    "name": "Espacio Nuevo 001",
    "status": "AVALIABLE",
    "priority": 1,
    "isReserved": false,
    "zoneId": "{ZONE_ID}"
  }'
```

---

## 🔄 Flujo Completo de Prueba

### 1. Verificar Estado Actual
```powershell
# Ver todas las zonas
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT id, name FROM zones;"

# Ver espacios por zona
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT code, status, is_reserved, zone_id FROM spaces;"

# Contar espacios por estado
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT status, COUNT(*) FROM spaces GROUP BY status;"
```

### 2. Liberar Espacios
```powershell
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "UPDATE spaces SET status = 'AVALIABLE', is_reserved = false WHERE status = 'OCCUPIED';"
```

### 3. Verificar Espacios Disponibles
```bash
curl http://localhost:8080/api/spaces/availables
```

### 4. Emitir Ticket
```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "1701234567"
    vehiculoPlaca: "TVS-2481"
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

---

## 📊 Comandos Útiles de Diagnóstico

### Ver todos los espacios con detalles
```powershell
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT s.code, s.status, s.is_reserved, z.name as zone_name FROM spaces s JOIN zones z ON s.zone_id = z.id;"
```

### Ver espacios disponibles por zona
```powershell
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "SELECT z.name, COUNT(s.id) as available_spaces FROM zones z LEFT JOIN spaces s ON z.id = s.zone_id AND s.status = 'AVALIABLE' AND s.is_reserved = false GROUP BY z.name;"
```

### Liberar un espacio específico por código
```powershell
docker exec parkin_zone_db psql -U parkin -d db_parkin_zone -c "UPDATE spaces SET status = 'AVALIABLE', is_reserved = false WHERE code = 'ZON-001';"
```

---

## ⚠️ Nota Importante

El sistema marca automáticamente los espacios como `OCCUPIED` cuando se emite un ticket. Si no se cierran los tickets correctamente, los espacios permanecerán ocupados indefinidamente.

**Recomendación:** Cerrar los tickets de prueba después de emitirlos para liberar los espacios automáticamente.

```graphql
mutation {
  cerrarTicket(ticketId: "{UUID-TICKET}") {
    codigoTicket
    estado
  }
}
```
