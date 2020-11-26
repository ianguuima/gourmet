FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD target/gourmet-0.0.1-SNAPSHOT.jar gourmet-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/gourmet-0.0.1-SNAPSHOT.jar"]