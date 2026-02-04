# 📋 Guía Completa de APIs - Postman

## 🌐 Endpoints Base

**A través de Kong API Gateway:**
- mc-clientes: `http://localhost:9000/clientes/api`
- zone-core: `http://localhost:9000/zones/api`

**Acceso Directo:**
- mc-clientes: `http://localhost:8081/api`
- zone-core: `http://localhost:8080/api`

**Kong Admin API:**
- Admin: `http://localhost:9001`

---

## 🚗 MC-CLIENTES - Gestión de Clientes y Vehículos

### 👥 PERSONAS

#### **Listar todas las personas**
```
GET http://localhost:9000/clientes/api/personas/
GET http://localhost:8081/api/personas/
```

#### **Listar solo personas naturales**
```
GET http://localhost:9000/clientes/api/personas/naturales
GET http://localhost:8081/api/personas/naturales
```

#### **Crear Persona Natural**
```
POST http://localhost:9000/clientes/api/personas/natural
POST http://localhost:8081/api/personas/natural

Content-Type: application/json

{
  "identificacion": "1713175071",
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juan.perez@email.com",
  "telefono": "0987654321",
  "direccion": "Av. 6 de Diciembre N34-451, Quito",
  "genero": "M",
  "fechaNacimiento": "1990-05-15"
}
```

**Valores válidos para `genero`**: `M` (Masculino), `F` (Femenino), `O` (Otro)

#### **Crear Persona Jurídica**
```
POST http://localhost:9000/clientes/api/personas/juridica
POST http://localhost:8081/api/personas/juridica

Content-Type: application/json

{
  "identificacion": "1792146739001",
  "nombre": "TechSolutions",
  "email": "info@techsolutions.com",
  "telefono": "022345678",
  "direccion": "Av. República del Salvador N34-183, Quito",
  "razonSocial": "TechSolutions Cia. Ltda.",
  "representanteLegal": "María Elena Rodríguez",
  "actividadEconomica": "Desarrollo de Software y Consultoría IT"
}
```

#### **Actualizar Persona Natural**
```
PUT http://localhost:9000/clientes/api/personas/natural/{uuid}
PUT http://localhost:8081/api/personas/natural/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Actualizar Persona Jurídica**
```
PUT http://localhost:9000/clientes/api/personas/juridica/{uuid}
PUT http://localhost:8081/api/personas/juridica/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Eliminar Persona**
```
DELETE http://localhost:9000/clientes/api/personas/{uuid}
DELETE http://localhost:8081/api/personas/{uuid}
```

---

### 🚙 VEHÍCULOS

#### **Listar todos los vehículos**
```
GET http://localhost:9000/clientes/api/vehiculos/
GET http://localhost:8081/api/vehiculos/
```

#### **Crear Moto**
```
POST http://localhost:9000/clientes/api/vehiculos/moto
POST http://localhost:8081/api/vehiculos/moto

Content-Type: application/json

{
  "placa": "ABC-123",
  "marca": "Yamaha",
  "modelo": "MT-03",
  "color": "Negro",
  "anioFabricacion": 2023,
  "propietarioId": "uuid-del-propietario",
  "cilindraje": 321,
  "tipo": "DEPORTIVA",
  "tieneCasco": true
}
```

**Valores válidos para `tipo` de moto:**
- `DEPORTIVA`
- `SUPER_DEPORTIVA`
- `CRUISER`
- `SCOOTER`
- `CUSTOM`
- `TOURING`
- `CHOPPER`
- `ENDURO`
- `MOTOCROSS`
- `ELECTRICO`

#### **Crear Automóvil**
```
POST http://localhost:9000/clientes/api/vehiculos/automovil
POST http://localhost:8081/api/vehiculos/automovil

Content-Type: application/json

{
  "placa": "XYZ-456",
  "marca": "Toyota",
  "modelo": "Corolla",
  "color": "Gris",
  "anioFabricacion": 2022,
  "propietarioId": "uuid-del-propietario",
  "tipo": "SEDAN",
  "combustible": "GASOLINA",
  "numeroPuertas": 4,
  "numeroPasajeros": 5,
  "cilindrada": 1800,
  "transmision": "AUTOMATICA",
  "aireAcondicionado": true,
  "abs": true,
  "airbags": true
}
```

**Valores válidos para `tipo` de automóvil:**
- `SUV`
- `CROSSOVER`
- `SEDAN`
- `HATCHBACK`
- `COUPE`
- `CONVERTIBLE`
- `DEPORTIVO`

**Valores válidos para `combustible`:**
- `GASOLINA`
- `DIESEL`
- `ELECTRICO`
- `HIBRIDO`

#### **Actualizar Moto**
```
PUT http://localhost:9000/clientes/api/vehiculos/moto/{uuid}
PUT http://localhost:8081/api/vehiculos/moto/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Actualizar Automóvil**
```
PUT http://localhost:9000/clientes/api/vehiculos/automovil/{uuid}
PUT http://localhost:8081/api/vehiculos/automovil/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Eliminar Vehículo**
```
DELETE http://localhost:9000/clientes/api/vehiculos/{uuid}
DELETE http://localhost:8081/api/vehiculos/{uuid}
```

---

## 🅿️ ZONE-CORE - Gestión de Zonas y Espacios

### 📍 ZONES - Gestión de Zonas

#### **Listar todas las zonas**
```
GET http://localhost:9000/zones/api/zones/
GET http://localhost:8080/api/zones/
```

#### **Obtener zona por ID**
```
GET http://localhost:9000/zones/api/zones/{uuid}
GET http://localhost:8080/api/zones/{uuid}
```

#### **Crear nueva zona**
```
POST http://localhost:9000/zones/api/zones/
POST http://localhost:8080/api/zones/

Content-Type: application/json

{
  "name": "Zona VIP Norte",
  "description": "Zona premium para clientes VIP",
  "capacity": 50,
  "type": "VIP",
  "isActive": true
}
```

**Valores válidos para `type`:**
- `VIP`
- `INTERNAL`
- `EXTERNAL`

#### **Actualizar zona**
```
PUT http://localhost:9000/zones/api/zones/{uuid}
PUT http://localhost:8080/api/zones/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Eliminar zona**
```
DELETE http://localhost:9000/zones/api/zones/{uuid}
DELETE http://localhost:8080/api/zones/{uuid}
```

---

### 🔲 SPACES - Gestión de Espacios

#### **Listar todos los espacios**
```
GET http://localhost:9000/zones/api/spaces/
GET http://localhost:8080/api/spaces/
```

#### **Obtener espacio por ID**
```
GET http://localhost:9000/zones/api/spaces/{uuid}
GET http://localhost:8080/api/spaces/{uuid}
```

#### **Crear nuevo espacio**
```
POST http://localhost:9000/zones/api/spaces/
POST http://localhost:8080/api/spaces/

Content-Type: application/json

{
  "name": "Espacio A1",
  "code": "VIP-A1-001",
  "status": "AVALIABLE",
  "isReserved": false,
  "priority": 1,
  "zoneId": "uuid-de-la-zona"
}
```

**Valores válidos para `status`:**
- `AVALIABLE`
- `OCCUPIED`
- `MAINTENANCE`
- `RESERVADE`

#### **Actualizar espacio**
```
PUT http://localhost:9000/zones/api/spaces/{uuid}
PUT http://localhost:8080/api/spaces/{uuid}

Content-Type: application/json

Body: Mismo formato que POST
```

#### **Eliminar espacio**
```
DELETE http://localhost:9000/zones/api/spaces/{uuid}
DELETE http://localhost:8080/api/spaces/{uuid}
```

---

## 🔧 Kong Admin API - Gestión del Gateway

### **Listar servicios**
```
GET http://localhost:9001/services
```

### **Listar rutas**
```
GET http://localhost:9001/routes
```

### **Listar plugins**
```
GET http://localhost:9001/plugins
```

### **Ver estado de Kong**
```
GET http://localhost:9001/status
```

---

## 📝 Headers Recomendados

Para todas las peticiones, incluir:
```
Content-Type: application/json
Accept: application/json
```

---

## 🔄 Flujo de Trabajo Recomendado

1. **Crear una Persona Natural o Jurídica**
   - Guardar el `id` devuelto en la respuesta

2. **Crear un Vehículo (Moto o Automóvil)**
   - Usar el `id` de la persona como `propietarioId`

3. **Crear una Zona**
   - Guardar el `id` de la zona

4. **Crear Espacios en la Zona**
   - Usar el `id` de la zona como `zoneId`

---

## ✅ Validaciones Importantes

### Cédula Ecuatoriana (10 dígitos)
- Debe ser válida según algoritmo módulo 10
- Ejemplo: `1713175071`

### RUC Ecuatoriano (13 dígitos)
- Debe ser válido según algoritmo módulo 11
- Ejemplo: `1792146739001`

### Placas de Vehículos
- Formato: `ABC-123` o `ABC-1234`

---

## 🐛 Errores Comunes

1. **404 Not Found**: Verificar que los servicios estén corriendo
2. **400 Bad Request**: Revisar validaciones del JSON
3. **500 Internal Server Error**: Revisar logs del contenedor

### Ver logs:
```powershell
docker logs mc-clientes --tail 50
docker logs zone-core --tail 50
docker logs kong-gateway --tail 50
```
