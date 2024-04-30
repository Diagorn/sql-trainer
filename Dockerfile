FROM amazoncorretto:17-alpine-jdk
LABEL authors="Diagorn"

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

EXPOSE 8080
CMD ["java", "-jar", "./build/libs/sql-trainer-0.0.1-SNAPSHOT.jar"]