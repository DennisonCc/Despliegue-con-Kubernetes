package ec.edu.espe.mc_clientes.messaging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class NotificationEvent {

    private UUID id;
    private String microservice;
    private String action;
    private String entityType;
    private UUID entityId;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String timestamp;

    private Map<String, Object> data;
    private String severity;
    private String hostname;
    private String ipAddress;

    public String getTimestampString(){
        return timestamp != null ? timestamp : LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
