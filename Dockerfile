FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
EXPOSE 8384

COPY  spring-boot-kcb-rest-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]