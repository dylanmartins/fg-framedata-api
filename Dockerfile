FROM eclipse-temurin:21-jdk

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/fg-framedata-api-0.0.1-SNAPSHOT.jar"]
