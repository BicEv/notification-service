package ru.bicev.notification_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.bicev.notification_service.kafka.NotificationProducer;
import ru.bicev.notification_service.model.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationProducer notificationProducer;

    public NotificationController(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public ResponseEntity<Void> sendNotification(@RequestBody Notification notification) {
        notificationProducer.sendNotification(notification);
        return ResponseEntity.ok().build();
    }

}
