FROM openjdk:17-alpine
WORKDIR /message-receiver

COPY target/message_receiver-1.0-SNAPSHOT.jar /message-receiver
COPY target/classes /message_receiver/classes

ENTRYPOINT ["java", "-jar", "message_receiver-1.0-SNAPSHOT.jar"]