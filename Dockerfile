FROM maven:3.8.6-amazoncorretto-11 as stage1
RUN mkdir /app
WORKDIR /app
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app
RUN mkdir /app/logs
RUN chmod -R a+rw /app/logs
WORKDIR /app
COPY  --from=stage1 /app/target/spring-boot-kcb-rest-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=mysql", "--server.port=8080"]
