FROM openjdk:latest
COPY ./target/test-0.1.0.6-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "test-0.1.0.6-jar-with-dependencies.jar"]