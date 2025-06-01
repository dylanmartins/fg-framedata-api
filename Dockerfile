FROM eclipse-temurin:21-jdk

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

COPY src ./src
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/framedata-api-0.0.1-SNAPSHOT.jar"]
