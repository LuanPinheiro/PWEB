FROM amazoncorretto:21-alpine-jdk
COPY target/gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
CMD sleep 15 && java -jar ./gateway-0.0.1-SNAPSHOT.jar
EXPOSE 8084