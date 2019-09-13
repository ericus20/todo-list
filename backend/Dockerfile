#### Stage 1: Build the application
FROM adoptopenjdk/openjdk12:slim as build

# Set the current working directory inside the image
WORKDIR /app

# Copy gradle executable to the image
COPY gradlew .
COPY gradle gradle
COPY build.gradle .

# Set permission to execute file
RUN chmod +x gradlew

# Install dependencies for environment
RUN apt-get update && apt-get install -y \
    dos2unix \
    && apt-get clean

# Convert Windows line endings to Unix line ending.
RUN dos2unix gradlew

# Build all the dependencies
RUN ./gradlew dependencies

# Copy the project source
COPY src src

# Package the application
RUN ./gradlew bootJar
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM adoptopenjdk/openjdk12:slim

ARG DEPENDENCY=/app/build/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","com.developersboard.backend.BackendApplication"]
