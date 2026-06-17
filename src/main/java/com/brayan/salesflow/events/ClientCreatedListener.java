package com.brayan.salesflow.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClientCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(ClientCreatedListener.class);

    @KafkaListener(topics = "debezium_salesflow.public.clients", groupId = "salesflow-crm-group")
    public void listen(String message) {
        log.info("=========================================================");
        log.info("🚀 EVENTO DE MICROSERVICIO RECIBIDO (CDC Debezium) 🚀");
        log.info("Mensaje en bruto recibido desde Kafka: {}", message);
        
        if (message != null && message.contains("\"op\":\"c\"")) {
            log.info("✅ ¡NUEVO CLIENTE CREADO DETECTADO!");
            log.info("Aquí iría la lógica para enviar un email de bienvenida o actualizar un index de búsqueda.");
        } else if (message != null && message.contains("\"op\":\"u\"")) {
            log.info("✏️ ¡CLIENTE ACTUALIZADO DETECTADO!");
        } else if (message != null && message.contains("\"op\":\"d\"")) {
            log.info("🗑️ ¡CLIENTE ELIMINADO DETECTADO!");
        }
        
        log.info("=========================================================");
    }
}
