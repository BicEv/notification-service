package ru.bicev.notification_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.bicev.notification_service.model.Notification;
import ru.bicev.notification_service.service.NotificationService;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void consume(String json) {
        logger.info("Received from Kafka: {}", json);
        try {
            Notification notification = objectMapper.readValue(json, Notification.class);
            notificationService.send(notification);
            logger.info("Notification consumed: {}", notification.getMessage());
        } catch (Exception e) {
            logger.warn("Failed to process json: {}", json);
            e.printStackTrace();
        }
    }

}
