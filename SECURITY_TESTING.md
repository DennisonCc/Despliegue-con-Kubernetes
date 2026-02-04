# Guía de Testing de Seguridad con OWASP ZAP

## 📋 Descripción

Este proyecto incluye configuración automatizada para realizar pruebas de seguridad DAST (Dynamic Application Security Testing) usando OWASP ZAP en toda la arquitectura de microservicios.

## 🎯 ¿Qué se prueba?

- **Kong API Gateway** (Puerto 9000)
- **mc-clientes** - Microservicio de usuarios (Puerto 8081)
- **zone-core** - Microservicio de zonas (Puerto 8080)
- **ms-tickets** - Microservicio de tickets con GraphQL (Puerto 4000)

## 🚀 Uso Rápido

### Opción 1: Escaneo Completo (Recomendado)

```powershell
# Asegúrate de que todos los servicios estén corriendo
docker-compose up -d

# Ejecuta el escaneo completo (10-20 minutos)
.\zap-scan.ps1
```

### Opción 2: Escaneo Rápido

```powershell
# Escanear todos los servicios (modo rápido)
.\zap-quick-scan.ps1 -Service all

# O escanear un servicio específico
.\zap-quick-scan.ps1 -Service kong
.\zap-quick-scan.ps1 -Service clientes
.\zap-quick-scan.ps1 -Service zone
.\zap-quick-scan.ps1 -Service tickets
```

## 📊 Reportes Generados

Los reportes se generan en la carpeta `reports/`:

- **HTML** - Reporte visual detallado
- **JSON** - Para procesamiento automatizado
- **XML** - Compatible con herramientas CI/CD

### Estructura de Reportes

```
reports/
├── zap-report-full.html          # Escaneo completo
├── zap-report-full.json
├── zap-report-full.xml
├── zap-report-kong-gateway.html  # Escaneos individuales
├── zap-report-mc-clientes.html
├── zap-report-zone-core.html
└── zap-report-ms-tickets.html
```

## ⚙️ Configuración

### Archivo: `zap-config.yaml`

Configuración principal del escaneo:

- **Contextos**: Define las URLs y rutas a escanear
- **Spider**: Exploración automática de endpoints
- **Passive Scan**: Análisis sin modificar requests
- **Active Scan**: Pruebas activas de vulnerabilidades
- **GraphQL**: Escaneo específico para ms-tickets

### Personalizar Escaneo

Edita `zap-config.yaml` para:

- Aumentar/reducir tiempo de escaneo (`maxDuration`)
- Excluir endpoints específicos (`excludePaths`)
- Ajustar profundidad de spider (`maxDepth`)
- Cambiar políticas de escaneo (`policy`)

## 🔍 Tipos de Vulnerabilidades Detectadas

OWASP ZAP busca vulnerabilidades del OWASP Top 10:

1. **Inyección SQL** - Queries maliciosas en bases de datos
2. **XSS** (Cross-Site Scripting) - Inyección de scripts maliciosos
3. **Autenticación rota** - Fallas en login/sesiones
4. **Exposición de datos sensibles** - Información filtrada
5. **XXE** (XML External Entities) - Ataques en parsers XML
6. **Control de acceso roto** - Permisos inadecuados
7. **Configuración incorrecta** - Headers, CORS, etc.
8. **CSRF** (Cross-Site Request Forgery)
9. **Componentes vulnerables** - Librerías desactualizadas
10. **Logging insuficiente** - Falta de auditoría

## 📈 Niveles de Riesgo

Los hallazgos se clasifican en:

- 🔴 **Alto (High)** - Requiere atención inmediata
- 🟠 **Medio (Medium)** - Debe corregirse pronto
- 🟡 **Bajo (Low)** - Mejorar cuando sea posible
- 🔵 **Informativo** - Buenas prácticas

## 🔧 Troubleshooting

### Error: "Docker no está corriendo"
```powershell
# Inicia Docker Desktop y espera a que esté listo
# Verifica con:
docker ps
```

### Error: "Servicios no están corriendo"
```powershell
# Levanta todos los servicios
docker-compose up -d

# Verifica que estén saludables
docker-compose ps
```

### Error: "Network not found"
```powershell
# Verifica el nombre de la red
docker network ls | Select-String "parkin"

# Si es diferente, edita zap-scan.ps1 y cambia $networkName
```

### El escaneo tarda mucho
```powershell
# Usa el escaneo rápido en su lugar
.\zap-quick-scan.ps1 -Service all

# O edita zap-config.yaml y reduce maxDuration
```

## 🔄 Integración CI/CD

### GitHub Actions Ejemplo

```yaml
name: Security Scan

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  zap-scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Start services
        run: docker-compose up -d
        
      - name: Wait for services
        run: sleep 30
        
      - name: Run ZAP Scan
        run: |
          docker run --rm \
            -v $(pwd):/zap/wrk:rw \
            --network integrationapigateway_parkin-network \
            -t zaproxy/zap-stable \
            zap.sh -cmd -autorun /zap/wrk/zap-config.yaml
            
      - name: Upload Reports
        uses: actions/upload-artifact@v3
        with:
          name: zap-reports
          path: reports/
```

## 📚 Recursos Adicionales

- [OWASP ZAP Documentation](https://www.zaproxy.org/docs/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [ZAP Automation Framework](https://www.zaproxy.org/docs/automate/automation-framework/)

## 🛡️ Mejores Prácticas

1. **Ejecuta escaneos regularmente** - Al menos semanalmente
2. **Revisa todos los hallazgos** - No ignores los "informativos"
3. **Prioriza por riesgo** - Alto → Medio → Bajo
4. **Documenta las correcciones** - Mantén registro de cambios
5. **Re-escanea después de fix** - Verifica que se solucionó
6. **Automatiza en CI/CD** - Previene regresiones

## 📝 Notas

- Los escaneos **NO** afectan los datos de las bases de datos
- ZAP puede generar tráfico significativo (normal en DAST)
- Algunos "falsos positivos" son posibles - valida manualmente
- El escaneo GraphQL requiere que el schema esté expuesto

## 🤝 Contribuir

Si encuentras formas de mejorar la configuración de seguridad:

1. Actualiza `zap-config.yaml`
2. Documenta los cambios
3. Comparte hallazgos críticos con el equipo
