FROM openjdk:19-jdk
LABEL maintainer="seailsong"
ARG JAR_FILE=build/libs/ConcertReservation-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-jar","/docker-springboot.jar"]