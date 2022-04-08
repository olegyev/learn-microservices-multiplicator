FROM openjdk:14
COPY ./target/multiplicator-0.0.1-SNAPSHOT.jar /usr/src/multiplicator/
WORKDIR /usr/src/multiplicator
EXPOSE 8080
CMD ["java", "-jar", "multiplicator-0.0.1-SNAPSHOT.jar"]