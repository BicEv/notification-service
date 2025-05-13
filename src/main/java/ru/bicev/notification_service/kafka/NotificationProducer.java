package ru.bicev.notification_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.bicev.notification_service.model.Notification;

@Component
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Notification notification) {
        try {
            String messsage = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send("notifications", messsage);
        } catch (Exception e) {
            logger.warn("Failed to serialize notification: {}", notification);
            e.printStackTrace();
        }
    }

}
