Notification Service
Notification Service — это простой микросервис на базе Spring Boot, предназначенный для обработки событий из Apache Kafka и отправки уведомлений двумя способами:

Email — отправка писем через SMTP (например, Gmail).

General — запись уведомлений в лог-файл.

Проект создан в образовательных целях для изучения интеграции Kafka и Spring Boot Mail.

Функциональность
Kafka Consumer: Слушает топик notifications и обрабатывает входящие сообщения.

Типы уведомлений:

EMAIL: Отправляет письма на указанный адрес.

GENERAL: Записывает сообщение в файл notifications.log.

Kafka Producer: Предоставляет REST API для отправки тестовых сообщений в Kafka.

Технологии
Java 17

Spring Boot 3.x

Apache Kafka

Spring Kafka

Spring Boot Mail

Gradle

Запуск проекта
1. Клонирование репозитория
bash
Копировать
Редактировать
git clone https://github.com/BicEv/notification-service.git
cd notification-service
2. Конфигурация SMTP
В файле src/main/resources/application.yml укажите настройки SMTP-сервера. Пример для Gmail:

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your.email@gmail.com
    password: your_app_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
Важно: Если вы используете Gmail, убедитесь, что у вас включена двухфакторная аутентификация и сгенерирован пароль приложения.

3. Запуск Kafka и Zookeeper
Убедитесь, что у вас запущены Kafka и Zookeeper. Пример docker-compose.yml:

version: '3.8'
services:
  zookeeper:
    image: bitnami/zookeeper:3.9
    ports:
      - "2181:2181"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes

  kafka:
    image: bitnami/kafka:3.9
    ports:
      - "9092:9092"
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_LISTENERS=PLAINTEXT://0.0.0.0:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
      - KAFKA_CFG_OFFSETS_TOPIC_REPLICATION_FACTOR=1
    depends_on:
      - zookeeper
Запустите контейнеры:
docker-compose up -d

4. Сборка и запуск приложения
./gradlew bootRun
Приложение будет доступно по адресу: http://localhost:8080


Тестирование отправки уведомлений
Отправка тестового уведомления
Отправьте POST-запрос на /send с телом:

{
  "type": "EMAIL",
  "recipient": "recipient@example.com",
  "message": "Привет! Это тестовое уведомление."
}
Для GENERAL уведомлений:
{
  "type": "GENERAL",
  "message": "Это общее уведомление, записанное в файл."
}

Проверка результатов
EMAIL: Проверьте почтовый ящик получателя.
GENERAL: Убедитесь, что в файле notifications.log появилась новая запись.

Структура проекта
notification-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.notification/
│   │   │       ├── NotificationServiceApplication.java
│   │   │       ├── controller/
│   │   │       │   └── NotificationController.java
│   │   │       ├── model/
│   │   │       │   └── Notification.java
│   │   │       ├── service/
│   │   │       │   ├── EmailNotificationService.java
│   │   │       │   ├── FileNotificationService.java
│   │   │       │   └── KafkaConsumerService.java
│   │   └── resources/
│   │       └── application.yml
├── build.gradle
└── README.md

Примечания
Проект предназначен для образовательных целей и демонстрации базовой интеграции Kafka с Spring Boot.

В будущем можно расширить функциональность, добавив поддержку других типов уведомлений или интеграцию с внешними сервисами.
