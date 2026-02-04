    package ec.edu.espe.zone_core.zone_core.messaging;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.cglib.core.Local;
    import ec.edu.espe.zone_core.zone_core.util.IpAddressUtil;

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

        private static final String ENTITY_ZONE = "ZONE";
        private static final String ENTITY_SPACE = "SPACE";

        private static final String SEVERITY_INFO = "INFO";
        private static final String SEVERITY_WARN = "WARN";

        public static final String IP_CLIENT="IP";
        public void sendNotification(String action, String entityType, UUID entityId, String message, Map<String, Object> data){
            NotificationEvent event = NotificationEvent.builder()
                .id(UUID.randomUUID())
                .microservice("microservice-zones")
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
                log.info("Notificacion enviada {}", event);
            }catch(Exception e){
                log.error("Error enviando notificacion: {}", event, action, e.getMessage());
            }
        }

        public void sendSpacesCreated(UUID spaceId, String space, UUID zoneId){

            Map<String, Object> data = new HashMap<>();
            data.put("spaceId", spaceId);
            data.put("zoneId", zoneId);
            sendNotification(ACTION_CREATE, ENTITY_SPACE, spaceId, "Space created: " + space, data);

        }

        public void sendZoneCreated(UUID zoneId, String zone){
            Map<String, Object> data = new HashMap<>();
            data.put("zoneId", zoneId);
            sendNotification(ACTION_CREATE, ENTITY_ZONE, zoneId, "Zone created: " + zone, data);
        }

        public void sendZoneUpdated(UUID zoneId, String zone){
            Map<String, Object> data = new HashMap<>();
            data.put("zoneId", zoneId);
            sendNotification(ACTION_UPDATE, ENTITY_ZONE, zoneId, "Zone updated: " + zone, data);
        }

        public void sendZoneDeleted(UUID zoneId, String zone){
            Map<String, Object> data = new HashMap<>();
            data.put("zoneId", zoneId);
            sendNotification(ACTION_DELETE, ENTITY_ZONE, zoneId, "Zone deleted: " + zone, data);
        }

        public void sendSpaceUpdated(UUID spaceId, String space, UUID zoneId){
            Map<String, Object> data = new HashMap<>();
            data.put("spaceId", spaceId);
            data.put("zoneId", zoneId);
            sendNotification(ACTION_UPDATE, ENTITY_SPACE, spaceId, "Space updated: " + space, data);
        }

        public void sendSpaceDeleted(UUID spaceId, String space, UUID zoneId){
            Map<String, Object> data = new HashMap<>();
            data.put("spaceId", spaceId);
            data.put("zoneId", zoneId);
            sendNotification(ACTION_DELETE, ENTITY_SPACE, spaceId, "Space deleted: " + space, data);
        } 
    }