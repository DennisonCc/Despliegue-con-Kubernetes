#!/bin/bash

# Script para configurar Kong API Gateway con los dos microservicios

echo "Esperando a que Kong esté listo..."
sleep 10

# Configurar servicio mc-clientes
echo "Configurando servicio mc-clientes..."
curl -i -X POST http://localhost:8001/services \
  --data name=mc-clientes \
  --data url=http://mc-clientes:8081

# Configurar ruta para mc-clientes
echo "Configurando ruta para mc-clientes..."
curl -i -X POST http://localhost:8001/services/mc-clientes/routes \
  --data 'paths[]=/clientes' \
  --data name=clientes-route

# Configurar servicio zone-core
echo "Configurando servicio zone-core..."
curl -i -X POST http://localhost:8001/services \
  --data name=zone-core \
  --data url=http://zone-core:8080

# Configurar ruta para zone-core
echo "Configurando ruta para zone-core..."
curl -i -X POST http://localhost:8001/services/zone-core/routes \
  --data 'paths[]=/zones' \
  --data name=zones-route

# Habilitar plugin de CORS (opcional)
echo "Habilitando CORS..."
curl -i -X POST http://localhost:8001/plugins \
  --data name=cors \
  --data config.origins=* \
  --data config.methods=GET,POST,PUT,DELETE,PATCH,OPTIONS \
  --data config.headers=Accept,Content-Type,Authorization \
  --data config.exposed_headers=X-Auth-Token \
  --data config.credentials=true \
  --data config.max_age=3600

# Habilitar plugin de rate limiting (opcional)
echo "Configurando rate limiting..."
curl -i -X POST http://localhost:8001/plugins \
  --data name=rate-limiting \
  --data config.minute=100 \
  --data config.hour=1000

echo "Configuración de Kong completada!"
echo ""
echo "Endpoints disponibles:"
echo "- mc-clientes: http://localhost:8000/clientes"
echo "- zone-core: http://localhost:8000/zones"
echo "- Kong Admin API: http://localhost:8001"
