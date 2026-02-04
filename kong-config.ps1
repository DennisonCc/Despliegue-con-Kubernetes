# Script PowerShell para configurar Kong API Gateway con los dos microservicios

Write-Host "Esperando a que Kong esté listo..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

# Configurar servicio mc-clientes
Write-Host "Configurando servicio mc-clientes..." -ForegroundColor Cyan
$body = @{
    name = "mc-clientes"
    url = "http://mc-clientes:8081"
}
Invoke-RestMethod -Uri "http://localhost:9001/services" -Method POST -Body $body

# Configurar ruta para mc-clientes
Write-Host "Configurando ruta para mc-clientes..." -ForegroundColor Cyan
$routeBody = @{
    'paths[]' = '/clientes'
    name = 'clientes-route'
}
Invoke-RestMethod -Uri "http://localhost:9001/services/mc-clientes/routes" -Method POST -Body $routeBody

# Configurar servicio zone-core
Write-Host "Configurando servicio zone-core..." -ForegroundColor Cyan
$body2 = @{
    name = "zone-core"
    url = "http://zone-core:8080"
}
Invoke-RestMethod -Uri "http://localhost:9001/services" -Method POST -Body $body2

# Configurar ruta para zone-core
Write-Host "Configurando ruta para zone-core..." -ForegroundColor Cyan
$routeBody2 = @{
    'paths[]' = '/zones'
    name = 'zones-route'
}
Invoke-RestMethod -Uri "http://localhost:9001/services/zone-core/routes" -Method POST -Body $routeBody2

# Habilitar plugin de CORS (opcional)
Write-Host "Habilitando CORS..." -ForegroundColor Cyan
$corsBody = @{
    name = "cors"
    'config.origins' = "*"
    'config.methods' = "GET,POST,PUT,DELETE,PATCH,OPTIONS"
    'config.headers' = "Accept,Content-Type,Authorization"
    'config.exposed_headers' = "X-Auth-Token"
    'config.credentials' = "true"
    'config.max_age' = "3600"
}
Invoke-RestMethod -Uri "http://localhost:9001/plugins" -Method POST -Body $corsBody

# Habilitar plugin de rate limiting (opcional)
Write-Host "Configurando rate limiting..." -ForegroundColor Cyan
$rateLimitBody = @{
    name = "rate-limiting"
    'config.minute' = "100"
    'config.hour' = "1000"
}
Invoke-RestMethod -Uri "http://localhost:9001/plugins" -Method POST -Body $rateLimitBody

Write-Host "`n✓ Configuración de Kong completada!" -ForegroundColor Green
Write-Host "`nEndpoints disponibles:" -ForegroundColor Yellow
Write-Host "- mc-clientes: http://localhost:9000/clientes" -ForegroundColor White
Write-Host "- zone-core: http://localhost:9000/zones" -ForegroundColor White
Write-Host "- Kong Admin API: http://localhost:9001" -ForegroundColor White
