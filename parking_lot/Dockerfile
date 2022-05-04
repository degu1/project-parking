FROM openjdk:17-alpine
WORKDIR /parking-lot

COPY target/parking_lot-1.0-SNAPSHOT.jar /parking-lot
COPY target/classes /parking-lot/classes

ENTRYPOINT ["java", "-jar", "parking_lot-1.0-SNAPSHOT.jar"]
