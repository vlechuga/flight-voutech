FROM openjdk:17-alpine as build

# Install dependencies
RUN apk add \
    maven

WORKDIR /app

COPY . /app
RUN mvn install -DskipTests

FROM openjdk:17-alpine
VOLUME /tmp
ARG DEPENDENCY=/app/target
COPY --from=build ${DEPENDENCY}/demo-*.jar flight-service.jar
ENTRYPOINT [ "java","-jar","/flight-service.jar" ]