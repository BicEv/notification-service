package ru.bicev.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long userId;
    private String email;
    private String deviceToken;
    private NotificationType type;
    private String subject;
    private String message;

    public static Notification emailNotification(String email, String subject, String message) {
        Notification notification = new Notification();
        notification.setType(NotificationType.EMAIL);
        notification.setEmail(email);
        notification.setSubject(subject);
        notification.setMessage(message);
        return notification;
    }

    public static Notification pushNotification(Long userId, String deviceToken, String message) {
        Notification notification = new Notification();
        notification.setType(NotificationType.PUSH);
        notification.setUserId(userId);
        notification.setDeviceToken(deviceToken);
        notification.setMessage(message);
        return notification;
    }

    public static Notification allNotification(Long userId, String email, String deviceToken, String subject,
            String message) {
        Notification notification = new Notification(userId, email, deviceToken, null, subject, message);
        notification.setType(NotificationType.ALL);
        return notification;
    }

}
