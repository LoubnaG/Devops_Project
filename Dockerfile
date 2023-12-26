# Stage 1: Build the application with Maven

FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/BANK*.jar /app/BANK.jar
CMD ["java", "-jar", "BANK.jar"]
EXPOSE 8081