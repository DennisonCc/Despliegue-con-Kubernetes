# Multi-stage build for notification-service (NestJS)
FROM node:20-alpine AS builder

WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar código fuente
COPY . .

# Compilar la aplicación
RUN npm run build

# Etapa de producción
FROM node:20-alpine

WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar solo dependencias de producción
RUN npm ci --only=production && npm cache clean --force

# Copiar código compilado desde la etapa de build
COPY --from=builder /app/dist ./dist

# Exponer puerto (ajustar según tu configuración)
EXPOSE 3002

# Usuario no privilegiado
USER node

# Comando de inicio
CMD ["node", "dist/main"]
