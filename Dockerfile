# build
FROM maven:3.9.10-eclipse-temurin-17-noble AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# runtime
FROM eclipse-temurin:17-jre-noble
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080
ENV TZ="America/Sao_Paulo"

ENTRYPOINT ["java", "-jar", "app.jar"]
