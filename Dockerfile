FROM openjdk:latest
COPY ./target/seMethodsCW.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethodsCW.jar", "db:3306"]