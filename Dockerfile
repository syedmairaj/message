FROM openjdk:17

COPY target/message.jar /usr/app/

WORKDIR /usr/app

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "message.jar"]