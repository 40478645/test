FROM openjdk:latest
COPY ./target/test-0.1.0.5-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "test-0.1.0.5-jar-with-dependencies.jar"]