package ru.bicev.notification_service.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ru.bicev.notification_service.model.Notification;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;
    private String lineSeparator = System.lineSeparator();

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }

    public void send(Notification notification) {
        switch (notification.getType()) {
            case EMAIL -> sendEmail(notification);
            case GENERAL -> sendLog(notification);
            case ALL -> {
                sendEmail(notification);
                sendLog(notification);
            }
            default -> {
                logger.warn("Unsupported notification type: {}", notification.getType());
                throw new IllegalArgumentException("Insupported notification type");
            }
        }
    }

    private void sendEmail(Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getEmail());
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            mailSender.send(message);
            logger.info("Email notification sent to: {} | subject: {} | message: {}",
                    notification.getEmail(), notification.getSubject(), notification.getMessage());
        } catch (MailException e) {
            logger.error("Error sending email: {}", notification.getEmail());
        }
    }

    private void sendLog(Notification notification) {
        try {
            Files.writeString(Paths.get("notifications.log"), notification.toString() + lineSeparator,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            logger.info("Push notification sent to userId: {} | deviceToken: {} | message: {}",
                    notification.getUserId(), notification.getDeviceToken(), notification.getMessage());
        } catch (IOException e) {
            logger.error("Error writing to file: {}", e.getMessage());
        }
    }

}
