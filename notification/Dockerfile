#Build stage
FROM gradle:jdk21-jammy AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle build

#Package stage
FROM amazoncorretto:21-alpine-jdk
ENV JAR_NAME=flightscraper-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
ENTRYPOINT ["java","-jar","/usr/app/build/libs/notification-0.0.1-SNAPSHOT.jar"]