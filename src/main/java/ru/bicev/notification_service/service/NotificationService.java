package ru.bicev.notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.bicev.notification_service.model.Notification;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void send(Notification notification) {
        switch (notification.getType()) {
            case EMAIL -> sendEmail(notification);
            case PUSH -> sendPush(notification);
            case ALL -> {
                sendEmail(notification);
                sendPush(notification);
            }
            default -> {
                logger.warn("Unsupported notification type: {}", notification.getType());
                throw new IllegalArgumentException("Insupported notification type");
            }
        }
    }

    private void sendEmail(Notification notification) {
        logger.info("Email notification sent to: {} | subject: {} | message: {}",
                notification.getEmail(), notification.getSubject(), notification.getMessage());
    }

    private void sendPush(Notification notification) {
        logger.info("Push notification sent to userId: {} | deviceToken: {} | message: {}",
                notification.getUserId(), notification.getDeviceToken(), notification.getMessage());
    }

}
