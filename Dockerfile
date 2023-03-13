FROM maven:3.9.0-eclipse-temurin-17-focal as dev-build
COPY . /app
WORKDIR /app
RUN ["mvn","clean","package","-DskipTests"]

FROM openjdk:17-alpine
COPY --from=dev-build /app/target/*.jar /app/shortener.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar", "shortener.jar"]
