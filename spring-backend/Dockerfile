FROM  openjdk:17-jdk-alpine
MAINTAINER  Gustavo Antonio Lutz de Matos <gustavo.almatos@gmail.com>

VOLUME /tmp
ADD ./target/spring-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${APP_PROFILE}","./app.jar"]
