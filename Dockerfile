FROM openjdk:17

COPY target/myapp.jar /app/myapp.jar

WORKDIR /app

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "myapp.jar"]
