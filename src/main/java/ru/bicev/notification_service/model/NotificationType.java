package ru.bicev.notification_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum NotificationType {
    EMAIL,
    PUSH,
    ALL
}
