FROM openjdk:17-oracle AS builder
WORKDIR /build
# Copy the source code into the image for building
COPY . /build

# Build
RUN ./mvnw clean package

# Run application
FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 5005
