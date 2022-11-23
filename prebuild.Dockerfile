FROM maven:3.8.6-amazoncorretto-11
RUN mkdir /app
WORKDIR /app
COPY . .
RUN mvn dependency:go-offline