# Script de Setup Completo - Rebuild y Datos de Prueba
# Este script reconstruye los contenedores y crea datos de prueba

Write-Host "=== Setup Completo del Sistema de Tickets ===" -ForegroundColor Cyan
Write-Host ""

# ============================================
# Paso 1: Detener contenedores existentes
# ============================================
Write-Host "[1/5] Deteniendo contenedores existentes..." -ForegroundColor Yellow
docker-compose down
Write-Host "  ✓ Contenedores detenidos" -ForegroundColor Green
Start-Sleep -Seconds 2

# ============================================
# Paso 2: Reconstruir imágenes
# ============================================
Write-Host "`n[2/5] Reconstruyendo imágenes Docker..." -ForegroundColor Yellow
Write-Host "  (Esto puede tomar 3-5 minutos)" -ForegroundColor Gray
docker-compose build
if ($LASTEXITCODE -ne 0) {
    Write-Host "  ✗ Error al reconstruir imágenes" -ForegroundColor Red
    exit 1
}
Write-Host "  ✓ Imágenes reconstruidas exitosamente" -ForegroundColor Green
Start-Sleep -Seconds 2

# ============================================
# Paso 3: Iniciar servicios
# ============================================
Write-Host "`n[3/5] Iniciando servicios..." -ForegroundColor Yellow
docker-compose up -d
if ($LASTEXITCODE -ne 0) {
    Write-Host "  ✗ Error al iniciar servicios" -ForegroundColor Red
    exit 1
}
Write-Host "  ✓ Servicios iniciados" -ForegroundColor Green

# ============================================
# Paso 4: Esperar a que los servicios estén listos
# ============================================
Write-Host "`n[4/5] Esperando a que los servicios estén listos..." -ForegroundColor Yellow
Write-Host "  (Esperando 45 segundos para que los servicios inicialicen)" -ForegroundColor Gray

for ($i = 45; $i -gt 0; $i--) {
    Write-Host -NoNewline "`r  Tiempo restante: $i segundos "
    Start-Sleep -Seconds 1
}
Write-Host "`r  ✓ Servicios listos                    " -ForegroundColor Green

# Verificar estado de servicios
Write-Host "`n  Verificando estado de servicios..." -ForegroundColor Gray
docker-compose ps

# ============================================
# Paso 5: Crear datos de prueba
# ============================================
Write-Host "`n[5/5] Creando datos de prueba..." -ForegroundColor Yellow

$baseUrlClientes = "http://localhost:8081/api"
$baseUrlZones = "http://localhost:8080/api"

# Crear Personas
Write-Host "  Creando personas..." -ForegroundColor Gray
$personas = @(
    @{
        tipo = "natural"
        endpoint = "/personas/natural"
        data = @{
            identificacion = "1234567890"
            nombre = "Juan"
            apellido = "Pérez"
            email = "juan.perez@example.com"
            telefono = "0991234567"
            direccion = "Av. Amazonas 123"
            genero = "MASCULINO"
            fechaNacimiento = "1990-05-15"
        }
    },
    @{
        tipo = "natural"
        endpoint = "/personas/natural"
        data = @{
            identificacion = "0987654321"
            nombre = "María"
            apellido = "González"
            email = "maria.gonzalez@example.com"
            telefono = "0987654321"
            direccion = "Av. 6 de Diciembre 456"
            genero = "FEMENINO"
            fechaNacimiento = "1985-08-20"
        }
    },
    @{
        tipo = "natural"
        endpoint = "/personas/natural"
        data = @{
            identificacion = "1122334455"
            nombre = "Carlos"
            apellido = "Ramírez"
            email = "carlos.ramirez@example.com"
            telefono = "0998877665"
            direccion = "Av. Naciones Unidas 789"
            genero = "MASCULINO"
            fechaNacimiento = "1992-03-10"
        }
    },
    @{
        tipo = "juridica"
        endpoint = "/personas/juridica"
        data = @{
            identificacion = "1790123456001"
            nombre = "TechCorp S.A."
            email = "contacto@techcorp.com"
            telefono = "022345678"
            direccion = "Av. República 100"
            razonSocial = "TechCorp Sociedad Anónima"
            representanteLegal = "Roberto Sánchez"
            actividadEconomica = "Desarrollo de Software"
        }
    },
    @{
        tipo = "juridica"
        endpoint = "/personas/juridica"
        data = @{
            identificacion = "1790987654001"
            nombre = "Comercial XYZ Ltda."
            email = "info@xyz.com"
            telefono = "023456789"
            direccion = "Av. Patria 200"
            razonSocial = "Comercial XYZ Limitada"
            representanteLegal = "Ana Torres"
            actividadEconomica = "Comercio al por mayor"
        }
    }
)

$personaIds = @()
foreach ($persona in $personas) {
    try {
        $url = "$baseUrlClientes$($persona.endpoint)"
        $body = $persona.data | ConvertTo-Json -Depth 10
        $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
        $personaIds += $response.id
    } catch {
        Write-Host "    ⚠ Error creando persona: $_" -ForegroundColor Yellow
    }
}
Write-Host "    ✓ $($personaIds.Count) personas creadas" -ForegroundColor Green

# Crear Vehículos
Write-Host "  Creando vehículos..." -ForegroundColor Gray
$vehiculos = @(
    @{
        tipo = "automovil"
        endpoint = "/vehiculos/automovil"
        data = @{
            placa = "ABC-1234"
            marca = "Toyota"
            modelo = "Corolla"
            color = "Blanco"
            anioFabricacion = 2020
            propietarioId = $personaIds[0]
            tipo = "SEDAN"
            combustible = "GASOLINA"
            numeroPuertas = 4
            numeroPasajeros = 5
            cilindrada = 1800
            transmision = "Automática"
            aireAcondicionado = $true
            abs = $true
            airbags = $true
        }
    },
    @{
        tipo = "automovil"
        endpoint = "/vehiculos/automovil"
        data = @{
            placa = "XYZ-5678"
            marca = "Chevrolet"
            modelo = "Spark"
            color = "Rojo"
            anioFabricacion = 2019
            propietarioId = $personaIds[1]
            tipo = "HATCHBACK"
            combustible = "GASOLINA"
            numeroPuertas = 4
            numeroPasajeros = 5
            cilindrada = 1400
            transmision = "Manual"
            aireAcondicionado = $true
            abs = $true
            airbags = $true
        }
    },
    @{
        tipo = "automovil"
        endpoint = "/vehiculos/automovil"
        data = @{
            placa = "DEF-9012"
            marca = "Mazda"
            modelo = "CX-5"
            color = "Negro"
            anioFabricacion = 2021
            propietarioId = $personaIds[2]
            tipo = "SUV"
            combustible = "GASOLINA"
            numeroPuertas = 4
            numeroPasajeros = 5
            cilindrada = 2500
            transmision = "Automática"
            aireAcondicionado = $true
            abs = $true
            airbags = $true
        }
    },
    @{
        tipo = "moto"
        endpoint = "/vehiculos/moto"
        data = @{
            placa = "MTO-111"
            marca = "Yamaha"
            modelo = "FZ-16"
            color = "Azul"
            anioFabricacion = 2020
            propietarioId = $personaIds[3]
            cilindraje = 150
            tipo = "DEPORTIVA"
            tieneCasco = $true
        }
    },
    @{
        tipo = "moto"
        endpoint = "/vehiculos/moto"
        data = @{
            placa = "MTO-222"
            marca = "Honda"
            modelo = "CBR 250"
            color = "Rojo"
            anioFabricacion = 2021
            propietarioId = $personaIds[4]
            cilindraje = 250
            tipo = "DEPORTIVA"
            tieneCasco = $true
        }
    }
)

$vehiculosCreados = 0
foreach ($vehiculo in $vehiculos) {
    try {
        $url = "$baseUrlClientes$($vehiculo.endpoint)"
        $body = $vehiculo.data | ConvertTo-Json -Depth 10
        $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
        $vehiculosCreados++
    } catch {
        Write-Host "    ⚠ Error creando vehículo: $_" -ForegroundColor Yellow
    }
}
Write-Host "    ✓ $vehiculosCreados vehículos creados" -ForegroundColor Green

# Crear Zonas
Write-Host "  Creando zonas..." -ForegroundColor Gray
$zonas = @(
    @{
        name = "Zona VIP"
        description = "Zona de estacionamiento VIP con espacios preferenciales"
        capacity = 5
        type = "VIP"
        isActive = $true
    },
    @{
        name = "Zona General"
        description = "Zona de estacionamiento general"
        capacity = 5
        type = "GENERAL"
        isActive = $true
    }
)

$zonaIds = @()
foreach ($zona in $zonas) {
    try {
        $url = "$baseUrlZones/zones/"
        $body = $zona | ConvertTo-Json -Depth 10
        $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
        $zonaIds += $response.id
    } catch {
        Write-Host "    ⚠ Error creando zona: $_" -ForegroundColor Yellow
    }
}
Write-Host "    ✓ $($zonaIds.Count) zonas creadas" -ForegroundColor Green

# Crear Espacios
Write-Host "  Creando espacios..." -ForegroundColor Gray
$espaciosCreados = 0

# Espacios VIP
for ($i = 1; $i -le 5; $i++) {
    try {
        $code = "VIP-A{0:D2}" -f $i
        $url = "$baseUrlZones/spaces/"
        $body = @{
            code = $code
            name = "Espacio $code"
            status = "AVALIABLE"
            priority = $i
            isReserved = $false
            zoneId = $zonaIds[0]
        } | ConvertTo-Json -Depth 10
        
        $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
        $espaciosCreados++
    } catch {
        Write-Host "    ⚠ Error creando espacio: $_" -ForegroundColor Yellow
    }
}

# Espacios General
for ($i = 1; $i -le 5; $i++) {
    try {
        $code = "GEN-B{0:D2}" -f $i
        $priority = $i + 5
        $url = "$baseUrlZones/spaces/"
        $body = @{
            code = $code
            name = "Espacio $code"
            status = "AVALIABLE"
            priority = $priority
            isReserved = $false
            zoneId = $zonaIds[1]
        } | ConvertTo-Json -Depth 10
        
        $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
        $espaciosCreados++
    } catch {
        Write-Host "    ⚠ Error creando espacio: $_" -ForegroundColor Yellow
    }
}
Write-Host "    ✓ $espaciosCreados espacios creados" -ForegroundColor Green

# ============================================
# Resumen Final
# ============================================
Write-Host "`n=== Setup Completado ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "✓ Contenedores reconstruidos" -ForegroundColor Green
Write-Host "✓ Servicios iniciados" -ForegroundColor Green
Write-Host "✓ Datos de prueba creados:" -ForegroundColor Green
Write-Host "  - Personas: $($personaIds.Count)" -ForegroundColor White
Write-Host "  - Vehículos: $vehiculosCreados" -ForegroundColor White
Write-Host "  - Zonas: $($zonaIds.Count)" -ForegroundColor White
Write-Host "  - Espacios: $espaciosCreados" -ForegroundColor White
Write-Host ""
Write-Host "🚀 Sistema listo para usar!" -ForegroundColor Green
Write-Host ""
Write-Host "Próximos pasos:" -ForegroundColor Cyan
Write-Host "  1. Abrir GraphQL Playground: http://localhost:4000/graphql" -ForegroundColor White
Write-Host "  2. Consultar la guía: GRAPHQL_TESTING_GUIDE.md" -ForegroundColor White
Write-Host "  3. Emitir tickets de prueba" -ForegroundColor White
Write-Host ""
