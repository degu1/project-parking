#
# Build stage
#
FROM maven:3.8.5-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:18-jdk-slim
COPY --from=build /home/app/target/message_receiver.jar /usr/local/lib/
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/message_receiver.jar"]