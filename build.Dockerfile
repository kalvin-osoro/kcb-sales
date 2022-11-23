FROM kcb-ses-poc-be-builder:v1.0.0 as builder 
WORKDIR /app
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app
RUN mkdir /app/logs
RUN chmod -R a+rw /app/logs
WORKDIR /app
COPY  --from=builder /app/target/spring-boot-kcb-rest-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=prod", "--server.port=8080"]