FROM amazoncorretto:21-alpine-jdk
COPY target/server-0.0.1-SNAPSHOT.jar server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/server-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083