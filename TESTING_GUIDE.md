# 🧪 Guía de Pruebas - Sistema de Notificaciones

## 📋 Prerequisitos
- ✅ zone-core corriendo en `http://localhost:8080`
- ✅ notification-service corriendo en `http://localhost:3001`
- ✅ RabbitMQ corriendo en `http://localhost:15672`

---

## 🔔 Operaciones Implementadas

### **ZONES** (3 operaciones con notificaciones)
1. ✅ CREATE Zone
2. ✅ UPDATE Zone
3. ✅ DELETE Zone

### **SPACES** (3 operaciones con notificaciones)
1. ✅ CREATE Space
2. ✅ UPDATE Space
3. ✅ DELETE Space

---

## 📝 Pruebas en Postman

### 1️⃣ **CREATE ZONE**
```http
POST http://localhost:8080/api/zones/
Content-Type: application/json

{
  "name": "Zona VIP Premium",
  "description": "Zona exclusiva para clientes VIP",
  "capacity": 50,
  "type": "VIP",
  "isActive": true
}
```

**Notificación esperada:**
- Action: `CREATE`
- Entity Type: `ZONE`
- Message: `Zone created: Zona VIP Premium`

---

### 2️⃣ **UPDATE ZONE**
```http
PUT http://localhost:8080/api/zones/{zone-id}
Content-Type: application/json

{
  "name": "Zona VIP Premium UPDATED",
  "description": "Zona exclusiva actualizada",
  "capacity": 60,
  "type": "VIP",
  "isActive": true
}
```

**Notificación esperada:**
- Action: `UPDATE`
- Entity Type: `ZONE`
- Message: `Zone updated: Zona VIP Premium UPDATED`

---

### 3️⃣ **DELETE ZONE**
```http
DELETE http://localhost:8080/api/zones/{zone-id}
```

**Notificación esperada:**
- Action: `DELETE`
- Entity Type: `ZONE`
- Message: `Zone deleted: Zona VIP Premium UPDATED`

---

### 4️⃣ **CREATE SPACE**
```http
POST http://localhost:8080/api/spaces/
Content-Type: application/json

{
  "name": "Espacio Premium A1",
  "code": "PREM-A1",
  "status": "AVALIABLE",
  "isReserved": false,
  "priority": 1,
  "zoneId": "tu-zone-id-aqui"
}
```

**Notificación esperada:**
- Action: `CREATE`
- Entity Type: `SPACE`
- Message: `Space created: PREM-A1`

---

### 5️⃣ **UPDATE SPACE**
```http
PUT http://localhost:8080/api/spaces/{space-id}
Content-Type: application/json

{
  "name": "Espacio Premium A1 UPDATED",
  "code": "PREM-A1-UPD",
  "status": "OCCUPIED",
  "isReserved": true,
  "priority": 2,
  "zoneId": "tu-zone-id-aqui"
}
```

**Notificación esperada:**
- Action: `UPDATE`
- Entity Type: `SPACE`
- Message: `Space updated: PREM-A1-UPD`

---

### 6️⃣ **DELETE SPACE**
```http
DELETE http://localhost:8080/api/spaces/{space-id}
```

**Notificación esperada:**
- Action: `DELETE`
- Entity Type: `SPACE`
- Message: `Space deleted: PREM-A1-UPD`

---

## 🔍 Verificación de Notificaciones

### Ver todas las notificaciones:
```http
GET http://localhost:3001/notifications
```

### Verificar cola en RabbitMQ:
1. Abrir `http://localhost:15672`
2. Login: `admin` / `admin`
3. Ir a **Queues** → `notification.queue`
4. Verificar mensajes procesados

---

## 📊 Estados y Enums Válidos

### **Zone Types:**
- `VIP`
- `INTERNAL`
- `EXTERNAL`

### **Space Status:**
- `AVALIABLE`
- `OCCUPIED`
- `MAINTENANCE`
- `RESERVADE`

---

## ✅ Checklist de Pruebas

### Zones:
- [ ] CREATE Zone → Notificación recibida
- [ ] UPDATE Zone → Notificación recibida
- [ ] DELETE Zone → Notificación recibida

### Spaces:
- [ ] CREATE Space → Notificación recibida
- [ ] UPDATE Space → Notificación recibida
- [ ] DELETE Space → Notificación recibida

### Verificaciones:
- [ ] Todas las notificaciones aparecen en `GET /notifications`
- [ ] Los datos en RabbitMQ son correctos
- [ ] No hay errores en los logs de zone-core
- [ ] No hay errores en los logs de notification-service

---

## 🐛 Troubleshooting

### Si no aparecen notificaciones:

1. **Verificar notification-service:**
   ```powershell
   Get-Process | Where-Object {$_.ProcessName -like "*node*"}
   ```

2. **Ver logs de notification-service:**
   - Revisar la terminal donde está corriendo `npm run start:dev`

3. **Verificar zone-core:**
   ```powershell
   docker logs zone-core --tail 50
   ```

4. **Verificar RabbitMQ:**
   - Conexión activa en `http://localhost:15672`
   - Cola `notification.queue` existe
   - Exchange `notifications_exchange` existe

---

## 📌 Notas Importantes

- **Content-Type:** Siempre usar `application/json` en los headers
- **UUIDs:** Guardar los IDs retornados para operaciones UPDATE/DELETE
- **Orden:** Crear Zone primero, luego Space (Space requiere zoneId)
- **Timing:** Esperar 1-2 segundos entre operaciones para ver logs claramente
