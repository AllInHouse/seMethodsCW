FROM openjdk:latest
COPY ./target/seMethodsCW-0.1.0.3-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethodsCW-0.1.0.3-jar-with-dependencies.jar"]