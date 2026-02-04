package ec.edu.espe.mc_clientes.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ec.edu.espe.mc_clientes.util.IpAddressUtil;

import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    
    private static final String ACTION_CREATE = "CREATE";
    private static final String ACTION_UPDATE = "UPDATE";
    private static final String ACTION_DELETE = "DELETE";

    private static final String ENTITY_PERSONA = "PERSONA";
    private static final String ENTITY_VEHICULO = "VEHICULO";

    private static final String SEVERITY_INFO = "INFO";
    private static final String SEVERITY_WARN = "WARN";

    public void sendNotification(String action, String entityType, UUID entityId, String message, Map<String, Object> data){
         NotificationEvent event = NotificationEvent.builder()
            .id(UUID.randomUUID())
            .microservice("microservice-clientes")
            .entityType(entityType)
            .entityId(entityId)
            .message(message)
            .action(action)
            .timestamp(LocalDateTime.now().toString())
            .data(data!=null?data:new HashMap<>())
            .severity(SEVERITY_INFO)
            .hostname(IpAddressUtil.getHostName())
            .ipAddress(IpAddressUtil.getRealIpAddress())
            .build();
        try{
            log.debug("Enviando notificacion: {}", event);
            rabbitTemplate.convertAndSend("notifications_exchange", "notification.routingkey", event);
            log.info("Notificacion enviada: {}", action);
        }catch(Exception e){
            log.error("Error enviando notificacion: {}", e.getMessage());
        }
    }

    // Personas
    public void sendPersonaCreated(UUID personaId, String identificacion, String tipo){
        Map<String, Object> data = new HashMap<>();
        data.put("personaId", personaId);
        data.put("identificacion", identificacion);
        data.put("tipo", tipo);
        sendNotification(ACTION_CREATE, ENTITY_PERSONA, personaId, "Persona created: " + identificacion + " (" + tipo + ")", data);
    }

    public void sendPersonaUpdated(UUID personaId, String identificacion, String tipo){
        Map<String, Object> data = new HashMap<>();
        data.put("personaId", personaId);
        data.put("identificacion", identificacion);
        data.put("tipo", tipo);
        sendNotification(ACTION_UPDATE, ENTITY_PERSONA, personaId, "Persona updated: " + identificacion + " (" + tipo + ")", data);
    }

    public void sendPersonaDeleted(UUID personaId, String identificacion, String tipo){
        Map<String, Object> data = new HashMap<>();
        data.put("personaId", personaId);
        data.put("identificacion", identificacion);
        data.put("tipo", tipo);
        sendNotification(ACTION_DELETE, ENTITY_PERSONA, personaId, "Persona deleted: " + identificacion + " (" + tipo + ")", data);
    }

    // Vehículos
    public void sendVehiculoCreated(UUID vehiculoId, String placa, UUID personaId){
        Map<String, Object> data = new HashMap<>();
        data.put("vehiculoId", vehiculoId);
        data.put("placa", placa);
        data.put("personaId", personaId);
        sendNotification(ACTION_CREATE, ENTITY_VEHICULO, vehiculoId, "Vehiculo created: " + placa, data);
    }

    public void sendVehiculoUpdated(UUID vehiculoId, String placa, UUID personaId){
        Map<String, Object> data = new HashMap<>();
        data.put("vehiculoId", vehiculoId);
        data.put("placa", placa);
        data.put("personaId", personaId);
        sendNotification(ACTION_UPDATE, ENTITY_VEHICULO, vehiculoId, "Vehiculo updated: " + placa, data);
    }

    public void sendVehiculoDeleted(UUID vehiculoId, String placa, UUID personaId){
        Map<String, Object> data = new HashMap<>();
        data.put("vehiculoId", vehiculoId);
        data.put("placa", placa);
        data.put("personaId", personaId);
        sendNotification(ACTION_DELETE, ENTITY_VEHICULO, vehiculoId, "Vehiculo deleted: " + placa, data);
    }
}
