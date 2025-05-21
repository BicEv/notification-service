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

    private String email;
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

    public static Notification generalNotification(Long userId, String deviceToken, String message) {
        Notification notification = new Notification();
        notification.setType(NotificationType.GENERAL);
        notification.setMessage(message);
        return notification;
    }

    @Override
    public String toString() {
        return "Notification [email=" + email + ", type=" + type + ", subject=" + subject + ", message=" + message
                + "]";
    }

    

}
