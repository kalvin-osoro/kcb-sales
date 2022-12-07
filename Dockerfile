FROM maven:3.8.6-amazoncorretto-11 as builder 
RUN mkdir /app
WORKDIR /app
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY  --from=builder /app/target/spring-boot-kcb-rest-api-0.0.1-SNAPSHOT.jar /app/app.jar
RUN mkdir /app/logs
RUN chmod -R a+rw /app/logs
RUN mkdir /app/upload
RUN chmod -R a+rw /app/upload
VOLUME /app/logs
VOLUME /app/upload
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=mysql", "--server.port=8080"]