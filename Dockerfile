# ===== Builder stage =====
FROM maven:3.9.9-eclipse-temurin-25 AS builder

WORKDIR /workspace

# Download dependencies first (cached if pom.xml hasn't changed)
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:25-jre AS runtime

WORKDIR /app

# Create a non-root user
RUN useradd -r -u 1001 appuser

# Copy the built jar
COPY --from=builder /workspace/target/*.jar /app/app.jar

# Use non-root user
USER appuser

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
