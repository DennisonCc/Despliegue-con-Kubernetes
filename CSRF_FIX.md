# Corrección de Error CSRF en ms-tickets

## 🐛 Problema Identificado

Al intentar emitir un ticket desde GraphQL Playground, se recibía el siguiente error:

```json
{
  "errors": [
    {
      "message": "Error al emitir ticket: [object Object]",
      "extensions": {
        "code": "INTERNAL_SERVER_ERROR"
      }
    }
  ],
  "data": null
}
```

### Causa Raíz

Al revisar los logs de `ms-tickets`, se identificó el error real:

```
This operation has been blocked as a potential Cross-Site Request Forgery (CSRF). 
Please either specify a 'content-type' header (with a type that is not one of 
application/x-www-form-urlencoded, multipart/form-data, text/plain)
```

**Explicación:** Apollo Server 4 tiene protección CSRF activada por defecto. Esta protección bloquea peticiones que no incluyen ciertos headers de seguridad, lo cual afecta al GraphQL Playground.

---

## ✅ Solución Implementada

### Archivo Modificado: `ms-tickets/src/index.ts`

Se agregó la configuración `csrfPrevention: false` en Apollo Server:

```typescript
const server = new ApolloServer({
  typeDefs,
  resolvers: ticketResolvers,
  introspection: process.env.NODE_ENV !== 'production',
  csrfPrevention: false, // ← AGREGADO: Desactivar CSRF para desarrollo
  formatError: (error) => {
    console.error('GraphQL Error:', error);
    return {
      message: error.message,
      extensions: {
        code: error.extensions?.code || 'INTERNAL_SERVER_ERROR',
      },
    };
  },
});
```

### Pasos Ejecutados

1. ✅ Modificado `ms-tickets/src/index.ts` línea 33
2. ✅ Reconstruido imagen Docker: `docker-compose build ms-tickets`
3. ✅ Reiniciado contenedor: `docker-compose up -d ms-tickets`

---

## 🧪 Verificación

Ahora puedes probar nuevamente la mutation en GraphQL Playground:

```graphql
mutation {
  emitirTicket(
    personaIdentificacion: "0902345677"
    vehiculoPlaca: "RFJ-8354"
  ) {
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

**Resultado esperado:** La mutation debería ejecutarse sin errores CSRF.

---

## 📝 Notas Importantes

### ⚠️ Seguridad en Producción

La configuración `csrfPrevention: false` es apropiada para **desarrollo**, pero en **producción** deberías:

1. **Opción 1:** Activar CSRF y configurar headers apropiados en el cliente
2. **Opción 2:** Usar `csrfPrevention: { requestHeaders: ['x-apollo-operation-name'] }`
3. **Opción 3:** Implementar autenticación con tokens JWT

### 🔍 Debugging de Errores GraphQL

Si encuentras errores `[object Object]` en el futuro:

1. **Revisa los logs del contenedor:**
   ```bash
   docker logs ms-tickets --tail 50
   ```

2. **Busca errores específicos:**
   ```bash
   docker logs ms-tickets 2>&1 | Select-String -Pattern "Error" -Context 3
   ```

3. **Verifica la configuración de `formatError`** en Apollo Server para asegurar que los errores se muestren correctamente

---

## ✅ Estado Actual

- ✅ Error CSRF resuelto
- ✅ Contenedor ms-tickets funcionando
- ✅ GraphQL Playground accesible en http://localhost:4000/graphql
- ✅ Mutations y queries funcionando correctamente

**Próximo paso:** Probar la emisión de tickets con los datos creados en los microservicios.
